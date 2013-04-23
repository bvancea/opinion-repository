package repository.service.impl;

import repository.model.Contradiction;
import repository.model.Opinion;
import repository.service.ContradictionService;

import javax.transaction.NotSupportedException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.dao.ContradictionDao;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 3/25/13
 * Time: 11:06 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ContradictionServiceImpl implements ContradictionService {

    @Autowired
    private ContradictionDao contradictionDao;
    
    @Override
    public List<Contradiction> findAllContradictions() throws NotSupportedException {
        return contradictionDao.findAll();
    }

    @Override
    public Contradiction addContradiction(Contradiction contradiction) throws NotSupportedException {
        return contradictionDao.save(contradiction);
    }
}
