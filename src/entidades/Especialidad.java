/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

public class Especialidad {
    private int id;
    private String especialidad;

    public Especialidad(int id, String especialidad) {
        this.setId(id);
        this.setEspecialidad(especialidad);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
    public Object[] toObject() {
        Object[] info = new Object[]{getId(),getEspecialidad()};
        return info;
    }
}
