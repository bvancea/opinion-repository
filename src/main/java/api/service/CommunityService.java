/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package api.service;

import api.model.Community;
import java.util.List;
import javax.transaction.NotSupportedException;

/**
 *
 * @author Alex Marchis
 */
public interface CommunityService {
    public List<Community> findAllCommunities() throws NotSupportedException;
    public Community addCommunity(Community community) throws NotSupportedException;
    public List<Community> addCommunities(List<Community> communities) throws NotSupportedException;
    public List<Community> findAllCommunitiesByHolderName(String holderName) throws NotSupportedException;
    public Community findById(String id) throws NotSupportedException;

}
