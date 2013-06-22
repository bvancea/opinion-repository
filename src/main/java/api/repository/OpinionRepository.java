package api.repository;

import api.model.Opinion;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.core.NamedQueries;
import org.springframework.data.solr.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 4/2/13
 * Time: 1:42 AM
 */
public interface OpinionRepository extends PagingAndSortingRepository<Opinion, String> {

    public List<Opinion> findByHolderLike(String holder);

    public List<Opinion> findByEntityLike(String entity);

    public List<Opinion> findBySentimentWordLike(String sentimentWord);

    public List<Opinion> findBySentimentOrientation(Float senimentOrientation);

    public List<Opinion> findByHolderAndEntityLike(String holder, String target);

    @Query("holder:?0 AND (target:?1 OR targetExpansions:?1)")
    public List<Opinion> findByHolderAndTarget(String holder, String target);

    @Query("(target:?0 OR targetExpansions:?0) AND sentimentOrientation:?1")
    public List<Opinion> findBySimilarTargetAndSentimentOrientation(String target, Double sentimentOrientation);

    public List<Opinion> findByEntityAndSentimentOrientation(String target, Double sentimentOrientation);

    @Query("(target:?0 OR targetExpansions:?0) AND sentimentWord:?1")
    public List<Opinion> findBySimilarTargetAndSentimentWord(String target, String sentimentWord);


}
