/*
 * clase para manejar las obras sociales
 */
package entidades;


public class obraSocial {

    private int id;
    private String obrasocial;

    public obraSocial(int id, String obrasocial) {
        this.setId(id);
        this.setObrasocial(obrasocial);
    }

    public obraSocial() {
        this.setId(0);
        this.setObrasocial("");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getObrasocial() {
        return obrasocial;
    }

    public void setObrasocial(String obrasocial) {
        this.obrasocial = obrasocial;
    }

    public Object[] toObject() {
        Object[] info = new Object[]{getId(),getObrasocial()};
        return info;
    }
}
