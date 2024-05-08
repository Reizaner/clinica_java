/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package clinica;


public class Clinica {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        javax.swing.SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                FrmSistema frm = new FrmSistema();
                frm.setVisible(true);
            }
        });
    }
    
}
