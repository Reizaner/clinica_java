/*
 *Clase para mostrar un texto e icono asociado
 */
package etiquetas;

import static sistema_clinica_araujo.JFrame_Sistema.iconos;

/**
 *
 * @author pablo
 */
public class LblCheck extends Lbl{
    
    public LblCheck(){
        setText("Obligatorio:");
        setIcon(iconos.getCheck(16));
    }
    
}
