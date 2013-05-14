package api.service;

import java.util.List;
import api.model.Opinion;

import javax.transaction.NotSupportedException;
import api.controller.dto.OpinionQueryExDTO;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 3/17/13
 * Time: 10:38 PM
 * To change this template use File | Settings | File Templates.
 */

public interface OpinionService {

    public List<Opinion> findAllOpinions() throws NotSupportedException;
    public Opinion addOpinion(Opinion opinion) throws NotSupportedException;
    public List<Opinion> addOpinions(List<Opinion> opinions) throws NotSupportedException;
    public List<Opinion> addExpandedOpinions(OpinionQueryExDTO opinionsExpansion) throws NotSupportedException;
    public List<Opinion> findAllOpinionsByHolderName(String holderName) throws NotSupportedException;
    public List<Opinion> findAllOpinionsByHolderAndTarget(String holderName, String targetEntity) throws NotSupportedException;
    public List<Opinion> findAllOpinionsByEntityName(String entityName) throws NotSupportedException;
    public Opinion findById(String id) throws NotSupportedException;
    public List<Opinion> findAllUnexpanded() throws NotSupportedException;

    
}
