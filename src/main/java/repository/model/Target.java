package repository.model;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 3/25/13
 * Time: 10:59 PM
 * To change this template use File | Settings | File Templates.
 */
public class Target {

    private String target;
    private List<String> targetVersions;

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public List<String> getTargetVersions() {
        return targetVersions;
    }

    public void setTargetVersions(List<String> targetVersions) {
        this.targetVersions = targetVersions;
    }

    public boolean addTargetVersion(String targetVersion) {
        return targetVersions.add(targetVersion);
    }
}
