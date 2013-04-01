package api.model.solr;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 4/2/13
 * Time: 12:23 AM
 *
 * Mark fields indexed by Solr.
 */
public interface SearcheableOpinion {

    String ID_FIELD = "id";
    String HOLDER_FIELD = "holder";
    String ENTITY_FIELD = "entity";
    String ATTRIBUTE_FIELD = "attribute";
    String SENTIMENT_WORD_FIELD = "sentimentWord";
    String ORIENTATION_FIELD = "sentimentOrientation";
    String DATE_FIELD = "date";
    String DOCUMENT_FIELD = "docId";

}
