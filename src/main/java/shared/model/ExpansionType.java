package shared.model;

import org.codehaus.jackson.annotate.JsonValue;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 5/4/13
 * Time: 1:03 PM
 */
public enum ExpansionType {

    TARGET(1),SENTIMENT_WORD(2);

    private long value;

    ExpansionType(long value) {
        this.value = value;
    }

    /*
     * Annotate for JSON Serialization/Deserialization
     */
    @JsonValue
    public String value() {
        return String.valueOf(value);
    }

}
