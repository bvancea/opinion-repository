package api.model;

import api.model.solr.SearcheableOpinion;
import org.apache.solr.client.solrj.beans.Field;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 3/16/13
 * Time: 8:54 PM
 */
public class Opinion implements SearcheableOpinion {

    @Field(ID_FIELD)
    private String id;

    @Field(HOLDER_FIELD)
    private String holder;

    @Field(ENTITY_FIELD)
    private String entity;

    @Field(ATTRIBUTE_FIELD)
    private String attribute;

    @Field(ORIENTATION_FIELD)
    private Double sentimentOrientation;

    @Field(SENTIMENT_WORD_FIELD)
    private String sentimentWord;

    @Field(DOCUMENT_FIELD)
    private String document;       //ToDo add JCR support/alternatives

    @Field(DATE_FIELD)
    private Date timestamp;

    private int position;


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

    public Double getSentimentOrientation() {
        return sentimentOrientation;
    }

    public void setSentimentOrientation(Double sentimentOrientation) {
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
