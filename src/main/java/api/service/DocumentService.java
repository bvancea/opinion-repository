package api.service;

import api.model.Document;
import java.util.List;

import javax.transaction.NotSupportedException;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 4/10/13
 * Time: 12:50 PM
 * To change this template use File | Settings | File Templates.
 */

public interface DocumentService {

    public List<Document> findAll() throws NotSupportedException;

    public Document add(Document document) throws NotSupportedException;

    public Document findById(String id) throws NotSupportedException;
    public Document findByTitle(String title);
    public void deleteById(String id) throws NotSupportedException;

}
