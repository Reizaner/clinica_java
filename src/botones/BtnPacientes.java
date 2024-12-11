package botones;

import static sistema_clinica_araujo.JFrame_Sistema.iconos;

/**
 *
 * @author OBREGON
 */
public class BtnPacientes extends Btn{
    public BtnPacientes(){
        setText("Pacientes");
        setIcon(iconos.getPaciente(32));
    }
}
