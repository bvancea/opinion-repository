package api.dao;

import java.util.List;
import api.dao.base.BaseDao;
import api.model.Opinion;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 3/17/13
 * Time: 10:07 AM
 *
 * Opinion DAO.
 */
public interface OpinionDao extends BaseDao<Opinion> {

    public List<Opinion> findByHolderName(final String holderName);
}