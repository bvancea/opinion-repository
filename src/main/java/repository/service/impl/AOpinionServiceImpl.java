package repository.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.dao.AOpinionDao;
import repository.model.AOpinion;
import repository.service.AOpinionService;

import javax.transaction.NotSupportedException;


/**
 * Created with IntelliJ IDEA.
 * User: alex
 *
 * Business logic for dealing with opinions.
 *
 * //Todo add functionality
 */
@Service
public class AOpinionServiceImpl implements AOpinionService {

    @Autowired
    AOpinionDao opinionDao;
    
    @Override
    public List<AOpinion> findAllOpinions() throws NotSupportedException {
        return opinionDao.findAll();
    }
    
    @Override
    public AOpinion addOpinion(AOpinion opinion) throws NotSupportedException {
        return opinionDao.save(opinion);
    }

}
