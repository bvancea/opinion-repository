/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.model;

/**
 *
 * @author Alex Marchis
 */
public class ATarget {
    private String Entity;
    private String Attribute;

    public ATarget(String Entity, String Attribute) {
        this.Entity = Entity;
        this.Attribute = Attribute;
    }

    @Override
    public String toString() {
        if(this.Attribute == null){
            return Entity;
        }
        return Entity + "+" + Attribute;
    }

    public void setEntity(String Entity) {
        this.Entity = Entity;
    }

    public void setAttribute(String Attribute) {
        this.Attribute = Attribute;
    }

    public String getEntity() {
        return Entity;
    }

    public String getAttribute() {
        return Attribute;
    }
    
    
}
