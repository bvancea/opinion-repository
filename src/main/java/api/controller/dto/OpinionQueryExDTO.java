/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package api.controller.dto;

import java.util.ArrayList;
import api.model.Opinion;

/**
 *
 * @author Alex Marchis
 */
public class OpinionQueryExDTO{
    
    private Opinion originalOpinion;
    
    private ArrayList<Opinion> expandedOpinions;

    public Opinion getOriginalOpinion() {
        return originalOpinion;
    }

    public void setOriginalOpinion(Opinion originalOpinion) {
        this.originalOpinion = originalOpinion;
    }

    public ArrayList<Opinion> getExpandedOpinions() {
        return expandedOpinions;
    }

    public void setExpandedOpinions(ArrayList<Opinion> expandedOpinions) {
        this.expandedOpinions = expandedOpinions;
    }
    
    
    
    
}
