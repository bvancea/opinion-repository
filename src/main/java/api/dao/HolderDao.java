/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package api.dao;

import api.dao.base.BaseDao;
import api.model.Holder;
import api.model.HolderRelationship;

/**
 *
 * @author Alex Marchis
 */
public interface HolderDao extends BaseDao<Holder> {
    
    public HolderRelationship saveHolderRelationship(
            final HolderRelationship holderRelationship);
    
}
