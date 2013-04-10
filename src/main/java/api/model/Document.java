package api.model;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 3/25/13
 * Time: 10:46 PM
 *
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Document {

    private String id;
    private String content;
    private String title;
    private Date addedDate;

    public Date getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
