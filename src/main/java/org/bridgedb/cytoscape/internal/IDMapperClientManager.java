/*
 Copyright (c) 2006, 2007, The Cytoscape Consortium (www.cytoscape.org)

 The Cytoscape Consortium is:
 - Institute for Systems Biology
 - University of California San Diego
 - Memorial Sloan-Kettering Cancer Center
 - Institut Pasteur
 - Agilent Technologies

 This library is free software; you can redistribute it and/or modify it
 under the terms of the GNU Lesser General Public License as published
 by the Free Software Foundation; either version 2.1 of the License, or
 any later version.

 This library is distributed in the hope that it will be useful, but
 WITHOUT ANY WARRANTY, WITHOUT EVEN THE IMPLIED WARRANTY OF
 MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE.  The software and
 documentation provided hereunder is on an "as is" basis, and the
 Institute for Systems Biology and the Whitehead Institute
 have no obligations to provide maintenance, support,
 updates, enhancements or modifications.  In no event shall the
 Institute for Systems Biology and the Whitehead Institute
 be liable to any party for direct, indirect, special,
 incidental or consequential damages, including lost profits, arising
 out of the use of this software and its documentation, even if the
 Institute for Systems Biology and the Whitehead Institute
 have been advised of the possibility of such damage.  See
 the GNU Lesser General Public License for more details.

 You should have received a copy of the GNU Lesser General Public License
 along with this library; if not, write to the Free Software Foundation,
 Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.
 */

package org.bridgedb.cytoscape.internal;

import org.bridgedb.cytoscape.internal.util.BridgeRestUtil;
import org.bridgedb.cytoscape.internal.util.DataSourceUtil;
import org.bridgedb.cytoscape.internal.util.DataSourceWrapper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.bridgedb.AttributeMapper;
import org.bridgedb.DataSource;
import org.bridgedb.IDMapper;
import org.bridgedb.IDMapperCapabilities;
import org.bridgedb.IDMapperStack;
import org.cytoscape.application.CyApplicationConfiguration;

/**
 *
 * @author gjj
 */
public class IDMapperClientManager {

    private enum CacheStatus {
        UNCACHED, CACHING, CACHED;
    }

    public interface IDMapperChangeListener {
        public void changed();
    }

    public static class TrackIDMapperChangeListener {
        private boolean changed = false;

        public void changed() {
            changed = true;
        }

        public boolean reset() {
            boolean tmp = changed;
            changed = false;
            return tmp;
        }

        public boolean isChanged() {
            return changed;
        }
    }
    
    private static CyApplicationConfiguration cyApplicationConfiguration;
    
    public static void setCyApplicationConfiguration(CyApplicationConfiguration applicationConfiguration) {
        cyApplicationConfiguration = applicationConfiguration;
    }
    
    private static final Map<String, IDMapperClientManager> idMapperClientManagers = new HashMap<String, IDMapperClientManager>();
    
    public static IDMapperClientManager getIDMapperClientManager(String appName) {
        if (appName==null) {
            appName = FinalStaticValues.PLUGIN_NAME; // by default;
        }
        
        IDMapperClientManager idMapperClientManager = idMapperClientManagers.get(appName);
        if (idMapperClientManager==null) {
            idMapperClientManager = new IDMapperClientManager(appName);
            idMapperClientManagers.put(appName, idMapperClientManager);
        }
        return idMapperClientManager;
    }
    
    public static IDMapperClientManager getDefaultIDMapperClientManager() {
        return getIDMapperClientManager(FinalStaticValues.PLUGIN_NAME);
    }

    private final Map<String, IDMapperClient> clientConnectionStringMap;
    private final Set<IDMapperClient> selectedClients;

    private CacheStatus cacheStatus = CacheStatus.UNCACHED;
    private Set<DataSourceWrapper> srcTypes = null;
    private Set<DataSourceWrapper> tgtTypes = null;
    private Set<List<DataSourceWrapper>> supportedMapping = null;
    private IDMapperStack selectedIDMapperStack = null;
    private final List<IDMapperChangeListener> listeners;
    
    private ExecutorService executor = null;

    private final int waitSeconds = 5;
    
    private final String appName;

