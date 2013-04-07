package repository.model;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 04.04.2013
 * Time: 22:10
 * To change this template use File | Settings | File Templates.
 */
public class AOpinion {

    private String id;
    private String holder;
    private ATarget target;
    private Double sentimentOrientation;
    private String sentimentWord;

    private String documentID;
    private int positionSW;
    private int positionT;
    private int expandedFlag;
    private int communitizedFlag;
    private Date timestamp;
    
    private List<AOpinion> opinionExpansions;

    public AOpinion(String id, String holder, ATarget target, Double sentimentOrientation, String sentimentWord, String documentID, int positionSW, int positionT, int expandedFlag, int communitizedFlag, Date timestamp) {
        this.id = id;
        this.holder = holder;
        this.sentimentOrientation = sentimentOrientation;
        this.sentimentWord = sentimentWord;
        this.documentID = documentID;
        this.positionSW = positionSW;
        this.positionT = positionT;
        this.expandedFlag = expandedFlag;
        this.communitizedFlag = communitizedFlag;
        this.timestamp = timestamp;
    }

    public AOpinion() {
    }

    public String getId() {
        return id;
    }

    public String getHolder() {
        return holder;
    }

    public ATarget getTarget() {
        return target;
    }

    public Double getSentimentOrientation() {
        return sentimentOrientation;
    }

    public String getSentimentWord() {
        return sentimentWord;
    }

    public String getDocumentID() {
        return documentID;
    }

    public int getPositionSW() {
        return positionSW;
    }

    public int getPositionT() {
        return positionT;
    }

    public int getExpandedFlag() {
        return expandedFlag;
    }

    public int getCommunitizedFlag() {
        return communitizedFlag;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public void setTarget(ATarget target) {
        this.target = target;
    }

    public void setSentimentOrientation(Double sentimentOrientation) {
        this.sentimentOrientation = sentimentOrientation;
    }

    public void setSentimentWord(String sentimentWord) {
        this.sentimentWord = sentimentWord;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
    }

    public void setPositionSW(int positionSW) {
        this.positionSW = positionSW;
    }

    public void setPositionT(int positionT) {
        this.positionT = positionT;
    }

    public void setExpandedFlag(int expandedFlag) {
        this.expandedFlag = expandedFlag;
    }

    public void setCommunitizedFlag(int communitizedFlag) {
        this.communitizedFlag = communitizedFlag;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setOpinionExpansions(List<AOpinion> opinionExpansions) {
        this.opinionExpansions = opinionExpansions;
    }

    public List<AOpinion> getOpinionExpansions() {
        return opinionExpansions;
    }

    
    
}
