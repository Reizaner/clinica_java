/*
 * CLASE que uso para saber si una ventana interna ya esta cargada
 */
package miselaneos;

import static clinica.FrmSistema.jDeskTopSis;
import forms.especilidades.FrmEspecialidades;
import forms.obrasocial.FrmObraSocial;
import forms.pacientes.FrmPacientes;
import java.awt.BorderLayout;
import java.util.HashMap;


public class GUI {

    private HashMap<Integer, FrmIntern> frmList;

    public GUI(){
        frmList = new HashMap<Integer, FrmIntern>();
    }
    
    /**
     * Metodo que recibe un valor int que seria el identificador de la venta
     * y tambien si voy a querer que se muestre 
     * @param id
     * @param isView 
     * @return  
     */
    
    public FrmObraSocial loadObraSocial(int id,boolean isView){
        FrmObraSocial frm = null;
        if(frmList.containsKey(id)){
            //si el id esta en la lista solo accedo al espacio/ubicacion de memoria del objeto
            frm = (FrmObraSocial) frmList.get(id);
        }else{
            //sino creo el objeto
            frm = new FrmObraSocial();
            //ingreso en la lista
            frmList.put(id,frm);
            //ingreeso en el objeto jDeskTopSis
            jDeskTopSis.add(frm, BorderLayout.CENTER);
        }
        if(isView){
            //quiere decir que voy a mostrar el objeto FrmObraSocial
            frm._show();
        }
        return frm; //hago que el metodo retorne el objeto solicitado por si necesito en el futuro.-
    }
    
    public FrmPacientes loadPacientes(int id,boolean isView){
        FrmPacientes frm = null;
        if(frmList.containsKey(id)){
            //si el id esta en la lista solo accedo al espacio/ubicacion de memoria del objeto
            frm = (FrmPacientes) frmList.get(id);
        }else{
            //sino creo el objeto
            frm = new FrmPacientes();
            //ingreso en la lista
            frmList.put(id,frm);
            //ingreeso en el objeto jDeskTopSis
            jDeskTopSis.add(frm, BorderLayout.CENTER);
        }
        if(isView){
            //quiere decir que voy a mostrar el objeto FrmObraSocial
            frm._show();
        }
        return frm; //hago que el metodo retorne el objeto solicitado por si necesito en el futuro.-
    }
    
    public FrmEspecialidades loadEspecialidades(int id,boolean isView){
        FrmEspecialidades frm = null;
        if(frmList.containsKey(id)){
            //si el id esta en la lista solo accedo al espacio/ubicacion de memoria del objeto
            frm = (FrmEspecialidades) frmList.get(id);
        }else{
            //sino creo el objeto
            frm = new FrmEspecialidades();
            //ingreso en la lista
            frmList.put(id,frm);
            //ingreeso en el objeto jDeskTopSis
            jDeskTopSis.add(frm, BorderLayout.CENTER);
        }
        if(isView){
            //quiere decir que voy a mostrar el objeto FrmObraSocial
            frm._show();
        }
        return frm; //hago que el metodo retorne el objeto solicitado por si necesito en el futuro.-
    }

}
