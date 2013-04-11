package api.service.impl;

import api.dao.OpinionDao;
import api.model.Opinion;
import api.repository.OpinionRepository;
import api.service.OpinionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.NotSupportedException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        //opinionRepository.save(opinion);
        return opinionDao.save(opinion);
    }

    @Override
    public List<Opinion> addOpinions(List<Opinion> opinions) throws NotSupportedException {
        for (Opinion opinion : opinions) {
            opinionDao.save(opinion);
        }
        return opinions;
    }

    @Override
    public List<Opinion> findAllOpinionsByHolderName(String holderName) throws NotSupportedException {
        return opinionDao.findByHolderName(holderName);
    }

    @Override
    public List<Opinion> findAllOpinionsByEntityName(String entityName) throws NotSupportedException {
        Map<String, Object> filterList = new HashMap<String, Object>();
        filterList.put("entity",entityName);
        return opinionDao.filterFind(filterList);
    }


}
