/*
 * clase para manejar la informacion de cliente
 */
package entidades;


public class Paciente {

    private int id;
    private String lastName;
    private String name;
    private int document;
    private int id_obrasocial;
    private String email;
    private String movils;

    
    
    public Paciente(int id, String lastName, String name, int document, int id_obrasocial, String email, String movils){
        
        this.setId(id);
        this.setLastName(lastName);
        this.setName(name);
        this.setDocument(document);
        this.setId_obrasocial(id_obrasocial);
        this.setEmail(email);
        this.setMovils(movils);
    
    }

    public Paciente() {
        this.setId(0);
        this.setLastName("");
        this.setName("");
        this.setDocument(0);
        this.setId_obrasocial(0);
        this.setEmail("");
        this.setMovils("");
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDocument() {
        return document;
    }

    public void setDocument(int document) {
        this.document = document;
    }

    public int getId_obrasocial() {
        return id_obrasocial;
    }

    public void setId_obrasocial(int id_obrasocial) {
        this.id_obrasocial = id_obrasocial;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMovils() {
        return movils;
    }

    public void setMovils(String movils) {
        this.movils = movils;
    }
    
    public Object[] toObject() {
        Object[] info = new Object[]{getId(),
                          getLastName(),
                          getName(),
                          String.valueOf(getDocument()),
                          String.valueOf(getId_obrasocial()),
                          String.valueOf(getMovils()),
                          getEmail(),
        };
        return info;
    }
}
