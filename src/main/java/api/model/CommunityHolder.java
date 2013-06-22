/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package api.model;

import java.util.Date;

/**
 *
 * @author Alex Marchis
 */
public class CommunityHolder {
    private String holderName;
    private double value;
    private Date timestamp;

    public String getHolderName() {
        return holderName;
    }

    public double getValue() {
        return value;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
      
}
