package api.service.impl;

import api.dao.CommunityContradictionDao;
import api.model.Contradiction;
import api.model.Opinion;
import api.service.ContradictionService;

import javax.transaction.NotSupportedException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import api.dao.ContradictionDao;
import api.model.CommunityContradiction;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 3/25/13
 * Time: 11:06 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ContradictionServiceImpl implements ContradictionService {

    @Autowired
    private ContradictionDao contradictionDao;

    @Autowired
    private CommunityContradictionDao communityContradictionDao;

    @Override
    public List<Contradiction> findAllContradictions() throws NotSupportedException {
        return contradictionDao.findAll();
    }

    @Override
    public Contradiction addContradiction(Contradiction contradiction) throws NotSupportedException {
        return contradictionDao.save(contradiction);
    }

    @Override
    public List<CommunityContradiction> findAllCommunityContradictions() throws NotSupportedException {
        return communityContradictionDao.findAll();
    }

    @Override
    public CommunityContradiction addCommunityContradiction(CommunityContradiction contradiction) throws NotSupportedException {
        return communityContradictionDao.save(contradiction);
    }
}