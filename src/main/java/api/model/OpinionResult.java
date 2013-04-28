package api.model;

import java.util.List;

public class OpinionResult {

    private String holder;

    private String entity;

    private List<Opinion> opinions;

    public String getHolder() {
        return holder;
    }

    public String getEntity() {
        return entity;
    }

    public List<Opinion> getOpinions() {
        return opinions;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public void setOpinions(List<Opinion> Opinions) {
        this.opinions = Opinions;
    }
    
    public Opinion getOriginalOpinion(){
        for(Opinion o : opinions){
            if(o.getEntity().equals(entity)){
                return o;
            }
        }
        
        return null;
    }

    @Override
    public String toString() {
        String print;
        print = "OpinionResult{" + "holder=" + holder + ", entity=" + entity + ", Opinions:";
        print += printOpinions();
        return print;
    }
    
    private String printOpinions(){
        String print = "";
        for(Opinion o : opinions){
            print += "\n\t" + o;
        }
        
        return print;
    }
        
}
