package api.service;

import api.model.CommunityContradiction;
import api.model.Contradiction;
import api.model.Opinion;

import javax.transaction.NotSupportedException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 3/25/13
 * Time: 11:05 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ContradictionService {

    public List<Contradiction> findAllContradictions() throws NotSupportedException;
    public Contradiction addContradiction(Contradiction contradiction) throws NotSupportedException;

    public List<CommunityContradiction> findAllCommunityContradictions() throws NotSupportedException;
    public CommunityContradiction addCommunityContradiction(CommunityContradiction contradiction) throws NotSupportedException;
}
