package repository.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import repository.controller.template.TemplateController;
import repository.model.Opinion;
import repository.service.OpinionService;

import javax.transaction.NotSupportedException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 3/16/13
 * Time: 8:39 PM
 *
 *
 * Handle all requests concerning opinions.
 */
@Controller
@RequestMapping("/opinion")
public class OpinionController extends TemplateController<Opinion> {

    @Autowired
    OpinionService opinionService;

    @RequestMapping(value = "/findall", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Opinion> getAll() throws NotSupportedException {
        return opinionService.findAllOpinions();
    }

}
