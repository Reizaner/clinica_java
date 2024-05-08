/*
 * Boton Nuevo
 */
package botones;

import static clinica.FrmSistema.iconos;

/**
 *
 * @author Leo
 */
public class BtnNew extends Btn {
    
    public BtnNew(){
        setText("Nuevo");
        setIcon(iconos.getAdd(16));
    }
}
