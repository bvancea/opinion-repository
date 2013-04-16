package api.controller.dto;

import api.model.Opinion;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 4/10/13
 * Time: 3:15 PM
 *
 * This should be returned to the repository clients.
 */
public class OpinionsDTO extends ArrayList<Opinion> {

    public OpinionsDTO() {
        super();
    }
}