    private IDMapperClientManager(String appName) {
        this.appName = appName;
        clientConnectionStringMap = new HashMap<String, IDMapperClient>();
        selectedClients = new HashSet<IDMapperClient>();
        listeners = new ArrayList<IDMapperChangeListener>();
        addIDMapperChangeListener(new IDMapperChangeListener() {
            public void changed() {
                cacheStatus = CacheStatus.UNCACHED;
            }
        });
        
        //reloadFromCytoscapeSessionProperties();
    }

    public void addIDMapperChangeListener(IDMapperChangeListener listener) {
        if (listener==null)
            throw new NullPointerException();

        listeners.add(listener);
    }

    private void fireIDMapperChange() {
        resetCache();
        for (IDMapperChangeListener listener : listeners) {
            listener.changed();
        }
    }

    /* modify 'CytoscapeInit.getProperties()' in Cy 3.0
    public void reloadFromCytoscapeSessionProperties() {
        
    	Properties props = CytoscapeInit.getProperties();

        String prefix = FinalStaticValues.CLIENT_SESSION_PROPS+".";

        Set<String> propIds = new HashSet();

        // Find all properties with this prefix
        Enumeration iter = props.propertyNames();
        while (iter.hasMoreElements()) {
            String property = (String) iter.nextElement();

            if (property.startsWith(prefix)) {
                int start = prefix.length();
                int end = property.indexOf('.', start);
                if (end!=-1) {
                    propIds.add(property.substring(start, end));
                }
            }
        }

        int i= 0;
        for (String pid : propIds) {
            String newPId = ""+(i++)+"-"+System.currentTimeMillis();
            IDMapperClientProperties imcp = new IDMapperClientProperties(pid);

            IDMapperClientImplTunables client  = new IDMapperClientImplTunables(imcp, newPId);
            registerClient(client, client.isSelected());
        }
    }
	*/
    
    private static final String CLIENT_START = "#client start";
    private static final String CLIENT_END = "#client end";
    private static final String CLIENT_ID = "Client ID:\t";
    private static final String CLIENT_CONN_STR = "Connection String:\t";
    private static final String CLIENT_CLASS_PATH = "Class Path:\t";
    private static final String CLIENT_DISPLAY_NAME = "Display Name:\t";
    private static final String CLIENT_SELECTED = "Selected:\t";
    private static final String CLIENT_TYPE = "Client Type:\t";
    
