/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package api.service.impl;

import api.dao.CommunityDao;
import api.model.Community;
import api.service.CommunityService;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.NotSupportedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Alex Marchis
 */
@Service
public class CommunityServiceImpl implements CommunityService {
    
    @Autowired 
    CommunityDao communityDao;

    @Override
    public List<Community> findAllCommunities() throws NotSupportedException {
        return communityDao.findAll();
    }

    @Override
    public Community addCommunity(Community community) throws NotSupportedException {
        return communityDao.save(community);
    }

    @Override
    public List<Community> addCommunities(List<Community> communities) throws NotSupportedException {
        List<Community> cms = new ArrayList<Community>();
        for(Community community: communities){
            cms.add(communityDao.save(community));
   
        }
        
        return cms;
    }

    @Override
    public List<Community> findAllCommunitiesByHolderName(String holderName) throws NotSupportedException {
        return communityDao.findByHolderName(holderName);
    }

    @Override
    public Community findById(String id) throws NotSupportedException {
        return communityDao.find(id);
    }
    
}
