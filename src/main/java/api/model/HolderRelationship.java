/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package api.model;

import api.model.util.HolderConstants.RelationshipType;
import java.util.Date;

/**
 *
 * @author Alex Marchis
 */
public class HolderRelationship {
    private String holderName;
    private String holderWithWhomName;
    private int type;
    private Date timestamp;

    public String getHolderName() {
        return holderName;
    }

    public String getHolderWithWhomName() {
        return holderWithWhomName;
    }

    public int getType() {
        return type;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public void setHolderWithWhomName(String holderWithWhomName) {
        this.holderWithWhomName = holderWithWhomName;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    
    public void invertFollowRelationship(){
        String aux = holderName;
        holderName = holderWithWhomName;
        holderWithWhomName = aux;
        
        if(type == RelationshipType.FOLLOWS.ordinal()){
            type = RelationshipType.ISFOLLOWED.ordinal();
        } else if(type == RelationshipType.ISFOLLOWED.ordinal()) {
            type = RelationshipType.FOLLOWS.ordinal();
        }
        
    }
    
}
