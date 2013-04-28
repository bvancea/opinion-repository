/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package api.service.impl;

import java.util.List;
import javax.transaction.NotSupportedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import api.dao.DocumentDao;
import api.model.Document;
import api.service.DocumentService;

/**
 *
 * @author Alex Marchis
 */
@Service
@Transactional
public class DocumentServiceImpl implements DocumentService{

    @Autowired
    private DocumentDao documentDao;

    @Override
    public List<Document> findAll() throws NotSupportedException {
        return documentDao.findAll();
    }

    @Override
    public Document add(Document document) throws NotSupportedException {
        return documentDao.save(document);
    }

    @Override
    public Document findById(String id) throws NotSupportedException {
        return documentDao.find(id);
    }

    @Override
    public Document findByTitle(String title) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deleteById(String id) throws NotSupportedException {
        documentDao.delete(id);
    }
}
