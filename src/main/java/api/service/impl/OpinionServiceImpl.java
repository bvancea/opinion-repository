package api.service.impl;

import api.repository.OpinionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import api.dao.OpinionDao;
import api.model.Opinion;
import api.service.OpinionService;

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

    @Autowired
    OpinionRepository opinionRepository;

    @Override
    public List<Opinion> findAllOpinions() throws NotSupportedException {
        return opinionDao.findAll();
    }

    @Override
    public Opinion addOpinion(Opinion opinion) throws NotSupportedException {
        opinionRepository.save(opinion);
        return opinionDao.save(opinion);
    }

    @Override
    public List<Opinion> findAllOpinionsByHolderName(String holderName) throws NotSupportedException {
        return opinionDao.findByHolderName(holderName);
    }

    @Override
    public List<Opinion> findAllOpinionsByEntityName(String entityName) throws NotSupportedException {
        return null;  //ToDo implement this
    }
}
