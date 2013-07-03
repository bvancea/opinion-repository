package api.service.impl;

import api.model.Opinion;
import api.repository.OpinionRepository;
import api.service.IndexedOpinionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Xenosky
 * Date: 6/23/13
 * Time: 1:07 AM
 * */
public class IndexedOpinionServiceImpl  implements IndexedOpinionService {


    @Autowired
    private OpinionRepository solrOpinionRepository;

    @Override
    public List<Opinion> findAll(int start, int end) {
        return solrOpinionRepository.findAll(new PageRequest(start, end)).getContent();
    }

    @Override
    public List<Opinion> findByHolderLike(String holderName) {
        return solrOpinionRepository.findByHolderLike(holderName);
    }

    @Override
    public List<Opinion> findByEntityLike(String target) {
         return solrOpinionRepository.findByEntityLike(target);
    }

    @Override
    public List<Opinion> findByHolderAndEntityLike(String holderName, String target) {
        return solrOpinionRepository.findByHolderAndEntityLike(holderName, target);
    }

    @Override
    public List<Opinion> addOrUpdateOpinions(List<Opinion> opinions) {
        solrOpinionRepository.save(opinions);

        return opinions;
    }
}
