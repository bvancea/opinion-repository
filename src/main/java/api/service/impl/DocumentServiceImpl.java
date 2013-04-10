package api.service.impl;

import api.dao.DocumentDao;
import api.model.Document;
import api.service.DocumentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.transaction.NotSupportedException;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 4/10/13
 * Time: 12:51 PM
 *
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
