package api.dao;

import api.dao.base.BaseDao;
import api.model.Opinion;
import api.model.OpinionResult;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 3/17/13
 * Time: 10:07 AM
 *
 * Opinion DAO.
 */
public interface OpinionDao extends BaseDao<Opinion> {

    public List<Opinion> save(Iterable<Opinion> opinions);

    public OpinionResult saveOpinionResult(final OpinionResult opinionResult);
    public Opinion saveInIdTable(final Opinion opinion);
    public List<Opinion> findByHolderName(final String holderName);
    public List<Opinion> findByHolderAndTarget(final String holderName, final String targetEntity);
    public List<Opinion> findUnexpanded();
}
