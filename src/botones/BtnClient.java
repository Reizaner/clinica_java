/*
 * Boton Cliente
 */
package botones;

import static clinica.FrmSistema.iconos;

/**
 *
 * @author pablo
 */
public class BtnClient extends Btn{
    
    public BtnClient(){
        setText("Cliente");
        setIcon(iconos.getClient(16));
    }
    
}
