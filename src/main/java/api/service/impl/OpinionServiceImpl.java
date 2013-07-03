package api.service.impl;

import api.controller.dto.OpinionQueryExDTO;
import api.dao.OpinionDao;
import api.model.Opinion;
import api.model.OpinionResult;
import api.repository.OpinionRepository;
import api.service.OpinionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.NotSupportedException;
import java.util.*;

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
    public Opinion addOrUpdateOpinion(Opinion opinion) throws NotSupportedException {
        //opinionRepository.save(opinion);
        return opinionDao.save(opinion);
    }

   @Override
    public List<Opinion> addOrUpdateOpinions(List<Opinion> opinions) throws NotSupportedException {
        for (Opinion opinion : opinions) {
            opinionDao.save(opinion);
        }
        return opinions;
    }

    public Opinion addOpinion(Opinion opinion) throws NotSupportedException {
        //generate id
        String id = "ID" + Calendar.getInstance().getTimeInMillis();
        opinion.setId(id);
        String entity = opinion.getEntity();
        float weight = (float)1;
        opinion.getTargetExpansions().add(entity);
        opinion.getTargetExpansionWeights().add(weight);

        opinionDao.saveInIdTable(opinion);
        return opinionDao.save(opinion);
    }

    @Override
    public List<Opinion> addOpinions(List<Opinion> opinions) throws NotSupportedException {

        for (Opinion opinion : opinions) {
            //generate id
            String id = "ID" + opinions.indexOf(opinion) +Calendar.getInstance().getTimeInMillis();
            opinion.setId(id);
            opinionDao.save(opinion);
        }
        return opinions;
    }

    @Override
    public List<Opinion> addExpandedOpinions(OpinionQueryExDTO opinionsExpansion) throws NotSupportedException{

        OpinionResult opinionResult = new OpinionResult();

        Opinion originalOpinion = opinionsExpansion.getOriginalOpinion();

        opinionResult.setEntity(originalOpinion.getEntity());
        opinionResult.setHolder(originalOpinion.getHolder());

        ArrayList<Opinion> opinions = new ArrayList<Opinion>();

        opinions.add(originalOpinion);
        opinions.addAll(opinionsExpansion.getExpandedOpinions());

        opinionResult.setOpinions(opinions);

        for (Opinion opinion : opinionResult.getOpinions()) {
            opinion.setExpanded(1);
        }
        opinionDao.saveOpinionResult(opinionResult);
        return opinionResult.getOpinions();
    }

    @Override
    public List<Opinion> findAllOpinionsByHolderName(String holderName) throws NotSupportedException {
        return opinionDao.findByHolderName(holderName);
    }

    @Override
    public List<Opinion> findAllOpinionsByHolderAndTarget(String holderName, String targetEntity) throws NotSupportedException {
        return opinionDao.findByHolderAndTarget(holderName, targetEntity);
    }

    @Override
    public List<Opinion> findAllOpinionsByEntityName(String entityName) throws NotSupportedException {
        return null;  //ToDo implement this
    }

    @Override
    public Opinion findById(String id) throws NotSupportedException {
        return opinionDao.find(id);
    }

    @Override
    public List<Opinion> findAllUnexpanded() throws NotSupportedException {

        return opinionDao.findUnexpanded();
    }

    @Override
    public Opinion addExpansions(Opinion opinion) throws NotSupportedException {
        opinion.setExpanded(1);

        opinionDao.saveInIdTable(opinion);
        return opinionDao.save(opinion);

    }


}
