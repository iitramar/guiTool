
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author iitramar
 */
public class Gui extends javax.swing.JFrame{

    /**
     * Creates new form Gui
     */
    public Gui() {
        initComponents();
        //architecture.setVisible(false);
        //mixingSpecs.setVisible(false);
        //algoClass.setVisible(false);
        //algorithms.setVisible(false);
        lblEx1.setVisible(false);
        lblEx2.setVisible(false);
        lblTarget.setVisible(false);
        lblPrecision.setVisible(false);
        txtEx1.setVisible(false);
        txtEx2.setVisible(false);
        txtTarget.setVisible(false);
        txtPrecision.setVisible(false);
        lblReactantVal.setVisible(false);
        lblBufferVal.setVisible(false);
        lblWasteVal.setVisible(false);
        lblOperationVal.setVisible(false);
        jScrollPane1.getColumnHeader().setVisible(false);
        jPanelStats.setVisible(false);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        for(int i=0;i<statTable.getColumnCount();i++){
            statTable.getColumnModel().getColumn(i).setCellRenderer( centerRenderer );
            statTable.getColumnModel().getColumn(i).setHeaderRenderer(centerRenderer);
        }
        setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelInput = new javax.swing.JPanel();
        btnSubmit = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        objective = new javax.swing.JComboBox<>();
        architecture = new javax.swing.JComboBox<>();
        mixingSpecs = new javax.swing.JComboBox<>();
        algoClass = new javax.swing.JComboBox<>();
        algorithms = new javax.swing.JComboBox<>();
        lblEx1 = new javax.swing.JLabel();
        lblEx2 = new javax.swing.JLabel();
        lblTarget = new javax.swing.JLabel();
        lblPrecision = new javax.swing.JLabel();
        txtEx1 = new javax.swing.JTextField();
        txtEx2 = new javax.swing.JTextField();
        txtTarget = new javax.swing.JTextField();
        txtPrecision = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jPanelStats = new javax.swing.JPanel();
        lblReactant = new javax.swing.JLabel();
        lblBuffer = new javax.swing.JLabel();
        lblWaste = new javax.swing.JLabel();
        lblOperation = new javax.swing.JLabel();
        lblReactantVal = new javax.swing.JLabel();
        lblBufferVal = new javax.swing.JLabel();
        lblWasteVal = new javax.swing.JLabel();
        lblOperationVal = new javax.swing.JLabel();
        jPanelGraph = new javax.swing.JPanel();
        jPanelInfo = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        statTable = new javax.swing.JTable();
        btnBrowse = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Frame");
        setBackground(java.awt.Color.gray);
        setForeground(java.awt.Color.darkGray);

        jPanelInput.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Input"));
        jPanelInput.setToolTipText("Input Pane");
        jPanelInput.setAutoscrolls(true);

        btnSubmit.setText("Submit");
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        btnReset.setText("Reset");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        objective.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Demo", "Stats", "Comparison" }));
        objective.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                objectiveActionPerformed(evt);
            }
        });

        architecture.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));
        architecture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                architectureActionPerformed(evt);
            }
        });

        mixingSpecs.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));
        mixingSpecs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mixingSpecsActionPerformed(evt);
            }
        });

        algoClass.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));
        algoClass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                algoClassActionPerformed(evt);
            }
        });

        algorithms.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select" }));
        algorithms.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                algorithmsActionPerformed(evt);
            }
        });

        lblEx1.setText("jLabel2");

        lblEx2.setText("jLabel3");

        lblTarget.setText("jLabel6");

        lblPrecision.setText("jLabel7");

        txtTarget.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTargetActionPerformed(evt);
            }
        });

        txtPrecision.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecisionActionPerformed(evt);
            }
        });

        jLabel1.setText("Objective");

        jLabel3.setText("Architecture");

        jLabel4.setText("Mixing Specifications");

        jLabel5.setText("Algorithm Class");

        jLabel6.setText("Algorithms");

        javax.swing.GroupLayout jPanelInputLayout = new javax.swing.GroupLayout(jPanelInput);
        jPanelInput.setLayout(jPanelInputLayout);
        jPanelInputLayout.setHorizontalGroup(
            jPanelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelInputLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelInputLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSubmit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnReset)
                        .addGap(24, 24, 24))
                    .addGroup(jPanelInputLayout.createSequentialGroup()
                        .addGroup(jPanelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblTarget)
                            .addComponent(lblPrecision))
                        .addGap(12, 12, 12)
                        .addGroup(jPanelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtTarget)
                            .addComponent(txtPrecision)))
                    .addGroup(jPanelInputLayout.createSequentialGroup()
                        .addGroup(jPanelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(28, 28, 28)
                        .addGroup(jPanelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(objective, 0, 125, Short.MAX_VALUE)
                            .addComponent(mixingSpecs, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(algoClass, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(algorithms, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(architecture, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelInputLayout.createSequentialGroup()
                        .addGroup(jPanelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEx1)
                            .addComponent(lblEx2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtEx1)
                            .addComponent(txtEx2))))
                .addContainerGap())
        );
        jPanelInputLayout.setVerticalGroup(
            jPanelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelInputLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(objective)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(architecture, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(mixingSpecs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelInputLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(algoClass)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(algorithms, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addGroup(jPanelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEx1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEx1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEx2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEx2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTarget, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTarget))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPrecision, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPrecision))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelInputLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSubmit)
                    .addComponent(btnReset))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jPanelStats.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Statistics"));

        lblReactant.setFont(new java.awt.Font("Ubuntu", 3, 18)); // NOI18N
        lblReactant.setText("Reactant");

        lblBuffer.setFont(new java.awt.Font("Ubuntu", 3, 18)); // NOI18N
        lblBuffer.setText("Buffer");

        lblWaste.setFont(new java.awt.Font("Ubuntu", 3, 18)); // NOI18N
        lblWaste.setText("Waste");

        lblOperation.setFont(new java.awt.Font("Ubuntu", 3, 18)); // NOI18N
        lblOperation.setText("Operation");

        lblReactantVal.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lblReactantVal.setText("jLabel2");

        lblBufferVal.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lblBufferVal.setText("jLabel3");

        lblWasteVal.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lblWasteVal.setText("jLabel6");

        lblOperationVal.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        lblOperationVal.setText("jLabel7");

        javax.swing.GroupLayout jPanelStatsLayout = new javax.swing.GroupLayout(jPanelStats);
        jPanelStats.setLayout(jPanelStatsLayout);
        jPanelStatsLayout.setHorizontalGroup(
            jPanelStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelStatsLayout.createSequentialGroup()
                .addGroup(jPanelStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblBufferVal)
                    .addGroup(jPanelStatsLayout.createSequentialGroup()
                        .addGroup(jPanelStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblOperation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblWaste, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblBuffer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblReactant, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanelStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelStatsLayout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(lblReactantVal))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelStatsLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanelStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblWasteVal, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblOperationVal, javax.swing.GroupLayout.Alignment.TRAILING))))))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanelStatsLayout.setVerticalGroup(
            jPanelStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelStatsLayout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addGroup(jPanelStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblReactant)
                    .addComponent(lblReactantVal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblBuffer)
                    .addComponent(lblBufferVal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblWaste)
                    .addComponent(lblWasteVal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelStatsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblOperation)
                    .addComponent(lblOperationVal)))
        );

        jPanelGraph.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Output"));
        jPanelGraph.setLayout(new java.awt.BorderLayout());

        statTable.setAutoCreateRowSorter(true);
        statTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "S.No", "Target", "Reactant", "Buffer", "Waste", "Operations", "MLB"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(statTable);

        javax.swing.GroupLayout jPanelInfoLayout = new javax.swing.GroupLayout(jPanelInfo);
        jPanelInfo.setLayout(jPanelInfoLayout);
        jPanelInfoLayout.setHorizontalGroup(
            jPanelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1115, Short.MAX_VALUE)
        );
        jPanelInfoLayout.setVerticalGroup(
            jPanelInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 711, Short.MAX_VALUE)
        );

        jPanelGraph.add(jPanelInfo, java.awt.BorderLayout.CENTER);

        btnBrowse.setText("Browse");
        btnBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseActionPerformed(evt);
            }
        });

        jLabel2.setText("Open Stats file");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 81, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBrowse))
                    .addComponent(jPanelInput, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanelStats, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(3, 3, 3)
                .addComponent(jPanelGraph, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanelInput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(jPanelStats, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnBrowse)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanelGraph, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void loadImage(String s){
        jPanelInfo.setVisible(false);
        File f = null;
        BufferedImage image = null;
        try {
            int width = 300, height = 200 ;
            System.out.println("Reading Image");
            f = new File(s);
            image = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
            image = ImageIO.read(f);
            System.out.println("Done!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        JLabel lblImage = new JLabel(new ImageIcon(image));
        jPanelGraph.add(lblImage, BorderLayout.CENTER);
        jPanelGraph.revalidate();
    }
    
    public void loadStat() throws IOException{
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("codos_Demo.txt"));
            String lastLine = "";
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) 
            {
                lastLine = sCurrentLine;
            }
            
            String a[] = lastLine.split("\t");
            lblReactantVal.setText(a[2]);
            lblBufferVal.setText(a[3]);
            lblWasteVal.setText(a[4]);
            lblOperationVal.setText(a[5]);
            lblReactantVal.setVisible(true);
            lblBufferVal.setVisible(true);
            lblWasteVal.setVisible(true);
            lblOperationVal.setVisible(true);
        }
        finally{
            br.close();
        }
        
    }
    
    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        // TODO add your handling code here:
        File f = new File("codos_Stat.txt");
        if(f.exists()){
            f.delete();
        }
        String obj = algorithms.getSelectedItem().toString();
        String obj1 = objective.getSelectedItem().toString();
        if(obj == "Codos"){
            codos codosObj = new codos();
            try {   
                    if(obj1 == "Demo" || obj1 == "Comparison"){
                        codosObj.runCodos(txtEx1.getText(), txtPrecision.getText(), txtTarget.getText(), "codos_Demo.txt");
                        TimeUnit.MILLISECONDS.sleep(1000);
                        String s = "codosDot.png";
                        loadStat();
                        loadImage(s);
                    }
                    else{
                        codosObj.runCodos(txtEx1.getText(), txtPrecision.getText());
                        BufferedReader br = new BufferedReader(new FileReader("codos_Stat.txt"));
                        String strLine;
                        //StringBuilder sb = new StringBuilder();
                        while ((strLine = br.readLine()) != null){
                            String[] s = strLine.split("\t");
                            Object[] ob = new Object[s.length];
                            for(int i=0; i< s.length; i++){
                                if(i == 1 || i == 3){
                                    ob[i] = s[i] ;
                                }
                                else{
                                    ob[i] = Integer.parseInt(s[i]) ;
                                }
                            }
                            DefaultTableModel tableModel = (DefaultTableModel)statTable.getModel();
                            tableModel.addRow(ob);
                       }
                        jScrollPane1.getColumnHeader().setVisible(true);
                        br.close();
                    }
    		} catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (InterruptedException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
    		}
        }
    }//GEN-LAST:event_btnSubmitActionPerformed
    
    private void objectiveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_objectiveActionPerformed
        // TODO add your handling code here
        String s = "Demo - To get Demo of an algorithm\nStats - To generate Statistics file for an algorithm\nComparison - To Compare any two algorithms";
        String obj = objective.getSelectedItem().toString();
        if(obj == "Demo"){
                jPanelStats.setVisible(true);
        }
        
        if(obj == "Demo" || obj == "Stats" || obj == "Comparison"){
            architecture.addItem("CMFB");
            architecture.addItem("DMFB");
            architecture.addItem("PMD");
            architecture.addItem("MEDA");
            architecture.setVisible(true);
        }
    }//GEN-LAST:event_objectiveActionPerformed

    private void architectureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_architectureActionPerformed
        // TODO add your handling code here:
        String obj = architecture.getSelectedItem().toString();
        if(obj == "CMFB"){
            mixingSpecs.addItem("Dilution");
        }
        else if(obj == "DMFB"){
            mixingSpecs.addItem("Dilution");
            mixingSpecs.addItem("Mixing");
        }
        else if(obj == "PMD"){
        }
        else if(obj == "MEDA"){
        }
        mixingSpecs.setVisible(true);
    }//GEN-LAST:event_architectureActionPerformed

    private void mixingSpecsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mixingSpecsActionPerformed
        // TODO add your handling code here:
        String comboBox1 = architecture.getSelectedItem().toString();
        String obj = mixingSpecs.getSelectedItem().toString();
        if(comboBox1 == "CMFB"){
            if(obj == "Dilution"){
                algoClass.addItem("SDST");
            }
        }
        else if(comboBox1 == "DMFB"){
            if(obj == "Dilution"){
                algoClass.addItem("SDST");
                algoClass.addItem("SDMT");
            }
            else if(obj == "Mixing"){
                algoClass.addItem("SDST");
                algoClass.addItem("SDMT");
            }
        }
        else if(comboBox1 == "PMD"){
            
        }
        else if(comboBox1 == "MEDA"){
            
        }
        algoClass.setVisible(true);
    }//GEN-LAST:event_mixingSpecsActionPerformed

    private void algoClassActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_algoClassActionPerformed
        // TODO add your handling code here:
        String comboBox1 = architecture.getSelectedItem().toString();
        String comboBox2 = mixingSpecs.getSelectedItem().toString();
        String obj = algoClass.getSelectedItem().toString();
        if(comboBox1 == "CMFB"){
            if(comboBox2 == "Dilution"){
                if(obj == "SDST"){
                    algorithms.addItem("Vospa");
                }
            }
        }
        else if(comboBox1 == "DMFB"){
            if(comboBox2 == "Dilution"){
                if(obj == "SDST"){
                    algorithms.addItem("Remia");
                    algorithms.addItem("DMRW");
                    algorithms.addItem("IDMA");
                    algorithms.addItem("Minmix");
                }
                else if(obj == "SDMT"){
                    algorithms.addItem("ExRemia");
                }
                else if(obj == "MDST"){
                    
                }
                else if(obj == "MDMT"){
                    
                }
            }
            else if(comboBox2 == "Mixing"){
                if(obj == "SDST"){
                    algorithms.addItem("Codos");
                }
                else if(obj == "SDMT"){
                    
                }
                else if(obj == "MDST"){
                    
                }
                else if(obj == "MDMT"){
                    
                }
            }
        }
        algorithms.setVisible(true);
    }//GEN-LAST:event_algoClassActionPerformed

    private void algorithmsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_algorithmsActionPerformed
        // TODO add your handling code here:
        String obj = algorithms.getSelectedItem().toString();
        String obj1 = objective.getSelectedItem().toString();
        if(obj == "Codos"){
            lblEx1.setText("Number of Reactants");
            lblEx1.setVisible(true);
            txtEx1.setVisible(true);
            if(obj1 == "Demo" || obj1 == "Comparison"){
                lblTarget.setText("Target Concentration");
                lblTarget.setVisible(true);
                txtTarget.setVisible(true);
            }
            lblPrecision.setText("Accuracy");
            lblPrecision.setVisible(true);
            txtPrecision.setVisible(true);
        }
    }//GEN-LAST:event_algorithmsActionPerformed

    private void txtTargetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTargetActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTargetActionPerformed

    private void txtPrecisionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecisionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecisionActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        // TODO add your handling code here:
        this.dispose();
        Gui gui = new Gui();
        gui.setVisible(true);
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseActionPerformed
        // TODO add your handling code here:
        try {
                Desktop.getDesktop().open(new File("codos_Stat.txt"));
            } catch (IOException e1) {

                e1.printStackTrace();
            }
    }//GEN-LAST:event_btnBrowseActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Gui().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> algoClass;
    private javax.swing.JComboBox<String> algorithms;
    private javax.swing.JComboBox<String> architecture;
    private javax.swing.JButton btnBrowse;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSubmit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelGraph;
    private javax.swing.JPanel jPanelInfo;
    private javax.swing.JPanel jPanelInput;
    private javax.swing.JPanel jPanelStats;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBuffer;
    private javax.swing.JLabel lblBufferVal;
    private javax.swing.JLabel lblEx1;
    private javax.swing.JLabel lblEx2;
    private javax.swing.JLabel lblOperation;
    private javax.swing.JLabel lblOperationVal;
    private javax.swing.JLabel lblPrecision;
    private javax.swing.JLabel lblReactant;
    private javax.swing.JLabel lblReactantVal;
    private javax.swing.JLabel lblTarget;
    private javax.swing.JLabel lblWaste;
    private javax.swing.JLabel lblWasteVal;
    private javax.swing.JComboBox<String> mixingSpecs;
    private javax.swing.JComboBox<String> objective;
    private javax.swing.JTable statTable;
    private javax.swing.JTextField txtEx1;
    private javax.swing.JTextField txtEx2;
    private javax.swing.JTextField txtPrecision;
    private javax.swing.JTextField txtTarget;
    // End of variables declaration//GEN-END:variables
}
