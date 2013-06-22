package api.model;

import api.model.solr.SearcheableOpinion;
import java.util.List;
import org.apache.solr.client.solrj.beans.Field;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 3/16/13
 * Time: 8:54 PM
 * To change this template use File | Settings | File Templates.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Opinion implements SearcheableOpinion {

    @Field(ID_FIELD)
    private String id;

    @Field(HOLDER_FIELD)
    private String holder;

   /* @Field(ENTITY_FIELD)*/
    @Field(TARGET_FIELD)
    private String entity;

    /*@Field(ATTRIBUTE_FIELD)*/
    private String attribute;

    @Field(ORIENTATION_FIELD)
    private Float sentimentOrientation;

    @Field(SENTIMENT_WORD_FIELD)
    private String sentimentWord;

    @Field(DOCUMENT_FIELD)
    private String document;       //ToDo add JCR support/alternatives

    @Field(TARGET_EXPANSIONS_FIELD)
    private List<String> targetExpansions;

    @Field(TARGET_EXPANSIONS_WEIGHTS_FIELD)
    private List<Float> targetExpansionWeights;

    @Field(SENTIMENT_WORD_EXPANSIONS_FIELD)
    private List<String> sentimentWordExpansions;

    @Field(SENTIMENT_WORD_EXPANSIONS_WEIGHTS_FIELD)
    private List<Float> sentimentWordExpansionWeights;

    @Field(DATE_FIELD)
    private Date timestamp;

    private int positionSW;
    private int positionT;
    private int expanded;
    private int communitized;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public Float getSentimentOrientation() {
        return sentimentOrientation;
    }

    public void setSentimentOrientation(Float sentimentOrientation) {
        this.sentimentOrientation = sentimentOrientation;
    }

    public String getSentimentWord() {
        return sentimentWord;
    }

    public void setSentimentWord(String sentimentWord) {
        this.sentimentWord = sentimentWord;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public int getPositionSW() {
        return positionSW;
    }

    public void setPositionSW(int positionSW) {
        this.positionSW = positionSW;
    }

    public int getPositionT() {
        return positionT;
    }

    public void setPositionT(int positionT) {
        this.positionT = positionT;
    }

    public int getExpanded() {
        return expanded;
    }

    public void setExpanded(int expanded) {
        this.expanded = expanded;
    }

    public int getCommunitized() {
        return communitized;
    }

    public void setCommunitized(int communitized) {
        this.communitized = communitized;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public List<String> getTargetExpansions() {
        return targetExpansions;
    }

    public void setTargetExpansions(List<String> targetExpansions) {
        this.targetExpansions = targetExpansions;
    }

    public List<Float> getTargetExpansionWeights() {
        return targetExpansionWeights;
    }

    public void setTargetExpansionWeights(List<Float> targetExpansionWeights) {
        this.targetExpansionWeights = targetExpansionWeights;
    }

    public List<String> getSentimentWordExpansions() {
        return sentimentWordExpansions;
    }

    public void setSentimentWordExpansions(List<String> sentimentWordExpansions) {
        this.sentimentWordExpansions = sentimentWordExpansions;
    }

    public List<Float> getSentimentWordExpansionWeights() {
        return sentimentWordExpansionWeights;
    }

    public void setSentimentWordExpansionWeights(List<Float> sentimentWordExpansionWeights) {
        this.sentimentWordExpansionWeights = sentimentWordExpansionWeights;
    }

    @Override
    public String toString() {
        return "Opinion{" +
                "holder='" + holder + '\'' +
                ", entity='" + entity + '\'' +
                ", attribute='" + attribute + '\'' +
                '}';
    }
  
}
