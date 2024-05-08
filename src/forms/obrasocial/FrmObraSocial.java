/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package forms.obrasocial;

import miselaneos.FrmIntern;
import static clinica.FrmSistema.iconos;
import datos.ObraSocial;
import entidades.obraSocial;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;


public class FrmObraSocial extends FrmIntern {

   

    public static int id = 1;
    
    private DefaultTableModel modelo;
    private TableRowSorter<TableModel> orden;
    /**
     * Creates new form FrmLocalice
     */
    public FrmObraSocial() {
        initComponents();
        _initTable();
        _loadObraSocial();
    }

    private void _initTable() {
        modelo = new DefaultTableModel();
        modelo.addColumn("#");
        modelo.addColumn("Obra Social");
        jTable.setModel(modelo);
        jTable.getColumnModel().getColumn(0).setPreferredWidth(40);
        jTable.getColumnModel().getColumn(1).setPreferredWidth(200);
    }

    private void _loadObraSocial() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                btnNew.setEnabled(false);
                btnEdit.setEnabled(false);
                btnDelete.setEnabled(false);
                btnUpdate.setEnabled(false);

                progressObraSocial.setValue(0);
                ArrayList<obraSocial> ObraSocialList = new ArrayList<obraSocial>();
                ObraSocial cnx = new ObraSocial();
                if (cnx.isOkConexion()) {

                    ObraSocialList = cnx.listObraSocial("SELECT * FROM " + cnx.TABLA);

                    cnx.isCloseConexion();
                }
                progressObraSocial.setMaximum(ObraSocialList.size());
                progressObraSocial.setVisible(true);

                //modelo.getDataVector().clear();
                modelo.setRowCount(0); //Soluciona el problema de la Exception(java.lang.ArrayIndexOutOfBoundsException)

                for (int index = 0; index < ObraSocialList.size(); index++) {
                    obraSocial l = ObraSocialList.get(index);
                    modelo.addRow(l.toObject());
                    progressObraSocial.setValue(index);
                }

                progressObraSocial.setVisible(false);
                progressObraSocial.setValue(0);

                setTitle("Obra Social...cantidad:" + ObraSocialList.size());

                btnNew.setEnabled(true);
                btnEdit.setEnabled(true);
                btnDelete.setEnabled(true);
                btnUpdate.setEnabled(true);
            }
        });
        t.start();

    }

    private boolean _isValidate(obraSocial l) {
        // Bandera para indicar si la validación fue exitosa.
        boolean isOk = false;

        // Verificar si el nombre de la obra social no está vacío.
        if (!l.getObrasocial().trim().isEmpty()) {
            // La validación es exitosa.
            isOk = true;
        } else {
            // Mostrar un mensaje de advertencia si no se ha ingresado el nombre de la obra social.
            showMessage("No Ha Ingresado Obra Social.-");
        }

        // Devolver la bandera de validación.
        return isOk;
}

