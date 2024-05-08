/*
 * Boton Editar
 */
package botones;

import static clinica.FrmSistema.iconos;


public class BtnEdit extends Btn {
    
    public BtnEdit(){
        setText("Actualizar");
        setIcon(iconos.getEdit(16));
    }
}
