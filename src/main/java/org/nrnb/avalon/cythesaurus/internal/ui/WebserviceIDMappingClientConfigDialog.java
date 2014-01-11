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

package org.nrnb.avalon.cythesaurus.internal.ui;

import org.nrnb.avalon.cythesaurus.internal.IDMapperClient;
import org.nrnb.avalon.cythesaurus.internal.IDMapperClientImpl;
import org.nrnb.avalon.cythesaurus.internal.util.BridgeRestUtil;
import org.cytoscape.work.TaskMonitor;

//import cytoscape.task.ui.JTaskConfig;

import org.cytoscape.work.TaskManager;


import org.bridgedb.IDMapperException;
import org.bridgedb.webservice.biomart.BiomartStub;
import org.bridgedb.webservice.synergizer.SynergizerStub;

import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.Vector;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import org.cytoscape.work.AbstractTask;
import org.cytoscape.work.TaskIterator;

// TODO: seperate different web service into different panels
/**
 *
 * @author gjj
 */
public class WebserviceIDMappingClientConfigDialog extends javax.swing.JDialog {

    public enum ClientType {
        BRIDGEDB("BridgeDb web service"),
        BIOMART("BioMart web service"),
        PICR("PICR (Protein Identifier Cross-Reference) web service"),
        SYNERGIZER("Synergizer web service"),
        CRONOS("CRONOS web service")
                ;

        private ClientType(String desc) {
            this.desc = desc;
        }

        public String toString() {
            return desc;
        }

        private String desc;
    }

    private enum CronosOrganism {
        HSA("hsa","Homo sapiens"),
        MMU("mmu","Mus musculus"),
        RNO("rno","Rattus norvegicus"),
        BTA("bta","Bos taurus"),
        CFA("cfa","Canis familiaris"),
        DME("dme","Drosophila melanogaster")
        ;

        CronosOrganism(String threeLetter, String fullName) {
            this.threeLetter = threeLetter;
            this.fullName = fullName;
        }

        private String threeLetter;
        private String fullName;

        public String threeLetter() {
            return threeLetter;
        }

        public String toString() {
            return fullName;
        }
    }

    // add a new client
    public WebserviceIDMappingClientConfigDialog(javax.swing.JDialog parent, TaskManager taskManager, boolean modal) {
        super(parent, modal);
        this.taskManager = taskManager;
        initComponents();
//        postInit();
    }

//    // configure a existing client
//    public  WebserviceIDMappingClientConfigDialog(javax.swing.JDialog parent,
//            boolean modal, IDMapperClient client) {
//        super(parent, modal);
//        this.client = client;
//        if (client!=null) {
//            IDMapperWebservice idMapper = (IDMapperWebservice) client.getIDMapper();
//            if (idMapper==null) {
//
//            } else {
//                if (idMapper instanceof IDMapperBiomart) {
//                    connectBiomart();
//                } else if (idMapper instanceof IDMapperSynergizer) {
//                    connectSynergizer();
//                } else if (idMapper instanceof IDMapperPicrRest) {
//
//                }
//            }
//        }
//
//        //loadBiomartFilterFile(); //turn off filtering
//
//        initComponents();
//
//        postInit();
//
//    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        javax.swing.JPanel typePanel = new javax.swing.JPanel();
        typeComboBox = new javax.swing.JComboBox();
        infoButton = new javax.swing.JButton();
        bridgedbPanel = new javax.swing.JPanel();
        javax.swing.JPanel bridgedbBaseUrlPanel = new javax.swing.JPanel();
        bridgedbBaseUrlComboBox = new javax.swing.JComboBox();
        biomartPanel = new javax.swing.JPanel();
        javax.swing.JPanel chooseDBPanel = new javax.swing.JPanel();
        chooseDBComboBox = new javax.swing.JComboBox();
        javax.swing.JPanel chooseDatasetPanel = new javax.swing.JPanel();
        chooseDatasetComboBox = new javax.swing.JComboBox();
        javax.swing.JPanel biomartOpPanel = new javax.swing.JPanel();
        biomartOptionCheckBox = new javax.swing.JCheckBox();
        biomartAdvancedPanel = new javax.swing.JPanel();
        javax.swing.JPanel biomartBaseUrlPanel = new javax.swing.JPanel();
        bioMartBaseUrlComboBox = new javax.swing.JComboBox();
        picrPanel = new javax.swing.JPanel();
        javax.swing.JPanel picrOpPanel = new javax.swing.JPanel();
        picrOptionCheckBox = new javax.swing.JCheckBox();
        picrAdvancedPanel = new javax.swing.JPanel();
        picrOnlyActiveCheckBox = new javax.swing.JCheckBox();
        synergizerPanel = new javax.swing.JPanel();
        javax.swing.JPanel chooseAuthorityPanel = new javax.swing.JPanel();
        chooseAuthorityComboBox = new javax.swing.JComboBox();
        javax.swing.JPanel chooseSpeciesPanel = new javax.swing.JPanel();
        chooseSpeciesComboBox = new javax.swing.JComboBox();
        javax.swing.JPanel synergizerOpPanel = new javax.swing.JPanel();
        synergizerOptionCheckBox = new javax.swing.JCheckBox();
        synergizerAdvancedPanel = new javax.swing.JPanel();
        javax.swing.JPanel synergizerBaseUrlPanel = new javax.swing.JPanel();
        synergizerBaseUrlTextField = new javax.swing.JTextField();
        javax.swing.JButton synergizerBaseUrlButton = new javax.swing.JButton();
        cronosPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        cronosSpeciesComboBox = new javax.swing.JComboBox();
        javax.swing.JPanel okPanel = new javax.swing.JPanel();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Webservice-based ID Mapping Resources Configuration");
        getContentPane().setLayout(new java.awt.GridBagLayout());

        typePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Web Service Type"));
        typePanel.setLayout(new javax.swing.BoxLayout(typePanel, javax.swing.BoxLayout.LINE_AXIS));

        Vector option = new Vector();
        option.add("Please select");
        for (ClientType type : ClientType.values()) {
            option.add(type);
        }
        typeComboBox.setModel(new javax.swing.DefaultComboBoxModel(option));
        typeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeComboBoxActionPerformed(evt);
            }
        });
        typePanel.add(typeComboBox);

        infoButton.setText("Info");
        infoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                infoButtonActionPerformed(evt);
            }
        });
        typePanel.add(infoButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(typePanel, gridBagConstraints);

        bridgedbPanel.setLayout(new java.awt.GridBagLayout());
        bridgedbPanel.setVisible(false);

        bridgedbBaseUrlPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Base URL of BridgeDb web service"));
        bridgedbBaseUrlPanel.setLayout(new java.awt.GridBagLayout());

        bridgedbBaseUrlComboBox.setEditable(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        bridgedbBaseUrlPanel.add(bridgedbBaseUrlComboBox, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        bridgedbPanel.add(bridgedbBaseUrlPanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(bridgedbPanel, gridBagConstraints);

        biomartPanel.setLayout(new java.awt.GridBagLayout());
        biomartPanel.setVisible(false);

        chooseDBPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Mart/Database"));
        chooseDBPanel.setMinimumSize(new java.awt.Dimension(400, 48));
        chooseDBPanel.setPreferredSize(new java.awt.Dimension(400, 50));
        chooseDBPanel.setLayout(new javax.swing.BoxLayout(chooseDBPanel, javax.swing.BoxLayout.LINE_AXIS));

        chooseDBComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseDBComboBoxActionPerformed(evt);
            }
        });
        chooseDBPanel.add(chooseDBComboBox);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        biomartPanel.add(chooseDBPanel, gridBagConstraints);

        chooseDatasetPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Dataset"));
        chooseDatasetPanel.setLayout(new javax.swing.BoxLayout(chooseDatasetPanel, javax.swing.BoxLayout.LINE_AXIS));

        chooseDatasetPanel.add(chooseDatasetComboBox);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        biomartPanel.add(chooseDatasetPanel, gridBagConstraints);

        biomartOpPanel.setLayout(new java.awt.GridBagLayout());

        biomartOptionCheckBox.setText("Show advanced option");
        biomartOptionCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                biomartOptionCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        biomartOpPanel.add(biomartOptionCheckBox, gridBagConstraints);

        biomartAdvancedPanel.setLayout(new java.awt.GridBagLayout());
        biomartAdvancedPanel.setVisible(biomartOptionCheckBox.isSelected());

        biomartBaseUrlPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Base URL of BioMart"));
        biomartBaseUrlPanel.setLayout(new java.awt.GridBagLayout());

        DefaultComboBoxModel biomartComboBoxModel = new DefaultComboBoxModel();
        biomartComboBoxModel.addElement(new BioMartWrapper("BioMart (EBI UK)","http://www.biomart.org/biomart/martservice"));
        biomartComboBoxModel.addElement(new BioMartWrapper("Ensembl (EBI UK)","http://www.ensembl.org/biomart/martservice"));
        biomartComboBoxModel.addElement(new BioMartWrapper("HIGH THROUGHPUT GENE TARGETING AND TRAPPING (SANGER UK)","http://www.sanger.ac.uk/htgt/biomart/martservice"));
        biomartComboBoxModel.addElement(new BioMartWrapper("WORMBASE (CSHL US)","http://www.wormbase.org/biomart/martservice"));
        biomartComboBoxModel.addElement(new BioMartWrapper("REACTOME (CSHL US)","http://banon.cshl.edu:5555/biomart/martservice"));
        biomartComboBoxModel.addElement(new BioMartWrapper("DICTYBASE (NORTHWESTERN US)","http://www.dictybase.org/biomart/martservice"));
        biomartComboBoxModel.addElement(new BioMartWrapper("MCWMart (US)","http://rote.hmgc.mcw.edu:9999/biomart/martservice"));
        biomartComboBoxModel.addElement(new BioMartWrapper("HGNC (EBI UK)","http://www.genenames.org/biomart/martservice"));
        biomartComboBoxModel.addElement(new BioMartWrapper("PRIDE (EBI UK)","http://www.ebi.ac.uk/pride/biomart/martservice"));
        biomartComboBoxModel.addElement(new BioMartWrapper("INTERPRO (EBI UK)","http://www.ebi.ac.uk/interpro/biomart/martservice"));
        biomartComboBoxModel.addElement(new BioMartWrapper("UNIPROT (EBI UK)","http://www.ebi.ac.uk/uniprot/biomart/martservice"));
        biomartComboBoxModel.addElement(new BioMartWrapper("EURATMART (EBI UK)","http://www.ebi.ac.uk/euratools/biomart/martservice"));
        biomartComboBoxModel.addElement(new BioMartWrapper("PARAMECIUM GENOME (CNRS FRANCE)","http://paramecium.cgm.cnrs-gif.fr/biomart/martservice"));
        biomartComboBoxModel.addElement(new BioMartWrapper("EUREXPRESS (MRC EDINBURGH UK)","http://biomart.eurexpress.org/biomart/martservice"));
        biomartComboBoxModel.addElement(new BioMartWrapper("PEPSEEKER (UNIVERSITY OF MANCHESTER UK)","http://www.ispider.manchester.ac.uk/pepseeker/biomart/martservice"));
        biomartComboBoxModel.addElement(new BioMartWrapper("PANCREATIC EXPRESSION DATABAS (INSTITUTE OF CANCER UK","http://www.pancreasexpression.org/biomart/martservice"));
        biomartComboBoxModel.addElement(new BioMartWrapper("Human genome sequence","http://www.pancreasexpression.org/biomart/martservice"));
        biomartComboBoxModel.addElement(new BioMartWrapper("VectorBase","http://biomart.vectorbase.org/biomart/martservice"));
        biomartComboBoxModel.addElement(new BioMartWrapper("Phytozome","http://www.phytozome.net/biomart/martservice"));

        bioMartBaseUrlComboBox.setModel(biomartComboBoxModel);
        bioMartComboBoxEditor = new TextComboBoxEditor(biomartStub.defaultBaseURL);
        bioMartComboBoxEditor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bioMartBaseUrlComboBoxActionPerformed(evt);
            }
        });

        bioMartBaseUrlComboBox.setEditor(bioMartComboBoxEditor);
        bioMartBaseUrlComboBox.setEditable(true);
        bioMartBaseUrlComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bioMartBaseUrlComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        biomartBaseUrlPanel.add(bioMartBaseUrlComboBox, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        biomartAdvancedPanel.add(biomartBaseUrlPanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        biomartOpPanel.add(biomartAdvancedPanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        biomartPanel.add(biomartOpPanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(biomartPanel, gridBagConstraints);

        picrPanel.setVisible(false);
        picrPanel.setLayout(new java.awt.GridBagLayout());

        picrOpPanel.setLayout(new java.awt.GridBagLayout());

        picrOptionCheckBox.setText("Show advanced option");
        picrOptionCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                picrOptionCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        picrOpPanel.add(picrOptionCheckBox, gridBagConstraints);

        picrAdvancedPanel.setLayout(new java.awt.GridBagLayout());
        picrAdvancedPanel.setVisible(picrOptionCheckBox.isSelected());

        picrOnlyActiveCheckBox.setSelected(true);
        picrOnlyActiveCheckBox.setText("Use only active mappings (remove deleted mappings)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        picrAdvancedPanel.add(picrOnlyActiveCheckBox, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        picrOpPanel.add(picrAdvancedPanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        picrPanel.add(picrOpPanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(picrPanel, gridBagConstraints);

        synergizerPanel.setLayout(new java.awt.GridBagLayout());
        synergizerPanel.setVisible(false);

        chooseAuthorityPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Authority"));
        chooseAuthorityPanel.setMinimumSize(new java.awt.Dimension(400, 48));
        chooseAuthorityPanel.setPreferredSize(new java.awt.Dimension(400, 50));
        chooseAuthorityPanel.setLayout(new javax.swing.BoxLayout(chooseAuthorityPanel, javax.swing.BoxLayout.LINE_AXIS));

        chooseAuthorityComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseAuthorityComboBoxActionPerformed(evt);
            }
        });
        chooseAuthorityPanel.add(chooseAuthorityComboBox);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        synergizerPanel.add(chooseAuthorityPanel, gridBagConstraints);

        chooseSpeciesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Species"));
        chooseSpeciesPanel.setLayout(new javax.swing.BoxLayout(chooseSpeciesPanel, javax.swing.BoxLayout.LINE_AXIS));

        chooseSpeciesPanel.add(chooseSpeciesComboBox);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        synergizerPanel.add(chooseSpeciesPanel, gridBagConstraints);

        synergizerOpPanel.setLayout(new java.awt.GridBagLayout());

        synergizerOptionCheckBox.setText("Show advanced option");
        synergizerOptionCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                synergizerOptionCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        synergizerOpPanel.add(synergizerOptionCheckBox, gridBagConstraints);

        synergizerAdvancedPanel.setLayout(new java.awt.GridBagLayout());
        synergizerAdvancedPanel.setVisible(synergizerOptionCheckBox.isSelected());

        synergizerBaseUrlPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("URL of Synergizer Server"));
        synergizerBaseUrlPanel.setLayout(new java.awt.GridBagLayout());

        synergizerBaseUrlTextField.setText(SynergizerStub.defaultBaseURL);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        synergizerBaseUrlPanel.add(synergizerBaseUrlTextField, gridBagConstraints);

        synergizerBaseUrlButton.setText("Change");
        synergizerBaseUrlButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                synergizerBaseUrlButtonActionPerformed(evt);
            }
        });
        synergizerBaseUrlPanel.add(synergizerBaseUrlButton, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        synergizerAdvancedPanel.add(synergizerBaseUrlPanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        synergizerOpPanel.add(synergizerAdvancedPanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        synergizerPanel.add(synergizerOpPanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(synergizerPanel, gridBagConstraints);

        cronosPanel.setVisible(false);
        cronosPanel.setLayout(new java.awt.GridBagLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Organism"));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        cronosSpeciesComboBox.setModel(new javax.swing.DefaultComboBoxModel(CronosOrganism.values()));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(cronosSpeciesComboBox, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        cronosPanel.add(jPanel1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(cronosPanel, gridBagConstraints);

        okPanel.setLayout(new javax.swing.BoxLayout(okPanel, javax.swing.BoxLayout.LINE_AXIS));

        okButton.setText("   OK   ");
        okButton.setToolTipText("");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });
        okPanel.add(okButton);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });
        okPanel.add(cancelButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        getContentPane().add(okPanel, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void biomartOptionCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_biomartOptionCheckBoxActionPerformed
        biomartAdvancedPanel.setVisible(biomartOptionCheckBox.isSelected());
        this.pack();
}//GEN-LAST:event_biomartOptionCheckBoxActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        setVisible(false);
        this.dispose();
}//GEN-LAST:event_cancelButtonActionPerformed

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        if (verifyInput()) {
            cancelled = false;
            setVisible(false);
            this.dispose();
        }
}//GEN-LAST:event_okButtonActionPerformed

    private void chooseDBComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooseDBComboBoxActionPerformed
        this.setDatasetsCombo();
    }//GEN-LAST:event_chooseDBComboBoxActionPerformed

    private void picrOptionCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_picrOptionCheckBoxActionPerformed
        picrAdvancedPanel.setVisible(picrOptionCheckBox.isSelected());
        this.pack();
    }//GEN-LAST:event_picrOptionCheckBoxActionPerformed

    private void typeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typeComboBoxActionPerformed
        Object obj = typeComboBox.getSelectedItem();
        if (!(obj instanceof ClientType)) {
            bridgedbPanel.setVisible(false);
            biomartPanel.setVisible(false);
            synergizerPanel.setVisible(false);
            picrPanel.setVisible(false);
            cronosPanel.setVisible(false);
            infoButton.setEnabled(false);
        } else {
            ClientType type = (ClientType)obj;
            if (type == ClientType.BRIDGEDB) {
                bridgedbPanel.setVisible(true);
                biomartPanel.setVisible(false);
                synergizerPanel.setVisible(false);
                picrPanel.setVisible(false);
                cronosPanel.setVisible(false);
                initBridgeDb();
            } else if (type == ClientType.BIOMART) {
                bridgedbPanel.setVisible(false);
                biomartPanel.setVisible(true);
                synergizerPanel.setVisible(false);
                picrPanel.setVisible(false);
                cronosPanel.setVisible(false);
                initBiomart();
            } else if (type == ClientType.SYNERGIZER) {
                bridgedbPanel.setVisible(false);
                biomartPanel.setVisible(false);
                synergizerPanel.setVisible(true);
                picrPanel.setVisible(false);
                cronosPanel.setVisible(false);
                initSynergizer();
            } else if (type == ClientType.PICR) {
                bridgedbPanel.setVisible(false);
                biomartPanel.setVisible(false);
                synergizerPanel.setVisible(false);
                picrPanel.setVisible(true);
                cronosPanel.setVisible(false);
            } else if (type == ClientType.CRONOS) {
                bridgedbPanel.setVisible(false);
                biomartPanel.setVisible(false);
                synergizerPanel.setVisible(false);
                picrPanel.setVisible(false);
                cronosPanel.setVisible(true);
            }
            infoButton.setEnabled(true);
        }

        this.pack();
    }//GEN-LAST:event_typeComboBoxActionPerformed

    private void chooseAuthorityComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooseAuthorityComboBoxActionPerformed
        this.setSynergizerSpecies();
    }//GEN-LAST:event_chooseAuthorityComboBoxActionPerformed

    private void synergizerOptionCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_synergizerOptionCheckBoxActionPerformed
        synergizerAdvancedPanel.setVisible(synergizerOptionCheckBox.isSelected());
        this.pack();
    }//GEN-LAST:event_synergizerOptionCheckBoxActionPerformed

    private void synergizerBaseUrlButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_synergizerBaseUrlButtonActionPerformed
        String baseUrl = synergizerBaseUrlTextField.getText();
        if (baseUrl==null || baseUrl.length()==0) {
            int ret = JOptionPane.showConfirmDialog(this,
                    "Error: the Synergizer server URL is empty. \n" +
                    "Use default: "+SynergizerStub.defaultBaseURL+"?",
                    "Empty URL", JOptionPane.YES_NO_OPTION);
            if (ret==JOptionPane.YES_OPTION) {
                synergizerBaseUrlTextField.setText(SynergizerStub.defaultBaseURL);
                baseUrl = SynergizerStub.defaultBaseURL;
            } else {
                return;
            }
        }

        try {
            synergizerStub = SynergizerStub.getInstance(baseUrl);
        } catch (Exception e) {
            int ret = JOptionPane.showConfirmDialog(this,
                    "Error: failed to connect to the Synergizer server. \n" +
                    "Use default: "+SynergizerStub.defaultBaseURL+"?",
                    "Failed", JOptionPane.YES_NO_OPTION);
            if (ret==JOptionPane.YES_OPTION) {
                synergizerBaseUrlTextField.setText(SynergizerStub.defaultBaseURL);
                baseUrl = SynergizerStub.defaultBaseURL;
                try {
                    synergizerStub = SynergizerStub.getInstance(baseUrl);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error: failed to connect " +
                            "to the Synergizer server. \nPlease try later.");
                    return;
                }

            } else {
                return;
            }
        }

        chooseAuthorityComboBox.setModel(new DefaultComboBoxModel(
                getSynergizerAuthorities()));
        setSynergizerSpecies();
    }//GEN-LAST:event_synergizerBaseUrlButtonActionPerformed

    private void infoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_infoButtonActionPerformed
        ClientType type = (ClientType)typeComboBox.getSelectedItem();
        
