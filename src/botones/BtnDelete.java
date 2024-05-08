/*
 * Boton Borrar
 */
package botones;

import static clinica.FrmSistema.iconos;


public class BtnDelete extends Btn {
    
    public BtnDelete(){
        setText("Borrar");
        setIcon(iconos.getDelete(16));
    }
}
