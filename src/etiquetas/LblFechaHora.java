/*
 * SuperClase para etiquetas o JLabel
 */
package etiquetas;

import javax.swing.JLabel;

import static sistema_clinica_araujo.JFrame_Sistema.iconos;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author pablo
 * 
 * Aqui se instancia la clase calendar y se le da el formato para despues mostrarlo
 */
public class LblFechaHora extends JLabel implements Runnable {

    public LblFechaHora() {
        setText("Fecha Hora");
        setIcon(iconos.getClock(16));
    }

    @Override
    public void run() {
        while (true) {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat sfecha = new SimpleDateFormat("dd/MM/yyyy");

            setText("Hora: " + sf.format(c.getTime()) + "  Fecha: " + sfecha.format(c.getTime()));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                
            }
        }
    }

}
