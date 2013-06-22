/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package api.model;

import java.util.List;

/**
 *
 * @author Alex Marchis
 */
public class Community {
    private String communityId;
    private List<CommunityFeature> features;
    private List<CommunityHolder> holders;

    public String getCommunityId() {
        return communityId;
    }

    public List<CommunityFeature> getFeatures() {
        return features;
    }

    public List<CommunityHolder> getHolders() {
        return holders;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public void setFeatures(List<CommunityFeature> features) {
        this.features = features;
    }

    public void setHolders(List<CommunityHolder> holders) {
        this.holders = holders;
    }
    
    
}
