package api.service;

import api.model.Opinion;
import shared.model.ExpansionType;
import shared.model.Expansions;
import shared.model.ExpansionsSet;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 5/5/13
 * Time: 3:55 PM
 */
public interface ExpansionsService {

    public Opinion updateExpansions(Expansions expansions);

    public List<Opinion> updateExpansions(ExpansionsSet expansionsSet);

    public Opinion addExpansions(Expansions expansions);

    public List<Opinion> addExpansionsFor(ExpansionsSet expansionsSet);

}
