package repository.model;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 3/16/13
 * Time: 8:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class Opinion {

    private String id;
    private String holder;
    private String entity;
    private String attribute;
    private Double sentimentOrientation;
    private String sentimentWord;

    //ToDo add JCR support/alternatives
    private String document;
    private int position;
    private Date timestamp;

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
