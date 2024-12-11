package forms.especialidades;

import datos.Especialidades;
import entidades.Especialidad;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import miselaneos.FrmIntern;
import static sistema_clinica_araujo.JFrame_Sistema.iconos;


/**
 *
 * @author OBREGON
 */
public class FrmEspecialidades extends FrmIntern {
    public static int id = 1;
    private DefaultTableModel modelo;
    private TableRowSorter<TableModel> orden;

    /**
     * Creates new form Frm_Pacientes
     */
    public FrmEspecialidades() {
        initComponents();
        _loadespecialidades();
    }
    
    private void _initTable() {
        modelo = new DefaultTableModel();
        modelo.addColumn("#");
        modelo.addColumn("Descripcion");
        jTable.setModel(modelo);
        jTable.getColumnModel().getColumn(0).setPreferredWidth(40);
        jTable.getColumnModel().getColumn(1).setPreferredWidth(200);
    }
    
    /**
     * Método para cargar las especialidades desde la base de datos.
     * Se ejecuta en un hilo separado para no bloquear la interfaz gráfica del usuario.
     * Desactiva los botones de nuevo, editar y eliminar mientras carga los datos y los vuelve a activar una vez completado.
     */
    private void _loadespecialidades() {
        if (!btnNew.isEnabled()) {
            return;
        }
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                btnNew.setEnabled(false);
                btnEdit.setEnabled(false);
                btnDelete.setEnabled(false);

                _initTable();

                progressEspecialidades.setValue(0);
                ArrayList<Especialidad> especialidadesList = new ArrayList<Especialidad>();
                Especialidades cnx = new Especialidades();

                if (cnx.isOkConexion()) {
                    especialidadesList = cnx.list("SELECT * FROM " + cnx.getTabla());
                    cnx.isCloseConexion();
                }

                progressEspecialidades.setMaximum(especialidadesList.size());
                progressEspecialidades.setVisible(true);

                modelo.setRowCount(0); // Soluciona el problema de la Exception(java.lang.ArrayIndexOutOfBoundsException)

                for (int index = 0; index < especialidadesList.size(); index++) {
                    Especialidad o = especialidadesList.get(index);
                    modelo.addRow(o.toObject());
                    progressEspecialidades.setValue(index);
                }

                progressEspecialidades.setVisible(false);
                progressEspecialidades.setValue(0);

                setTitle("Especialidades...cantidad:" + especialidadesList.size());

                btnNew.setEnabled(true);
                btnEdit.setEnabled(true);
                btnDelete.setEnabled(true);
            }
        });
        t.start();
    }

    /**
     * Método para validar los datos de una especialidad.
     * Verifica que la descripción de la especialidad no esté vacía.
     *
     * @param esp El objeto Especialidad a validar.
     * @return true si los datos son válidos, false de lo contrario.
     */
    private boolean _isValidate(Especialidad esp) {
        boolean isOk = false;

        if (esp.getDescripcion().trim().isEmpty()) {
            JOptionPane.showMessageDialog(pnlCentroEspecialidad, "No Ha Ingresado Localidad.-", "Aviso", JOptionPane.WARNING_MESSAGE);
            return isOk;
        }

        isOk = true;
        return isOk;
    }

    /**
     * Método para obtener los datos de una especialidad desde el formulario.
     *
     * @return Un objeto Especialidad con los datos del formulario.
     */
    private Especialidad _getEspecialidadForm() {
        Especialidad esp = new Especialidad(0, "");
        esp.setId(txtId.toEntero());
        esp.setDescripcion(txtEspecialidad.getText().trim());
        return esp;
    }

    /**
     * Método para establecer los datos de una especialidad en el formulario.
     *
     * @param esp El objeto Especialidad cuyos datos se establecerán en el formulario.
     */
    private void _setForm(Especialidad esp) {
        txtId.setText(String.valueOf(esp.getId()));
        txtEspecialidad.setText(esp.getDescripcion().trim());
        txtEspecialidad.requestFocus();
    }

    /**
     * Método para seleccionar una fila de la tabla y cargar los datos de la especialidad correspondiente en el formulario.
     */
    private void _selectionRow() {
        int indexRow = jTable.getSelectedRow();
        int indexModel = jTable.convertRowIndexToModel(indexRow);
        int id = (int) modelo.getValueAt(indexModel, 0);
        String descripcion = (String) modelo.getValueAt(indexModel, 1);
        Especialidad esp = new Especialidad(id, descripcion);
        _setForm(esp);
    }

    /**
     * Método para agregar una nueva especialidad a la base de datos.
     * Verifica si los datos de la especialidad son válidos antes de agregar.
     *
     * @return true si los datos se agregaron correctamente, false de lo contrario.
     */
    private boolean _isNew() {
        boolean isOk = false;
        Especialidad esp = _getEspecialidadForm();
        if (_isValidate(esp)) {
            Especialidades cnx = new Especialidades();
            if (cnx.isOkConexion()) {
                isOk = cnx.isNew(esp);
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
     * Método para actualizar los datos de una especialidad en la base de datos.
     * Verifica si los datos de la especialidad son válidos antes de actualizar.
     *
     * @return true si los datos se actualizaron correctamente, false de lo contrario.
     */
    private boolean _isUpdate() {
        boolean isOk = false;
        Especialidad esp = _getEspecialidadForm();
        if (_isValidate(esp)) {
            Especialidades cnx = new Especialidades();
            if (cnx.isOkConexion()) {
                isOk = cnx.isUpdate(esp);
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
     * Método para eliminar una especialidad de la base de datos.
     * Verifica si los datos de la especialidad son válidos y pide confirmación antes de eliminar.
     *
     * @return true si los datos se eliminaron correctamente, false de lo contrario.
     */
    private boolean _isDelete() {
        boolean isOk = false;
        Especialidad esp = _getEspecialidadForm();
        if (_isValidate(esp)) {
            if (JOptionPane.showConfirmDialog(pnlCentroEspecialidad, "Desea Eliminar.", "Aviso", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
                return isOk;
            }
            Especialidades cnx = new Especialidades();
            if (cnx.isOkConexion()) {
                isOk = cnx.isDelete(esp);
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

        pnlCentroGrid = new javax.swing.JPanel();
        pnlFiltrar = new javax.swing.JPanel();
        lblFilter1 = new etiquetas.LblFilter();
        txtFilter = new texto.TxtMayusculas();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable = new javax.swing.JTable();
        progressEspecialidades = new javax.swing.JProgressBar();
        pnlEspecialidad = new javax.swing.JPanel();
        pnlCentroEspecialidad = new javax.swing.JPanel();
        id1 = new etiquetas.Id();
        txtId = new texto.TxtNro();
        labelEsp = new etiquetas.Lbl();
        txtEspecialidad = new texto.TxtMayusculas();
        pnlButton = new javax.swing.JPanel();
        btnNew = new botones.BtnNew();
        btnEdit = new botones.BtnEdit();
        btnDelete = new botones.BtnDelete();

        setTitle("Especialidades");
        setFrameIcon(iconos.getEspecialidad(16));
        getContentPane().setLayout(new java.awt.BorderLayout());

        pnlCentroGrid.setLayout(new java.awt.BorderLayout());

        pnlFiltrar.setPreferredSize(new java.awt.Dimension(451, 30));

        txtFilter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtFilterKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout pnlFiltrarLayout = new javax.swing.GroupLayout(pnlFiltrar);
        pnlFiltrar.setLayout(pnlFiltrarLayout);
        pnlFiltrarLayout.setHorizontalGroup(
            pnlFiltrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFiltrarLayout.createSequentialGroup()
                .addComponent(lblFilter1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(160, Short.MAX_VALUE))
        );
        pnlFiltrarLayout.setVerticalGroup(
            pnlFiltrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFiltrarLayout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(pnlFiltrarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblFilter1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlCentroGrid.add(pnlFiltrar, java.awt.BorderLayout.NORTH);

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

        progressEspecialidades.setVisible(false);
        pnlCentroGrid.add(progressEspecialidades, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(pnlCentroGrid, java.awt.BorderLayout.CENTER);

        pnlEspecialidad.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        pnlEspecialidad.setPreferredSize(new java.awt.Dimension(320, 279));
        pnlEspecialidad.setLayout(new java.awt.BorderLayout());

        txtId.setEditable(false);

        labelEsp.setText("Especialidad");

        javax.swing.GroupLayout pnlCentroEspecialidadLayout = new javax.swing.GroupLayout(pnlCentroEspecialidad);
        pnlCentroEspecialidad.setLayout(pnlCentroEspecialidadLayout);
        pnlCentroEspecialidadLayout.setHorizontalGroup(
            pnlCentroEspecialidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCentroEspecialidadLayout.createSequentialGroup()
                .addGroup(pnlCentroEspecialidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(id1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelEsp, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCentroEspecialidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCentroEspecialidadLayout.createSequentialGroup()
                        .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 167, Short.MAX_VALUE))
                    .addComponent(txtEspecialidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlCentroEspecialidadLayout.setVerticalGroup(
            pnlCentroEspecialidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCentroEspecialidadLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlCentroEspecialidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(id1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlCentroEspecialidadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(labelEsp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtEspecialidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );

        pnlEspecialidad.add(pnlCentroEspecialidad, java.awt.BorderLayout.CENTER);

        pnlButton.setPreferredSize(new java.awt.Dimension(290, 40));

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

        pnlEspecialidad.add(pnlButton, java.awt.BorderLayout.SOUTH);

        getContentPane().add(pnlEspecialidad, java.awt.BorderLayout.LINE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtFilterKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFilterKeyReleased
        orden = new TableRowSorter<TableModel>(modelo);
        this.jTable.setRowSorter(orden);

        RowFilter<TableModel, Object> filtro = RowFilter.regexFilter(txtFilter.getText().trim());
        orden.setRowFilter(filtro);
    }//GEN-LAST:event_txtFilterKeyReleased

    private void jTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableMouseClicked
        _selectionRow();
    }//GEN-LAST:event_jTableMouseClicked

    private void jTableKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableKeyReleased
        _selectionRow();
    }//GEN-LAST:event_jTableKeyReleased

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
        if (_isNew()) {
            _setForm(new Especialidad());
            _loadespecialidades();
        }
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        if (!btnEdit.isEnabled()) {
            return;
        }
        if (_isUpdate()) {
            _loadespecialidades();
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (!btnDelete.isEnabled()) {
            return;
        }
        if (_isDelete()) {
            _loadespecialidades();
        }
    }//GEN-LAST:event_btnDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private botones.BtnDelete btnDelete;
    private botones.BtnEdit btnEdit;
    private botones.BtnNew btnNew;
    private etiquetas.Id id1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable;
    private etiquetas.Lbl labelEsp;
    private etiquetas.LblFilter lblFilter1;
    private javax.swing.JPanel pnlButton;
    private javax.swing.JPanel pnlCentroEspecialidad;
    private javax.swing.JPanel pnlCentroGrid;
    private javax.swing.JPanel pnlEspecialidad;
    private javax.swing.JPanel pnlFiltrar;
    private javax.swing.JProgressBar progressEspecialidades;
    private texto.TxtMayusculas txtEspecialidad;
    private texto.TxtMayusculas txtFilter;
    private texto.TxtNro txtId;
    // End of variables declaration//GEN-END:variables
}
