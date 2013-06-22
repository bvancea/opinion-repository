/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package api.dao;

import api.dao.base.BaseDao;
import api.model.Community;
import java.util.List;

/**
 *
 * @author Alex Marchis
 */
public interface CommunityDao extends BaseDao<Community> {
    public List<Community> findByHolderName(final String holderName);
}
