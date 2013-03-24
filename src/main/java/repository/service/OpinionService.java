package repository.service;

import java.util.List;
import repository.model.Opinion;

import javax.transaction.NotSupportedException;

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
}