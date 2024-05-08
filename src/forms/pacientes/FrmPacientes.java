/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package forms.pacientes;

import miselaneos.FrmIntern;
import static clinica.FrmSistema.iconos;
import datos.Pacientes;
import entidades.Paciente;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;


public class FrmPacientes extends FrmIntern {

    public static int id = 2;
    private DefaultTableModel modelo;
    private TableRowSorter<TableModel> orden;

    /**
     * Creates new form FrmPacientes
     */
    public FrmPacientes() {
        initComponents();
        _loadPacientes();
    }
    
     private void _initTable() {
        modelo = new DefaultTableModel();
        modelo.addColumn("#");
        modelo.addColumn("Apellido");
        modelo.addColumn("Nombre(s)");
        modelo.addColumn("Documento");
        modelo.addColumn("Obra Social");
        modelo.addColumn("Telefono");
        
        
        jTable.setRowSorter(null); //Elimino Filtro
        jTable.setModel(modelo);
        jTable.getColumnModel().getColumn(0).setPreferredWidth(40);
        jTable.getColumnModel().getColumn(1).setPreferredWidth(160);
        jTable.getColumnModel().getColumn(2).setPreferredWidth(160);
        jTable.getColumnModel().getColumn(3).setPreferredWidth(80);
        jTable.getColumnModel().getColumn(4).setPreferredWidth(160);
        jTable.getColumnModel().getColumn(5).setPreferredWidth(160);
 
    }
     private void _loadPacientes() {
        if (!btnNew.isEnabled()) {
            return;
        }
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                btnNew.setEnabled(false);
                btnEdit.setEnabled(false);
                btnDelete.setEnabled(false);
                btnUpdate.setEnabled(false);

                _initTable();

                progressPacientes.setValue(0);
                ArrayList<Paciente> PacientesList = new ArrayList<Paciente>();
                Pacientes cnx = new Pacientes();
                if (cnx.isOkConexion()) {

                    PacientesList = cnx.listPaciente("SELECT * FROM " + cnx.TABLA);

                    cnx.isCloseConexion();
                }
                progressPacientes.setMaximum(PacientesList.size());
                progressPacientes.setVisible(true);

                //modelo.getDataVector().clear();
                modelo.setRowCount(0); //Soluciona el problema de la Exception(java.lang.ArrayIndexOutOfBoundsException)

                for (int index = 0; index < PacientesList.size(); index++) {
                    Paciente c = PacientesList.get(index);
                    modelo.addRow(c.toObject());
                    progressPacientes.setValue(index);
                }

                progressPacientes.setVisible(false);
                progressPacientes.setValue(0);

                setTitle("Pacientes...cantidad:" + PacientesList.size());

                _filter(txtFilter.getText().trim()); //Aplico Filtro luego de cargar todo
                
                btnNew.setEnabled(true);
                btnEdit.setEnabled(true);
                btnDelete.setEnabled(true);
                btnUpdate.setEnabled(true);
            }
        });
        t.start();
        

    }
     
    private boolean _isValidate(Paciente c) {
        boolean isOk = false;

        if (c.getLastName().trim().isEmpty()) {
            showMessage("No Ha Ingresado Apellido. ");
            isOk = true;
        } else if (c.getName().trim().isEmpty()) {
            showMessage("No Ha Ingresado Nombre(s). ");
            isOk = true;
        } else if (c.getDocument() == 0) {
            showMessage("No Ha Ingresado Documento. ");
            isOk = true;
        }

    return !isOk;
}

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(pnlFicha, message, "Aviso", JOptionPane.WARNING_MESSAGE);
    }
     
     private Paciente _getPacienteForm() {
        Paciente c = new Paciente();

        c.setId(txtId.toEntero());
        c.setLastName(txtLastName.getText().trim());
        c.setName(txtName.getText().trim());
        c.setDocument(txtDocument.toEntero());
        c.setId_obrasocial(pnlComboObraSociales1._getObraSocialSelected().getId());
        c.setMovils(txtMovil.getText().trim());
        c.setEmail(txtEmail.getText().trim());

        return c;
    }
     
    private void _setForm(Paciente c) {
        txtId.setText(String.valueOf(c.getId()));
        txtLastName.setText(c.getLastName().trim());
        txtName.setText(c.getName().trim());
        txtDocument.setText(String.valueOf(c.getDocument()));
        pnlComboObraSociales1._setObraSocialSelected(c.getId_obrasocial());
        txtMovil.setText(c.getMovils().trim());
        txtEmail.setText(c.getEmail().trim());
        txtLastName.requestFocus();
    }
    
    private void _selectionRow() {
        try {
            int indexRow = jTable.getSelectedRow();
            int indexModel = jTable.convertRowIndexToModel(indexRow);

            int id = (int) modelo.getValueAt(indexModel, 0);

            Paciente c = new Paciente();
            Pacientes cnx = new Pacientes();
            if (cnx.isOkConexion()) {
                c = cnx.getPaciente(id);
                cnx.isCloseConexion();
            }

            _setForm(c);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    } 
     
     private void _filter(String texto){
        try{
            orden = new TableRowSorter<TableModel>(modelo);
            this.jTable.setRowSorter(orden);

            RowFilter<TableModel, Object> filtro = RowFilter.regexFilter(texto.trim());
            orden.setRowFilter(filtro);
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }    
    }
     
      private boolean _isNew() {
        boolean isOk = false;
        Paciente c = _getPacienteForm();
        if (_isValidate(c)) {
            //Esta en codiciones
            Pacientes cnx = new Pacientes();
            if (cnx.isOkConexion()) {
                isOk = cnx.isNew(c);
                if (isOk) {
                    cnx.isCloseConexion();
                } else {
                    cnx.isCancelConexion();
                }
            }
        }
        return isOk;
    }
      
      private boolean _isDelete() {
        boolean isOk = false;
        Paciente c = _getPacienteForm();
        if (_isValidate(c)) {
            //Esta en codiciones
            if (JOptionPane.showConfirmDialog(pnlFicha, "Desea Eliminar.", "Aviso", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                return isOk;
            }
            Pacientes cnx = new Pacientes();
            if (cnx.isOkConexion()) {
                isOk = cnx.isDelete(c);
                if (isOk) {
                    cnx.isCloseConexion();
                } else {
                    cnx.isCancelConexion();
                }
            }
        }
        return isOk;
    }
      private boolean _isUpdate() {
        boolean isOk = false;
        Paciente c = _getPacienteForm();
        if (_isValidate(c)) {
            //Esta en codiciones
            Pacientes cnx = new Pacientes();
            if (cnx.isOkConexion()) {
                isOk = cnx.isUpdate(c);
                if (isOk) {
                    cnx.isCloseConexion();
                } else {
                    cnx.isCancelConexion();
                }
            }
        }
        return isOk;
    }
     

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane = new javax.swing.JTabbedPane();
        pnlListPacientes = new javax.swing.JPanel();
        pnlFilList = new javax.swing.JPanel();
        lblFilter1 = new etiquetas.LblFilter();
        txtFilter = new texto.TxtMayusculas();
        btnUpdate = new botones.BtnUpdate();
        pnlList = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        pnlFicha = new javax.swing.JPanel();
        txtId = new texto.TxtNro();
        txtDocument = new texto.TxtNro();
        id1 = new etiquetas.Id();
        lblCheck1 = new etiquetas.LblCheck();
        lblCheck2 = new etiquetas.LblCheck();
        lblCheck3 = new etiquetas.LblCheck();
        txtLastName = new texto.TxtMayusculas();
        txtName = new texto.TxtMayusculas();
        txtMovil = new texto.TxtFormato1Ejem();
        lbl2 = new etiquetas.Lbl();
        txtEmail = new texto.TxtMinusculas();
        lbl4 = new etiquetas.Lbl();
        pnlComboObraSociales1 = new forms.obrasocial.pnlComboObraSociales();
        pnlButtons = new javax.swing.JPanel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(32767, 10));
        btnNew = new botones.BtnNew();
        btnEdit = new botones.BtnEdit();
        btnDelete = new botones.BtnDelete();
        progressPacientes = new javax.swing.JProgressBar();

        setTitle("Pacientes");
        setFrameIcon(iconos.getClient(16)
        );
        getContentPane().setLayout(new java.awt.BorderLayout());

        jTabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPaneStateChanged(evt);
            }
        });

        pnlListPacientes.setLayout(new java.awt.BorderLayout());

        pnlFilList.setPreferredSize(new java.awt.Dimension(765, 40));

        txtFilter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFilterKeyReleased(evt);
            }
        });

        btnUpdate.setText("");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlFilListLayout = new javax.swing.GroupLayout(pnlFilList);
        pnlFilList.setLayout(pnlFilListLayout);
        pnlFilListLayout.setHorizontalGroup(
            pnlFilListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFilListLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblFilter1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 474, Short.MAX_VALUE)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlFilListLayout.setVerticalGroup(
            pnlFilListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFilListLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(pnlFilListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblFilter1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlListPacientes.add(pnlFilList, java.awt.BorderLayout.NORTH);

        pnlList.setLayout(new java.awt.BorderLayout());

        jTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableMouseClicked(evt);
            }
        });
        jTable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTableKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(jTable);

        pnlList.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pnlListPacientes.add(pnlList, java.awt.BorderLayout.CENTER);

        jTabbedPane.addTab("Lista", iconos.getList(16)
            , pnlListPacientes);

        pnlFicha.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtId.setEditable(false);

        lblCheck1.setText("Apellido:");

        lblCheck2.setText("Nombre(s):");

        lblCheck3.setText("Documento:");

        txtLastName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLastNameActionPerformed(evt);
            }
        });

        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameActionPerformed(evt);
            }
        });

        lbl2.setText("Nro. de Tel. / Cel.:");

        txtEmail.setLenghtText(45);

        lbl4.setText("E-Mail:");

        javax.swing.GroupLayout pnlFichaLayout = new javax.swing.GroupLayout(pnlFicha);
        pnlFicha.setLayout(pnlFichaLayout);
        pnlFichaLayout.setHorizontalGroup(
            pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFichaLayout.createSequentialGroup()
                .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFichaLayout.createSequentialGroup()
                        .addGap(109, 109, 109)
                        .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtId, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtName, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtLastName, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 180, Short.MAX_VALUE))
                    .addGroup(pnlFichaLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlFichaLayout.createSequentialGroup()
                                .addComponent(lbl4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(51, 51, 51)
                                .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(pnlFichaLayout.createSequentialGroup()
                                .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlFichaLayout.createSequentialGroup()
                                        .addComponent(lbl2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(txtMovil, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnlFichaLayout.createSequentialGroup()
                                        .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(id1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblCheck1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblCheck2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblCheck3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(13, 13, 13)
                                        .addComponent(txtDocument, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(pnlComboObraSociales1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        pnlFichaLayout.setVerticalGroup(
            pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFichaLayout.createSequentialGroup()
                .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFichaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(id1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCheck1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15)
                        .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCheck2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19)
                        .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCheck3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDocument, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlFichaLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(pnlComboObraSociales1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFichaLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(lbl2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtMovil, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlFichaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(158, Short.MAX_VALUE))
        );

        pnlButtons.setLayout(new java.awt.GridLayout(5, 1, 5, 5));
        pnlButtons.add(filler1);

        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });
        pnlButtons.add(btnNew);

        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        pnlButtons.add(btnEdit);

        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        pnlButtons.add(btnDelete);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlFicha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlButtons, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(pnlButtons, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(pnlFicha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane.addTab("Ficha Pacientes", jPanel2);

        getContentPane().add(jTabbedPane, java.awt.BorderLayout.CENTER);

        progressPacientes.setVisible(false);
        getContentPane().add(progressPacientes, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        _loadPacientes();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void txtFilterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFilterKeyReleased
        _filter(txtFilter.getText().trim());
    }//GEN-LAST:event_txtFilterKeyReleased

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        if (_isNew()) {
            _setForm(new Paciente());
            //_loadClients();
        }
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        if (!btnEdit.isEnabled()) {
            return;
        }
        if (_isUpdate()) {
            JOptionPane.showMessageDialog(pnlFicha, "Registro Actualizado.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            //_loadClients();
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (!btnDelete.isEnabled()) {
            return;
        }
        if (_isDelete()) {
            _setForm(new Paciente());
            //_loadClients();
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void txtLastNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLastNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLastNameActionPerformed

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameActionPerformed

    private void jTableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableKeyReleased
        _selectionRow();
    }//GEN-LAST:event_jTableKeyReleased

    private void jTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMouseClicked
        _selectionRow();
    }//GEN-LAST:event_jTableMouseClicked

    private void jTabbedPaneStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPaneStateChanged
        // Si elige la opcion de lista cargar toda la lista
        int option = jTabbedPane.getSelectedIndex();
        if (option == 0) {
            _loadPacientes();
        }
    }//GEN-LAST:event_jTabbedPaneStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private botones.BtnDelete btnDelete;
    private botones.BtnEdit btnEdit;
    private botones.BtnNew btnNew;
    private botones.BtnUpdate btnUpdate;
    private javax.swing.Box.Filler filler1;
    private etiquetas.Id id1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane;
    private javax.swing.JTable jTable;
    private etiquetas.Lbl lbl2;
    private etiquetas.Lbl lbl4;
    private etiquetas.LblCheck lblCheck1;
    private etiquetas.LblCheck lblCheck2;
    private etiquetas.LblCheck lblCheck3;
    private etiquetas.LblFilter lblFilter1;
    private javax.swing.JPanel pnlButtons;
    private forms.obrasocial.pnlComboObraSociales pnlComboObraSociales1;
    private javax.swing.JPanel pnlFicha;
    private javax.swing.JPanel pnlFilList;
    private javax.swing.JPanel pnlList;
    private javax.swing.JPanel pnlListPacientes;
    private javax.swing.JProgressBar progressPacientes;
    private texto.TxtNro txtDocument;
    private texto.TxtMinusculas txtEmail;
    private texto.TxtMayusculas txtFilter;
    private texto.TxtNro txtId;
    private texto.TxtMayusculas txtLastName;
    private texto.TxtFormato1Ejem txtMovil;
    private texto.TxtMayusculas txtName;
    // End of variables declaration//GEN-END:variables
}
