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
import api.dao.HolderDao;
import api.model.Holder;
import api.model.HolderRelationship;
import api.service.HolderService;

/**
 *
 * @author Alex Marchis
 */
@Service
@Transactional
public class HolderServiceImpl implements HolderService{

    @Autowired
    private HolderDao holderDao;

    @Override
    public List<Holder> findAll() throws NotSupportedException {
        return holderDao.findAll();
    }

    @Override
    public Holder add(Holder holder) throws NotSupportedException {
        return holderDao.save(holder);
    }

    @Override
    public HolderRelationship addRelationship(HolderRelationship hrel) throws NotSupportedException {
        return holderDao.saveHolderRelationship(hrel);
    }

    @Override
    public Holder findByName(String name) throws NotSupportedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

   
}
