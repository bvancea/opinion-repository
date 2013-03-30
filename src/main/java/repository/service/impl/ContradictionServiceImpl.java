package repository.service.impl;

import repository.model.Contradiction;
import repository.model.Opinion;
import repository.service.ContradictionService;

import javax.transaction.NotSupportedException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 3/25/13
 * Time: 11:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class ContradictionServiceImpl implements ContradictionService {

    @Override
    public List<Contradiction> findAllContradictions() throws NotSupportedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Opinion addContradiction(Contradiction contradiction) throws NotSupportedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
