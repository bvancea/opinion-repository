package repository.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import repository.controller.template.TemplateController;
import repository.model.AOpinion;
import repository.service.AOpinionService;

import javax.transaction.NotSupportedException;
import javax.validation.Valid;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 *
 *
 * Handle all requests concerning opinions.
 */
@Controller
@RequestMapping("/Aopinion")
public class AOpinionController extends TemplateController<AOpinion> {

    @Autowired
    AOpinionService opinionService;

    @RequestMapping(value = "/findall", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<AOpinion> getAll() throws NotSupportedException {
        return opinionService.findAllOpinions();
    }
    
    @Override
    public AOpinion add(@Valid @RequestBody AOpinion record) throws NotSupportedException {
        return opinionService.addOpinion(record);
    }

}