//        if (type==ClientType.BRIDGEDB) {
//        	openBrowser.openURL("http://webservice.bridgedb.org/ ");
//        } else if (type==ClientType.BIOMART) {
//            OpenBrowser.openURL("http://www.biomart.org/");
//        } else if (type==ClientType.PICR) {
//            OpenBrowser.openURL("http://www.ebi.ac.uk/Tools/picr/");
//        } else if (type==ClientType.SYNERGIZER) {
//            OpenBrowser.openURL("http://llama.med.harvard.edu/synergizer/translate/");
//        } else if (type==ClientType.CRONOS) {
//            OpenBrowser.openURL("http://mips.helmholtz-muenchen.de/genre/proj/cronos/index.html");
//        }
    }//GEN-LAST:event_infoButtonActionPerformed

    private void bioMartBaseUrlComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bioMartBaseUrlComboBoxActionPerformed
        String baseUrl = bioMartComboBoxEditor.getURLstr();
        if (baseUrl==null || baseUrl.length()==0) {
            int ret = JOptionPane.showConfirmDialog(this,
                    "Error: the Biomart URL is empty. \n" +
                    "Use default: "+BiomartStub.defaultBaseURL+"?",
                    "Empty URL", JOptionPane.YES_NO_OPTION);
            if (ret==JOptionPane.YES_OPTION) {
                bioMartComboBoxEditor.setURLStr(BiomartStub.defaultBaseURL);
                baseUrl = BiomartStub.defaultBaseURL;
            } else {
                return;
            }
        }

        try {
            biomartStub = BiomartStub.getInstance(baseUrl);
        } catch (Exception e) {
            int ret = JOptionPane.showConfirmDialog(this,
                    "Error: failed to connect to the Biomart. \n" +
                    "Use default: "+BiomartStub.defaultBaseURL+"?",
                    "Failed", JOptionPane.YES_NO_OPTION);
            if (ret==JOptionPane.YES_OPTION) {
                bioMartComboBoxEditor.setURLStr(BiomartStub.defaultBaseURL);
                baseUrl = BiomartStub.defaultBaseURL;
                try {
                    biomartStub = BiomartStub.getInstance(baseUrl);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error: failed to connect to the Biomart. \n" +
                            "Please try later.");
                    return;
                }

            } else {
                return;
            }
        }

        setChooseDBComboBox();
        setDatasetsCombo();
    }//GEN-LAST:event_bioMartBaseUrlComboBoxActionPerformed

    private boolean verifyInput() {
        Object obj = typeComboBox.getSelectedItem();
        if (!(obj instanceof ClientType)) {
            JOptionPane.showMessageDialog(this, "Error: please select a web service.");
            return false;
        }

        // TODO: check more
        if (obj==ClientType.BIOMART) {
            if (biomartStub==null) {
                JOptionPane.showMessageDialog(this, "Error: failed to connect to a Biomart.");
                return false;
            }
        } else if (obj==ClientType.SYNERGIZER) {
            if (synergizerStub==null) {
                JOptionPane.showMessageDialog(this, "Error: failed to connect to Synergizer.");
                return false;
            }
        }

        return true;
    }

    public Vector<String> getSynergizerAuthorities() {
        if (!connectSynergizer()) {
            JOptionPane.showMessageDialog(this, "Failed to connect to Synergizer.");
            return null;
        }

        if (synergizerStub==null) {
            return new Vector();
        }

        Vector<String> auths;
        try {
            auths = new Vector(synergizerStub.availableAuthorities());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Failed to connect to Synergizer.");
            return new Vector();
        }

        Collections.sort(auths);
        return auths;
    }

    private void setMarts() {
        mapMartDisplayName = new HashMap();

        if (!connectBiomart()) {
            JOptionPane.showMessageDialog(this, "Failed to connect to BioMart.");
            return;
        }

        Set<String> martSet = new HashSet();

        if (biomartStub!=null) {
            try {
                martSet = biomartStub.availableMarts();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (String mart : martSet) {
            if (!databaseFilter.contains(mart)) {
                String displayName = biomartStub.martDisplayName(mart);
                String display = displayName;//+"("+mart+")";
                mapMartDisplayName.put(display, mart);
            }
        }
    }
    
    private void setChooseDBComboBox() {
        setMarts();
        Vector<String> marts = new Vector(mapMartDisplayName.keySet());
        
        Collections.sort(marts);

        chooseDBComboBox.setModel(new DefaultComboBoxModel(marts));
    }

    private void setSynergizerSpecies() {
        String auth = (String) chooseAuthorityComboBox.getSelectedItem();
        Vector<String> species = new Vector();
        try {
            species = new Vector(synergizerStub.availableSpecies(auth));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Collections.sort(species);
        this.chooseSpeciesComboBox.setModel(new DefaultComboBoxModel(species));
    }

    private boolean setDatasets() {
        mapDatasetDisplayName = new HashMap();
        String dbDisplay = (String) chooseDBComboBox.getSelectedItem();
        String db = mapMartDisplayName.get(dbDisplay);
        Set<String> datasets = new HashSet();
        try {
            datasets = biomartStub.availableDatasets(db);
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }

        try {
            for (String ds : datasets) {
                if (!datasetFilter.contains(ds)) {
                    String display = biomartStub.datasetDisplayName(db, ds);
                    mapDatasetDisplayName.put(display, ds);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private void setDatasetsCombo() {
//        final JTaskConfig jTaskConfig = new JTaskConfig();
//        jTaskConfig.setOwner(cytoscape.Cytoscape.getDesktop());
//        jTaskConfig.displayCloseButton(true);
//        jTaskConfig.displayCancelButton(false);
//        jTaskConfig.displayStatus(true);
//        jTaskConfig.setAutoDispose(true);
//        jTaskConfig.setMillisToPopup(100);

        final SetDataSetsTask task = new SetDataSetsTask(this);
        final TaskIterator ti = new TaskIterator(task);
        
        taskManager.execute(ti);
    }

    private boolean connectSynergizer() {
        if (synergizerStub!=null) {
            return true;
        }

        try {
            synergizerStub = SynergizerStub.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private boolean connectBiomart() {
        if (biomartStub!=null) {
            return true;
        }
        
        try {
            biomartStub = BiomartStub.getInstance();
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public IDMapperClient getIDMappingClient()
            throws IDMapperException, ClassNotFoundException {
        String[] strs = getSettings();
        String connStr = strs[0];
        String className = strs[1];
        String displayName = strs[2];

        return new IDMapperClientImpl
                        .Builder(connStr, className)
                        .displayName(displayName)
                        .build();
    }

    private String[] getSettings() {        
        ClientType type = (ClientType) typeComboBox.getSelectedItem();
        if (type==ClientType.BRIDGEDB) {
            String className = "org.bridgedb.webservice.bridgerest.BridgeRest";

            StringBuilder connString = new StringBuilder("idmapper-bridgerest:") ;
            StringBuilder displayName = new StringBuilder("BridgeDb");

            String baseurl = this.bridgedbComboBoxEditor.getURLstr();
            connString.append(baseurl);
            displayName.append(" ("+baseurl+")");
            return new String[]{connString.toString(), className, displayName.toString()};
        } else if (type==ClientType.BIOMART) {
            String className = "org.bridgedb.webservice.biomart.IDMapperBiomart";

            StringBuilder connString = new StringBuilder("idmapper-biomart:") ;
            StringBuilder displayName = new StringBuilder("BioMart");

            String baseurl = bioMartComboBoxEditor.getURLstr();
            connString.append(baseurl+"?");
            if (baseurl.compareTo(BiomartStub.defaultBaseURL)!=0) {
                displayName.append("("+baseurl+")");
            }

            String dbDisplay = (String) chooseDBComboBox.getSelectedItem();
            String db = mapMartDisplayName.get(dbDisplay);
            connString.append("mart="+db);
            displayName.append(":mart="+dbDisplay);

            String dsDisplay = (String) chooseDatasetComboBox.getSelectedItem();
            String ds = mapDatasetDisplayName.get(dsDisplay);
            connString.append("&dataset="+ds);
            displayName.append("&dataset="+dsDisplay);

            return new String[]{connString.toString(), className, displayName.toString()};
        } else if (type==ClientType.SYNERGIZER) {
            String className = "org.bridgedb.webservice.synergizer.IDMapperSynergizer";

            StringBuilder connString = new StringBuilder("idmapper-synergizer:") ;
            StringBuilder displayName = new StringBuilder("Synergizer");

            String baseurl = synergizerBaseUrlTextField.getText();
            connString.append(baseurl+"?");
            if (baseurl.compareTo(SynergizerStub.defaultBaseURL)!=0) {
                displayName.append("("+baseurl+")");
            }

            String auth = (String)chooseAuthorityComboBox.getSelectedItem();
            connString.append("authority="+auth);
            displayName.append(":authority="+auth);

            String species = (String) chooseSpeciesComboBox.getSelectedItem();
            connString.append("&species="+species);
            displayName.append("&species="+species);

            return new String[]{connString.toString(), className, displayName.toString()};
        } else if (typeComboBox.getSelectedItem()==ClientType.PICR) {
            String className = "org.bridgedb.webservice.picr.IDMapperPicrRest";
            boolean onlyActive = picrOnlyActiveCheckBox.isSelected();
            String connString = "idmapper-picr-rest:only-active="+(onlyActive?"true":"false");
            String displayName = "PICR: Protein Identifier Cross-Reference Service";
            return new String[]{connString, className, displayName};
        } else if (typeComboBox.getSelectedItem()==ClientType.CRONOS) {
            String className = "org.bridgedb.webservice.cronos.IDMapperCronos";
            CronosOrganism organism = ((CronosOrganism)cronosSpeciesComboBox.getSelectedItem());
            String connString = "idmapper-cronos:"+organism.threeLetter();
            String displayName = "CRONOS ("+organism.fullName+")";
            return new String[]{connString, className, displayName};
        } else {
            throw new java.lang.IllegalStateException();
        }
    }

    

    public boolean isCancelled() {
        return cancelled;
    }

//    private static final String FILTER_TXT = "/resources/biomart_dataset_filter.txt";
//    private void loadBiomartFilterFile() {
//        try {
//            InputStreamReader inFile = new InputStreamReader(this.getClass().getResource(FILTER_TXT).openStream());
//            BufferedReader inBuffer = new BufferedReader(inFile);
//
//            String line;
//
//            while ((line = inBuffer.readLine()) != null) {
//                    String[] parts = line.split("\\t", 2);
//
//                    if (parts.length!=2) continue;
//
//                    if (parts[0].compareTo("db")==0) {
//                        databaseFilter.add(parts[1]);
//                    } else if (parts[0].compareTo("ds")==0) {
//                        datasetFilter.add(parts[1]);
//                    }
//
//            }
//
//            inFile.close();
//            inBuffer.close();
//        } catch(IOException e) {
//            e.printStackTrace();
//        }
//    }

    private void initBridgeDb() {
        if (!bridgeRestInitialized) {
            List<String> orgs = BridgeRestUtil.supportedOrganismsNr(BridgeRestUtil.defaultBaseUrl);
            String[] orgUrls = new String[orgs.size()];
            int iorg = 0;
            for (String org : orgs) {
                orgUrls[iorg++] = BridgeRestUtil.defaultBaseUrl + "/" +org;
            }
            DefaultComboBoxModel bridgeComboBoxModel = new DefaultComboBoxModel(orgUrls);
            bridgedbBaseUrlComboBox.setModel(bridgeComboBoxModel);
            String textInEditor = orgUrls.length==0?"No organism available, please specify yours.":orgUrls[0];
            bridgedbComboBoxEditor = new TextComboBoxEditor(textInEditor);
            bridgedbBaseUrlComboBox.setEditor(bridgedbComboBoxEditor);
            //bridgedbBaseUrlComboBox.setSelectedItem("http://webservice.bridgedb.org/Human");
            bridgeRestInitialized = true;
        }
    }

    private void initBiomart() {
        if (!biomartInitialized) {
            setChooseDBComboBox();
            setDatasetsCombo();
            biomartInitialized = true;
        }
    }

    private void initSynergizer() {
        if (!synergizerInitialized) {
            chooseAuthorityComboBox.setModel(new DefaultComboBoxModel(getSynergizerAuthorities()));
            setSynergizerSpecies();
            synergizerInitialized = true;
        }
    }

    private final TaskManager taskManager;
    private Set<String> datasetFilter = new HashSet();
    private Set<String> databaseFilter = new HashSet();

    private BiomartStub biomartStub;
    private SynergizerStub synergizerStub;
    //private IDMapperClient client = null;
    //private IDMapperWebservice idMapper = null;
    private boolean cancelled = true;
    //private Map<ClientType, JPanel> mapTypePanel;

    private Map<String, String> mapMartDisplayName;
    private Map<String, String> mapDatasetDisplayName;

    private boolean biomartInitialized = false, synergizerInitialized = false, bridgeRestInitialized = false;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox bioMartBaseUrlComboBox;
    private TextComboBoxEditor bioMartComboBoxEditor;
    private javax.swing.JPanel biomartAdvancedPanel;
    private javax.swing.JCheckBox biomartOptionCheckBox;
    private javax.swing.JPanel biomartPanel;
    private javax.swing.JComboBox bridgedbBaseUrlComboBox;
    private TextComboBoxEditor bridgedbComboBoxEditor;
    private javax.swing.JPanel bridgedbPanel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JComboBox chooseAuthorityComboBox;
    private javax.swing.JComboBox chooseDBComboBox;
    private javax.swing.JComboBox chooseDatasetComboBox;
    private javax.swing.JComboBox chooseSpeciesComboBox;
    private javax.swing.JPanel cronosPanel;
    private javax.swing.JComboBox cronosSpeciesComboBox;
    private javax.swing.JButton infoButton;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton okButton;
    private javax.swing.JPanel picrAdvancedPanel;
    private javax.swing.JCheckBox picrOnlyActiveCheckBox;
    private javax.swing.JCheckBox picrOptionCheckBox;
    private javax.swing.JPanel picrPanel;
    private javax.swing.JPanel synergizerAdvancedPanel;
    private javax.swing.JTextField synergizerBaseUrlTextField;
    private javax.swing.JCheckBox synergizerOptionCheckBox;
    private javax.swing.JPanel synergizerPanel;
    private javax.swing.JComboBox typeComboBox;
    // End of variables declaration//GEN-END:variables

    private class SetDataSetsTask extends AbstractTask {
        private JDialog dialog;
        
        SetDataSetsTask(JDialog dialog) {
            this.dialog = dialog;
        }
        
		@Override
		public void cancel() {
			// could not be cancelled
		}

		@Override
		public void run(TaskMonitor taskMonitor) throws Exception {
			
                    try {
                            taskMonitor.setStatusMessage("Loading...");
                            taskMonitor.setProgress(0.00);
                            
                            if (setDatasets()) {
                                Vector<String> dss = new Vector(mapDatasetDisplayName.keySet());
                                Collections.sort(dss);

                                chooseDatasetComboBox.setModel(new DefaultComboBoxModel(dss));
                            } else {
                                chooseDatasetComboBox.setModel(new DefaultComboBoxModel());
                                JOptionPane.showMessageDialog(dialog, "Failed to connect to the selected mart.\n" +
                                        "Please select select another mart or change the base URL of BioMart.");
                                if (!biomartOptionCheckBox.isSelected()) {
                                    biomartOptionCheckBox.setSelected(true);
                                    biomartAdvancedPanel.setVisible(biomartOptionCheckBox.isSelected());
                                    dialog.pack();
                                }
                            }
                            
                            taskMonitor.setStatusMessage("Done");
                            taskMonitor.setProgress(1.00);
                    } catch (Exception e) {
                            taskMonitor.setProgress(1.00);
                            taskMonitor.setStatusMessage("failed.\n");
                            e.printStackTrace();
                    }
			
		}
    }

    private class TextComboBoxEditor implements javax.swing.ComboBoxEditor {
            private Object item;
            private javax.swing.JTextField tfInput;

            public TextComboBoxEditor(String defaultURL) {
                tfInput = new javax.swing.JTextField(defaultURL);
            }

            public String getURLstr() {
                    return tfInput.getText();
            }

            public void setURLStr(String urlStr) {
                    tfInput.setText(urlStr);
            }

            public void addActionListener(java.awt.event.ActionListener l) {
                    tfInput.addActionListener(l);
            }
//
//            public void addKeyListener(java.awt.event.KeyListener l) {
//                    tfInput.addKeyListener(l);
//            }

            public java.awt.Component getEditorComponent() {
                    return tfInput;
            }

            public Object getItem() {
                    return item;
            }

            public void removeActionListener(java.awt.event.ActionListener l) {
            }

            public void selectAll() {
            }

            public void setItem(Object anObject) {
                    if (anObject == null) {
                            return;
                    }

                    if (anObject instanceof String) {
                        item = anObject;
                        tfInput.setText((String)anObject);
                    } else  if (anObject instanceof BioMartWrapper) {
                        item = anObject;
                        tfInput.setText(((BioMartWrapper)item).getUrl());
                    }
            }
    } // BioMartComboBoxEditor

    private class BioMartWrapper {
        private String name;
        private String url;
        public BioMartWrapper(String name, String url) {
            this.name = name;
            this.url = url;
        }

        public String getUrl() {
            return url;
        }

        public String toString() {
            return name;
        }
    }

}