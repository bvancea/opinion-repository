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

    public List<Opinion> findByHolderName(final String holderName);
    public List<Opinion> save(Iterable<Opinion> opinions);

    OpinionResult saveOpinionResult(OpinionResult opinionResult);

    List<Opinion> findByHolderAndTarget(String holderName, String targetEntity);
}
