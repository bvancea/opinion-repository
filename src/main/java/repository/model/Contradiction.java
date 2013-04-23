package repository.model;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 3/25/13
 * Time: 10:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class Contradiction {

    private String firstOpinionId;
    private String secondOpinionId;
    private int contradictionType;


    public String getFirstOpinionId() {
        return firstOpinionId;
    }

    public void setFirstOpinionId(String firstOpinionId) {
        this.firstOpinionId = firstOpinionId;
    }

    public String getSecondOpinionId() {
        return secondOpinionId;
    }

    public void setSecondOpinionId(String secondOpinionId) {
        this.secondOpinionId = secondOpinionId;
    }

    public int getContradictionType() {
        return contradictionType;
    }

    public void setContradictionType(int contradictionType) {
        this.contradictionType = contradictionType;
    }
    
    public void invertOpinionIDs(){
        String auxid = firstOpinionId;
        firstOpinionId = secondOpinionId;
        secondOpinionId = auxid;
    }
}
