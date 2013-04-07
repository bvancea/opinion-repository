package repository.service;

import java.util.List;
import repository.model.AOpinion;

import javax.transaction.NotSupportedException;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * To change this template use File | Settings | File Templates.
 */

public interface AOpinionService {

    public List<AOpinion> findAllOpinions() throws NotSupportedException;
    public AOpinion addOpinion(AOpinion opinion) throws NotSupportedException;

}
