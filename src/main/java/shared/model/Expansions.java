package shared.model;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 5/4/13
 * Time: 1:02 PM
 */
public class Expansions {

    private long opinionId;
    private ExpansionType type;
    private String initialWord;

    private List<String> expansions;
    private List<Float> weights;

    public String getInitialWord() {
        return initialWord;
    }

    public void setInitialWord(String initialWord) {
        this.initialWord = initialWord;
    }

    public long getOpinionId() {
        return opinionId;
    }

    public void setOpinionId(long opinionId) {
        this.opinionId = opinionId;
    }

    public ExpansionType getType() {
        return type;
    }

    public void setType(ExpansionType type) {
        this.type = type;
    }

    public List<String> getExpansions() {
        return expansions;
    }

    public void setExpansions(List<String> expansions) {
        this.expansions = expansions;
    }

    public List<Float> getWeights() {
        return weights;
    }

    public void setWeights(List<Float> weights) {
        this.weights = weights;
    }
}


