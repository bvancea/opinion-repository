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
    String TARGET_FIELD = "target";
    String TARGET_EXPANSIONS_FIELD = "targetExpansions";
    String TARGET_EXPANSIONS_WEIGHTS_FIELD = "teWeights";

    String SENTIMENT_WORD_FIELD = "sentimentWord";
    String SENTIMENT_WORD_EXPANSIONS_FIELD = "sentimentWordExpansions";
    String SENTIMENT_WORD_EXPANSIONS_WEIGHTS_FIELD = "swWeights";

    String ORIENTATION_FIELD = "sentimentOrientation";
    String DATE_FIELD = "date";
    String DOCUMENT_FIELD = "docId";

}
