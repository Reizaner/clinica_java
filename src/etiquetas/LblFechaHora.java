/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package etiquetas;

import javax.swing.JLabel;

import static clinica.FrmSistema.iconos;
import java.text.SimpleDateFormat;
import java.util.Calendar;


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
