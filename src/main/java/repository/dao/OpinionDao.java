package repository.dao;

import java.util.List;
import repository.dao.base.BaseDao;
import repository.dao.base.BasePersistence;
import repository.model.Opinion;

import javax.transaction.NotSupportedException;
import java.util.Map;

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
    public List<Opinion> findByHolderAndTarget(final String holderName, final String targetEntity);
    public Opinion findById(final String id);
}
