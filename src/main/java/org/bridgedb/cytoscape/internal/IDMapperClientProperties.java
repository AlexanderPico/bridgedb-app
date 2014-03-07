/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.bridgedb.cytoscape.internal;

import org.cytoscape.property.AbstractConfigDirPropsReader;
import org.cytoscape.property.CyProperty.SavePolicy;
import org.cytoscape.work.Tunable;

import java.util.Enumeration;
import java.util.Properties;

/**
 *
 * @author gjj
 */

public class IDMapperClientProperties extends AbstractConfigDirPropsReader 
        //implements TunableListener
{
    /**
     * Constructor.
     *
     * @param propertyPrefix String representing the prefix to be used
     *                       when pulling properties from the property
     *                       list.
     */
    public IDMapperClientProperties(String propertyPrefix) {
        super(propertyPrefix, FinalStaticValues.CLIENT_SESSION_PROPS, SavePolicy.CONFIG_DIR);
    }

    public IDMapperClientProperties(String propertyPrefix,
            IDMapperClientProperties idMapperProps) {
        this(propertyPrefix);
//
//        for (Tunable tunable : idMapperProps.getTunables()) {
//            add(tunable);
//        }
//
//        initializeProperties(); // save to props or set to tunables
//
////        String prefix = idMapperProps.getPrefix();
////        Properties props = CytoscapeInit.getProperties();
////
////        // Find all properties with this prefix
////        Enumeration iter = props.propertyNames();
////
////        while (iter.hasMoreElements()) {
////            String property = (String) iter.nextElement();
////
////            if (property.startsWith(prefix)) {
////                props.remove(property);
////            }
////        }
    }
//
//    /**
//     * saveProperties is used to add modified properties to the Cytoscape
//     * properties so they can be saved in the properties file.
//     *
//     */
//    public void saveProperties(Tunable tunable) {
//        if (!getTunables().contains(tunable)) return;
//
//        String prefix = getName();
//        Properties props = getProperties();
//        props.setProperty(prefix + tunable.getName(), tunable.getValue().toString());
//    }
//
//    public void release() {
//        String prefix = getName();
//        Properties props = getProperties();
//
//        // Find all properties with this prefix
//        Enumeration iter = props.propertyNames();
//
//        while (iter.hasMoreElements()) {
//            String property = (String) iter.nextElement();
//
//            if (property.startsWith(prefix)) {
//                props.remove(property);
//            }
//        }
//    }
//
////    public void tunableChanged(Tunable tunable) {
////        setProperty(tunable.getName(), tunable.getValue().toString());
////        //saveProperties();
////    }

}