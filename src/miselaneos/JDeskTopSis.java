/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miselaneos;

import static clinica.FrmSistema.imgs;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JDesktopPane;


public class JDeskTopSis extends JDesktopPane {

    private Image backGround;

    public JDeskTopSis() {
        backGround = imgs.getBackGround();
    }
    
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(backGround, 0, 0,(int) getWidth(), (int) getHeight(), null);
    }
}
