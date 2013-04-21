/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package repository.service;

import java.util.List;
import javax.transaction.NotSupportedException;
import repository.model.Document;

/**
 *
 * @author Alex Marchis
 */
public interface DocumentService {

    public List<Document> findAll() throws NotSupportedException;

    public Document add(Document document) throws NotSupportedException;

    public Document findById(String id) throws NotSupportedException;
    public Document findByTitle(String title);
    public void deleteById(String id) throws NotSupportedException;

}
