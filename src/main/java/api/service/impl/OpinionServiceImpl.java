package api.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import api.dao.OpinionDao;
import api.model.Opinion;
import api.service.OpinionService;

import javax.transaction.NotSupportedException;
import java.util.List;
import api.controller.dto.OpinionQueryExDTO;
import api.model.OpinionResult;

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
        //generate id
        String id = "ID" + Calendar.getInstance().getTimeInMillis();  
        opinion.setId(id);
        
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
        List<Opinion> allOpinions = findAllOpinions();
        List<Opinion> unexpandedOpinions = new ArrayList<Opinion>(); 
        for(Opinion opinion : allOpinions){
            if(opinion.getExpanded() == 0){
                unexpandedOpinions.add(opinion);
            }
        }
        
        return unexpandedOpinions;
    }
}