/*
  Método para mostrar un mensaje de advertencia en un panel específico.
 */
    private void showMessage(String message) {
        JOptionPane.showMessageDialog(pnlCentro, message, "Aviso", JOptionPane.WARNING_MESSAGE);
    }

    private obraSocial _getObrasocialForm() {
        obraSocial l = new obraSocial();

        l.setId(txtId.toEntero());
        l.setObrasocial(txtObraSocial.getText().trim());

        return l;
    }

    private void _setForm(obraSocial l) {
        txtId.setText(String.valueOf(l.getId()));
        txtObraSocial.setText(l.getObrasocial().trim());

        txtObraSocial.requestFocus();
    }

    private void _selectionRow() {
        int indexRow = jTable.getSelectedRow();
        int indexModel = jTable.convertRowIndexToModel(indexRow);

        int id = (int) modelo.getValueAt(indexModel, 0);
        String obraSocial = (String) modelo.getValueAt(indexModel, 1);

        obraSocial l = new obraSocial(id, obraSocial);

        _setForm(l);

    }

    private boolean _isNew() {
        boolean isOk = false;
        obraSocial l = _getObrasocialForm();
        if (_isValidate(l)) {
            //Esta en codiciones
            ObraSocial cnx = new ObraSocial();
            if (cnx.isOkConexion()) {
                isOk = cnx.isNew(l);
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
        obraSocial l = _getObrasocialForm();
        if (_isValidate(l)) {
            //Esta en codiciones
            ObraSocial cnx = new ObraSocial();
            if (cnx.isOkConexion()) {
                isOk = cnx.isUpdate(l);
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
        obraSocial l = _getObrasocialForm();
        if (_isValidate(l)) {
            //Esta en codiciones
            if (JOptionPane.showConfirmDialog(pnlCentro, "Desea Eliminar.", "Aviso", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                return isOk;
            }
            ObraSocial cnx = new ObraSocial();
            if (cnx.isOkConexion()) {
                isOk = cnx.isDelete(l);
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

        pnlCentro = new javax.swing.JPanel();
        pnlCentroGrid = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        txtFilter = new texto.TxtMayusculas();
        lblFilter1 = new etiquetas.LblFilter();
        btnUpdate = new botones.BtnUpdate();
        progressObraSocial = new javax.swing.JProgressBar();
        pnlObraSocial = new javax.swing.JPanel();
        pnlCentroObra = new javax.swing.JPanel();
        id1 = new etiquetas.Id();
        txtId = new texto.TxtNro();
        txtObraSocial = new texto.TxtMayusculas();
        lbl1 = new etiquetas.Lbl();
        pnlButton = new javax.swing.JPanel();
        btnNew = new botones.BtnNew();
        btnEdit = new botones.BtnEdit();
        btnDelete = new botones.BtnDelete();

        setTitle("OBRA SOCIAL");
        setFrameIcon(iconos.getObraSocial(16)
        );
        setPreferredSize(new java.awt.Dimension(754, 380));
        getContentPane().setLayout(new java.awt.BorderLayout());

        pnlCentro.setLayout(new java.awt.BorderLayout());

        pnlCentroGrid.setLayout(new java.awt.BorderLayout());

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

        pnlCentroGrid.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.setPreferredSize(new java.awt.Dimension(451, 40));

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(lblFilter1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 170, Short.MAX_VALUE)
                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblFilter1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        pnlCentroGrid.add(jPanel1, java.awt.BorderLayout.NORTH);

        progressObraSocial.setVisible(false);
        pnlCentroGrid.add(progressObraSocial, java.awt.BorderLayout.PAGE_END);

        pnlCentro.add(pnlCentroGrid, java.awt.BorderLayout.CENTER);

        pnlObraSocial.setBorder(javax.swing.BorderFactory.createTitledBorder("Obra Social"));
        pnlObraSocial.setPreferredSize(new java.awt.Dimension(320, 279));
        pnlObraSocial.setRequestFocusEnabled(false);
        pnlObraSocial.setVerifyInputWhenFocusTarget(false);

        txtId.setEditable(false);

        lbl1.setText("Obra Social");

        javax.swing.GroupLayout pnlCentroObraLayout = new javax.swing.GroupLayout(pnlCentroObra);
        pnlCentroObra.setLayout(pnlCentroObraLayout);
        pnlCentroObraLayout.setHorizontalGroup(
            pnlCentroObraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCentroObraLayout.createSequentialGroup()
                .addGroup(pnlCentroObraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(id1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCentroObraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCentroObraLayout.createSequentialGroup()
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 147, Short.MAX_VALUE))
                    .addComponent(txtObraSocial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlCentroObraLayout.setVerticalGroup(
            pnlCentroObraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCentroObraLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCentroObraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(id1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCentroObraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lbl1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtObraSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );

        pnlButton.setPreferredSize(new java.awt.Dimension(290, 400));

        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });
        pnlButton.add(btnNew);

        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });
        pnlButton.add(btnEdit);

        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        pnlButton.add(btnDelete);

        javax.swing.GroupLayout pnlObraSocialLayout = new javax.swing.GroupLayout(pnlObraSocial);
        pnlObraSocial.setLayout(pnlObraSocialLayout);
        pnlObraSocialLayout.setHorizontalGroup(
            pnlObraSocialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlCentroObra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(pnlButton, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        pnlObraSocialLayout.setVerticalGroup(
            pnlObraSocialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlObraSocialLayout.createSequentialGroup()
                .addComponent(pnlCentroObra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pnlCentro.add(pnlObraSocial, java.awt.BorderLayout.LINE_END);

        getContentPane().add(pnlCentro, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtObraSocialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtObraSocialActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtObraSocialActionPerformed

    private void txtFilterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFilterKeyReleased
        orden = new TableRowSorter<TableModel>(modelo);
        this.jTable.setRowSorter(orden);

        RowFilter<TableModel, Object> filtro = RowFilter.regexFilter(txtFilter.getText().trim());
        orden.setRowFilter(filtro);
    }//GEN-LAST:event_txtFilterKeyReleased

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        if (!btnUpdate.isEnabled()) {
            return;
        }
        _loadObraSocial();
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        if (_isNew()) {
            _setForm(new obraSocial());
            _loadObraSocial();
        }
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        if (!btnEdit.isEnabled()) {
            return;
        }
        if (_isUpdate()) {
            _loadObraSocial();
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (!btnDelete.isEnabled()) {
            return;
        }
        if (_isDelete()) {
            _loadObraSocial();
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void jTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMouseClicked
        _selectionRow();
    }//GEN-LAST:event_jTableMouseClicked

    private void jTableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableKeyReleased
        _selectionRow();
    }//GEN-LAST:event_jTableKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private botones.BtnDelete btnDelete;
    private botones.BtnEdit btnEdit;
    private botones.BtnNew btnNew;
    private botones.BtnUpdate btnUpdate;
    private etiquetas.Id id1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable;
    private etiquetas.Lbl lbl1;
    private etiquetas.LblFilter lblFilter1;
    private javax.swing.JPanel pnlButton;
    private javax.swing.JPanel pnlCentro;
    private javax.swing.JPanel pnlCentroGrid;
    private javax.swing.JPanel pnlCentroObra;
    private javax.swing.JPanel pnlObraSocial;
    private javax.swing.JProgressBar progressObraSocial;
    private texto.TxtMayusculas txtFilter;
    private texto.TxtNro txtId;
    private texto.TxtMayusculas txtObraSocial;
    // End of variables declaration//GEN-END:variables
}
