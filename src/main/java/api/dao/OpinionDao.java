package api.dao;

import java.util.List;
import api.dao.base.BaseDao;
import api.dao.base.BasePersistence;
import api.model.Opinion;

import javax.transaction.NotSupportedException;
import java.util.Map;
import api.model.OpinionResult;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 3/17/13
 * Time: 10:07 AM
 *
 * Opinion DAO.
 */
public interface OpinionDao extends BaseDao<Opinion> {

    public OpinionResult saveOpinionResult(final OpinionResult opinionResult);
    public List<Opinion> findByHolderName(final String holderName);
    public List<Opinion> findByHolderAndTarget(final String holderName, final String targetEntity);
    //public Opinion findById(final String id);
}
