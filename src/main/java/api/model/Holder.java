package api.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 *
 * @author Alex Marchis
 */
public class Holder {

    private String name;
    private Date birthDate;
    private int gender;
    private String religion;
    private String location;
    private String communityID;
    
    private List<HolderRelationship> relationships;

    public Holder() {
        this.relationships = new ArrayList<HolderRelationship>();
    }

    public String getName() {
        return name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public int getGender() {
        return gender;
    }

    public String getReligion() {
        return religion;
    }

    public String getLocation() {
        return location;
    }

    public String getCommunityID() {
        return communityID;
    }

    public List<HolderRelationship> getRelationships() {
        return relationships;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setCommunityID(String communityID) {
        this.communityID = communityID;
    }

    public void setRelationships(List<HolderRelationship> relationships) {
        this.relationships = relationships;
    }
    
}


