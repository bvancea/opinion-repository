package api.service.impl;

import api.dao.OpinionDao;
import api.model.Opinion;
import api.repository.OpinionRepository;
import api.service.ExpansionsService;
import api.service.OpinionService;
import org.apache.commons.collections.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shared.model.ExpansionType;
import shared.model.Expansions;
import shared.model.ExpansionsSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 5/5/13
 * Time: 4:01 PM
 *
 * Handle the update of the target/sentiment word expansions.
 *
 * ToDo refactor horrible code
 */
@Service
public class ExpansionsServiceImpl implements ExpansionsService {

    @Autowired
    private OpinionRepository solrOpinionRepository;

    @Autowired
    private OpinionDao opinionDao;

    @Autowired
    private OpinionService opinionService;

    @Override
    public Opinion updateExpansions(Expansions expansions) {

        String opinionId = String.valueOf(expansions.getOpinionId());
        Opinion opinion = solrOpinionRepository.findOne(opinionId);

        List<String> wordExpansions = expansions.getExpansions();
        List<Float> weights = expansions.getWeights();

        if (expansions.getType() == ExpansionType.SENTIMENT_WORD) {
            opinion.setSentimentWordExpansions(wordExpansions);
            opinion.setSentimentWordExpansionWeights(weights);
        } else if (expansions.getType() == ExpansionType.TARGET) {
            opinion.setTargetExpansions(wordExpansions);
            opinion.setTargetExpansionWeights(weights);
        }

        return opinion;
    }

    @Override
    public List<Opinion> updateExpansions(ExpansionsSet expansionsSet) {

        List<Opinion> updatedOpinions = new ArrayList<Opinion>();

        for (Expansions expansions : expansionsSet) {
            Opinion opinion = updateExpansions(expansions);
            updatedOpinions.add(opinion);
        }

        solrOpinionRepository.save(updatedOpinions);
        //opinionDao.save(updatedOpinions);

        return updatedOpinions;
    }

    @Override
    public Opinion addExpansions(Expansions expansions) {
        String opinionId = String.valueOf(expansions.getOpinionId());
        Opinion opinion = solrOpinionRepository.findOne(opinionId);

        List<String> wordExpansions = expansions.getExpansions();
        List<Float> weights = expansions.getWeights();

        List<String> currentExpansions;
        List<Float> currentWeights;

        if (expansions.getType() == ExpansionType.SENTIMENT_WORD) {

            currentExpansions = opinion.getSentimentWordExpansions();
            currentWeights = opinion.getSentimentWordExpansionWeights();

            wordExpansions = ListUtils.union(currentExpansions, wordExpansions);
            weights = ListUtils.union(currentWeights,weights);

            opinion.setSentimentWordExpansions(wordExpansions);
            opinion.setSentimentWordExpansionWeights(weights);

        } else if (expansions.getType() == ExpansionType.TARGET) {

            currentExpansions = opinion.getTargetExpansions();
            currentWeights = opinion.getTargetExpansionWeights();

            wordExpansions = ListUtils.union(currentExpansions, wordExpansions);
            weights = ListUtils.union(currentWeights,weights);

            opinion.setTargetExpansions(wordExpansions);
            opinion.setTargetExpansionWeights(weights);
        }

        return opinion;
    }

    @Override
    public List<Opinion> addExpansionsFor(ExpansionsSet expansionsSet) {
        List<Opinion> updatedOpinions = new ArrayList<Opinion>();

        for (Expansions expansions : expansionsSet) {
            Opinion opinion = addExpansions(expansions);
            updatedOpinions.add(opinion);
        }

        solrOpinionRepository.save(updatedOpinions);
        //opinionDao.save(updatedOpinions);

        return updatedOpinions;
    }
}