    public boolean reloadFromCytoscapeGlobalProperties() {
        removeAllClients(true); // remove all of the current clients

        try {
            File file = getGlobalPropertiesFile();
            
            BufferedReader in = new BufferedReader(new FileReader(file));

            String clientId = null;
            String classStr = null;
            String connStr = null;
            String display = null;
            boolean selected = true;
            IDMapperClient.ClientType clientType = null;

            String line;
            while ((line=in.readLine())!=null) {
                if (line.compareTo(CLIENT_START)==0) {
                    clientId = null;
                    classStr = null;
                    connStr = null;
                    display = null;
                    selected = true;
                } else if (line.compareTo(CLIENT_END)==0) {
                    if (classStr!=null && connStr!=null) {
                        try {
                            IDMapperClient client = new IDMapperClientImpl
                                    .Builder(connStr, classStr)
                                    .displayName(display)
                                    .id(clientId)
                                    .selected(selected)
                                    .clientType(clientType)
                                    .build();
                            registerClient(client, selected);
                        } catch(Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                    // something is wrong with the file
                    }
                } else if (line.startsWith(CLIENT_ID)) {
                    clientId = line.substring(CLIENT_ID.length());
                } else if (line.startsWith(CLIENT_CONN_STR)) {
                    connStr = line.substring(CLIENT_CONN_STR.length());
                } else if (line.startsWith(CLIENT_CLASS_PATH)) {
                    classStr = line.substring(CLIENT_CLASS_PATH.length());
                } else if (line.startsWith(CLIENT_DISPLAY_NAME)) {
                    display = line.substring(CLIENT_DISPLAY_NAME.length());
                } else if (line.startsWith(CLIENT_SELECTED)) {
                    selected = Boolean.parseBoolean(line.substring(
                            CLIENT_SELECTED.length()));
                } else if (line.startsWith(CLIENT_TYPE)) {
                    clientType = IDMapperClient.ClientType.valueOf(
                            line.substring(CLIENT_TYPE.length()));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
    
    /* modify 'CytoscapeInit.getProperties()' in Cy 3.0
    static boolean registerDefaultClient() {
        Properties props = CytoscapeInit.getProperties();
        String defaultSpecies = props.getProperty(FinalStaticValues.DEFAULT_SPECIES_NAME);
        return registerDefaultClient(defaultSpecies);
    }
    
    private boolean registerDefaultClient(String defaultSpecies) {
        return registerDefaultClient(defaultSpecies, null);
    }

    private boolean registerDefaultClient(String newDefaultSpecies, String oldDefaultSpecies) {
        if (newDefaultSpecies==null) {
            throw new IllegalArgumentException("newDefaultSpecies is null");
        }

        if (oldDefaultSpecies!=null) {
            removeClient("idmapper-bridgerest:"+BridgeRestUtil.defaultBaseUrl+"/"+oldDefaultSpecies);
        }

        List<String> orgs = BridgeRestUtil.supportedOrganisms(BridgeRestUtil.defaultBaseUrl);
        if (!orgs.contains(newDefaultSpecies)) {
            System.err.println("No default ID mapping resources for species: "+newDefaultSpecies
                    +". Please configure manually.");
            return false;
        }

        String classPath = "org.bridgedb.webservice.bridgerest.BridgeRest";
        String connStr = "idmapper-bridgerest:"+BridgeRestUtil.defaultBaseUrl+"/"+newDefaultSpecies;
        IDMapperClient client;
        try {
            client = new IDMapperClientImpl
                                .Builder(connStr, classPath)
                                .displayName("BridgeDb("+BridgeRestUtil.defaultBaseUrl+"/"+newDefaultSpecies+")")
                                .selected(true)
                                .clientType(IDMapperClient.ClientType.WEBSERVICE)
                                .build();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        registerClient(client);
        return true;
    }*/
    
    private File getGlobalPropertiesFile() throws IOException {
        File configDir = cyApplicationConfiguration.getAppConfigurationDirectoryLocation(
                BridgeDbApp.class);
        if (!configDir.exists()) {
            if (!configDir.mkdirs()) {
                System.err.println("Failed to create config dir for bridgedb");
            }
        }
        
        File configFile = new File(configDir.getAbsolutePath()
                + File.separatorChar + appName + "." + FinalStaticValues.CLIENT_GLOBAL_PROPS);
        if (!configFile.exists()) {
            if (!configFile.createNewFile()) {
                System.err.println("Failed to create config file for bridgedb");
            }
        }
        
        return configFile;
    }
    
    public void saveCurrentToCytoscapeGlobalProperties() throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(getGlobalPropertiesFile()));

        Set<IDMapperClient> clients = allClients();

        for (IDMapperClient client : clients) {
            out.write(CLIENT_START);
            out.newLine();

            String clientId = client.getId();
            out.write(CLIENT_ID+clientId);
            out.newLine();

            String classStr = client.getClassString();
            out.write(CLIENT_CLASS_PATH+classStr);
            out.newLine();

            String connStr = client.getConnectionString();
            out.write(CLIENT_CONN_STR+connStr);
            out.newLine();

            String display = client.getDisplayName();
            out.write(CLIENT_DISPLAY_NAME+display);
            out.newLine();

            boolean selected = selectedClients.contains(client);
            out.write(CLIENT_SELECTED+Boolean.toString(selected));
            out.newLine();

            IDMapperClient.ClientType clientType = client.getClientType();
            out.write(CLIENT_TYPE+clientType.name());
            out.newLine();

            out.write(CLIENT_END);
            out.newLine();
        }

        out.close();
    }

    public int countClients() {
        return clientConnectionStringMap.size();
    }

    public Set<IDMapperClient> allClients() {
        return new HashSet(clientConnectionStringMap.values());
    }

    public Set<IDMapperClient> selectedClients() {
        return Collections.unmodifiableSet(selectedClients);
    }
    
    public IDMapperClient getClient(String clientConnStr) {
        return clientConnectionStringMap.get(clientConnStr);
    }

    public boolean removeClient(String clientConnStr) {
        return removeClient(clientConnStr, true);
    }

    public boolean removeClient(String clientConnStr,
            boolean removeSessionProps) {
        if (clientConnStr == null) {
            return false;
        }
    
        IDMapperClient cl = clientConnectionStringMap.get(clientConnStr);
        return removeClient(cl, removeSessionProps);
    }

    public boolean removeClient(final IDMapperClient client) {
        return removeClient(client, true);
    }

    public boolean removeClient(final IDMapperClient client,
            boolean removeSessionProps) {
        if (client == null) {
            return false;
        }

        if (!clientConnectionStringMap.containsValue(client)) {
            return false;
        }

        clientConnectionStringMap.remove(client.getConnectionString());
        selectedClients.remove(client);

//        if (removeSessionProps &&
//                client instanceof IDMapperClientImplTunables) {
//            ((IDMapperClientImplTunables)client).close();
//        }

        fireIDMapperChange();
        
        return true;
    }

    public void removeAllClients() {
        removeAllClients(true);
    }

    public void removeAllClients(boolean removeSessionProps) {
        for (IDMapperClient client : allClients()) {
            removeClient(client, removeSessionProps);
        }
    }

    public boolean registerClient(final IDMapperClient client) {
        return registerClient(client, true);
    }

    public boolean registerClient(final IDMapperClient client, boolean selected) {
        return registerClient(client, selected, false);
    }

    /**
     * Register a client. If there exists a client in the manager with the same
     * connection string, that client will be replaced with the new client.
     * @param client
     */
    public boolean registerClient(final IDMapperClient client, boolean selected, boolean connectImmediately) {
        if (client == null) {
            throw new IllegalArgumentException();
        }

        IDMapperClient oldClient = getClient(client.getConnectionString());
        if (oldClient!=null) {
            removeClient(oldClient);
        }

        if (connectImmediately) {
            if (null == client.getIDMapper())
                return false;
        }

        clientConnectionStringMap.put(client.getConnectionString(), client);

        if (selected) {
            selectedClients.add(client);
        }
        
        fireIDMapperChange();
        return true;
    }

    public  void setClientSelection(IDMapperClient client, boolean select) {
        boolean changed;
        if (select)
            changed = selectedClients.add(client);
        else
            changed = selectedClients.remove(client);

        if (changed) {
            client.setSelected(select);
            fireIDMapperChange();
        }
    }

    public  boolean isClientSelected(IDMapperClient client) {
        return selectedClients.contains(client);
    }

    public  IDMapperStack selectedIDMapperStack() {
        cacheAndWait(waitSeconds);
        return selectedIDMapperStack;
    }

    /**
     *
     * @return supported source ID types by the selected resources
     */
    public  Set<DataSourceWrapper> getSupportedSrcTypes() {
        cacheAndWait(waitSeconds);
        return srcTypes;
    }

    /**
     *
     * @return supported target ID types by the selected resources
     */
    public  Set<DataSourceWrapper> getSupportedTgtTypes() {
        cacheAndWait(waitSeconds);
        return tgtTypes;
    }

    public  boolean isMappingSupported(DataSourceWrapper srcType, DataSourceWrapper tgtType) {
        cacheAndWait(waitSeconds);
        List<DataSourceWrapper> dsws = new ArrayList<DataSourceWrapper>(2);
        dsws.add(srcType);
        dsws.add(tgtType);
        return supportedMapping.contains(dsws);
    }

    public  void resetCache() {
        cacheStatus = CacheStatus.UNCACHED;
    }

    public  void reCache() {
        resetCache();
        cache();
    }

    public  void cacheAndWait(int seconds) {
        cache();
        if (cacheStatus == CacheStatus.CACHED)
            return;
        try {
            if (!executor.awaitTermination(seconds, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
            cacheStatus = CacheStatus.CACHED;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  void cache() {
        synchronized(cacheStatus) {
            if (cacheStatus == CacheStatus.CACHED)
                return;

            if (cacheStatus == CacheStatus.CACHING) {
                if (executor.isTerminated()) {
                    cacheStatus = CacheStatus.CACHED;
                }
                return;
            }

            cacheStatus = CacheStatus.CACHING;
        }

        selectedIDMapperStack = new IDMapperStack();
        srcTypes = Collections.synchronizedSet(new HashSet<DataSourceWrapper>());
        tgtTypes = Collections.synchronizedSet(new HashSet<DataSourceWrapper>());
        supportedMapping = Collections.synchronizedSet(new HashSet<List<DataSourceWrapper>>());

        try {
            executor = Executors.newCachedThreadPool();

            for (IDMapperClient client : selectedClients()) {
                final IDMapper idMapper = client.getIDMapper();
                if (idMapper==null)
                    continue;

                //selectedIDMapperStack
                selectedIDMapperStack.addIDMapper(idMapper);

                executor.execute(new Runnable() {
                    public void run() {
                        IDMapperCapabilities caps = idMapper.getCapabilities();

                        Set<DataSource> srcs, tgts;
                        try {
                            srcs = caps.getSupportedSrcDataSources();
                            tgts = caps.getSupportedTgtDataSources();
                        } catch (Exception e) {
                            e.printStackTrace();
                            return;
                        }

                        // srcTypes
                        if (srcs!=null) {
                            for (DataSource ds : srcs) {
                                srcTypes.add(DataSourceWrapper.getInstance(
                                        DataSourceUtil.getName(ds), DataSourceWrapper.DsAttr.DATASOURCE));
                            }
                        }

                        // tgtTypes
                        if (tgts!=null) {
                            for (DataSource ds : tgts) {
                                tgtTypes.add(DataSourceWrapper.getInstance(
                                        DataSourceUtil.getName(ds), DataSourceWrapper.DsAttr.DATASOURCE));
                            }
                        }

                        // mapping from type to type
                        if (srcs!=null && tgts!=null) {
                            for (DataSource src : srcs) {
                                for (DataSource tgt : tgts) {
                                    boolean spt = false;
                                    try {
                                        spt = caps.isMappingSupported(src, tgt);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    if (spt) {
                                        List<DataSourceWrapper> dsws = new ArrayList<DataSourceWrapper>(2);
                                        dsws.add(DataSourceWrapper.getInstance(
                                            DataSourceUtil.getName(src), DataSourceWrapper.DsAttr.DATASOURCE));
                                        dsws.add(DataSourceWrapper.getInstance(
                                            DataSourceUtil.getName(tgt), DataSourceWrapper.DsAttr.DATASOURCE));
                                        supportedMapping.add(dsws);
                                    }
                                }
                            }
                        }

                        // AttributeMapper
                        if (!(idMapper instanceof AttributeMapper))
                            return;

                        AttributeMapper attrMapper = (AttributeMapper)idMapper;
                        Set<String> attrs = null;
                        try {
                            attrs = attrMapper.getAttributeSet();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (attrs==null)
                            return;

                        for (String attr : attrs) {
                            DataSourceWrapper dsw = DataSourceWrapper.getInstance(attr, DataSourceWrapper.DsAttr.ATTRIBUTE);
                            if (attrMapper.isFreeAttributeSearchSupported()) {
                                srcTypes.add(dsw);
                                if (tgts!=null) {
                                    for (DataSource tgt : tgts) {
                                        List<DataSourceWrapper> dsws = new ArrayList<DataSourceWrapper>(2);
                                        dsws.add(dsw);
                                        dsws.add(DataSourceWrapper.getInstance(
                                            tgt.getFullName(), DataSourceWrapper.DsAttr.DATASOURCE));
                                        supportedMapping.add(dsws);
                                    }
                                }
                            }

                            tgtTypes.add(dsw);
                            if (srcs!=null) {
                                for (DataSource src : srcs) {
                                    List<DataSourceWrapper> dsws = new ArrayList<DataSourceWrapper>(2);
                                    dsws.add(DataSourceWrapper.getInstance(
                                        src.getFullName(), DataSourceWrapper.DsAttr.DATASOURCE));
                                    dsws.add(dsw);
                                    supportedMapping.add(dsws);
                                }
                            }
                        }
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            cacheStatus = CacheStatus.UNCACHED;
        }
    }
}
