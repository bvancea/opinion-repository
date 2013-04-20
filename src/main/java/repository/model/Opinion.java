package repository.model;

import java.util.Date;
import java.util.List;

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

    private String document;       //ToDo add JCR support/alternatives

    private Date timestamp;

    private int positionSW;
    private int positionT;
    private int expanded;
    private int communitized;

    public String getId() {
        return id;
    }

    public String getHolder() {
        return holder;
    }

    public String getEntity() {
        return entity;
    }

    public String getAttribute() {
        return attribute;
    }

    public Double getSentimentOrientation() {
        return sentimentOrientation;
    }

    public String getSentimentWord() {
        return sentimentWord;
    }

    public String getDocument() {
        return document;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public int getPositionSW() {
        return positionSW;
    }

    public int getPositionT() {
        return positionT;
    }

    public int getExpanded() {
        return expanded;
    }

    public int getCommunitized() {
        return communitized;
    }
  
    public void setId(String id) {
        this.id = id;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public void setSentimentOrientation(Double sentimentOrientation) {
        this.sentimentOrientation = sentimentOrientation;
    }

    public void setSentimentWord(String sentimentWord) {
        this.sentimentWord = sentimentWord;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setPositionSW(int positionSW) {
        this.positionSW = positionSW;
    }

    public void setPositionT(int positionT) {
        this.positionT = positionT;
    }

    public void setExpanded(int expanded) {
        this.expanded = expanded;
    }

    public void setCommunitized(int communitized) {
        this.communitized = communitized;
    }
  
}
