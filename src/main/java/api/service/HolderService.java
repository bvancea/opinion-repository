/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package api.service;

import api.model.Holder;
import api.model.HolderRelationship;
import java.util.List;
import javax.transaction.NotSupportedException;

/**
 *
 * @author Alex Marchis
 */
public interface HolderService {
    
    public List<Holder> findAll() throws NotSupportedException;
    public Holder add(Holder holder) throws NotSupportedException;
    public HolderRelationship addRelationship(HolderRelationship hrel) throws NotSupportedException;
    public Holder findByName(String name) throws NotSupportedException;
    
}
