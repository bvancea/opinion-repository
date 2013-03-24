package repository.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.dao.OpinionDao;
import repository.model.Opinion;
import repository.service.OpinionService;

import javax.transaction.NotSupportedException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 3/17/13
 * Time: 10:40 PM
 *
 * Business logic for dealing with opinions.
 *
 * //Todo add functionality
 */
@Service
public class OpinionServiceImpl implements OpinionService {

    @Autowired
    OpinionDao opinionDao;

    @Override
    public List<Opinion> findAllOpinions() throws NotSupportedException {
        return opinionDao.findAll();
    }

    @Override
    public Opinion addOpinion(Opinion opinion) throws NotSupportedException {
        return opinionDao.save(opinion);
    }
}
