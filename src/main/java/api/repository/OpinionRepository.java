package api.repository;

import api.model.Opinion;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.solr.repository.support.SimpleSolrRepository;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 4/2/13
 * Time: 1:42 AM
 * To change this template use File | Settings | File Templates.
 */
public interface OpinionRepository extends CrudRepository<Opinion, String> {

    public List<Opinion> findByHolderLike(String holder);
    public List<Opinion> findByEntityLike(String entity);
    public List<Opinion> findByAttributeLike(String attribute);
    public List<Opinion> findBySentimentWordLike(String sentimentWord);
    public List<Opinion> findBySentimentOrientation(Float senimentOrientation);

}
