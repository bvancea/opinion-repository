package api.controller;

import api.controller.dto.OpinionsDTO;
import api.controller.template.TemplateController;
import api.model.Opinion;
import api.service.OpinionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.NotSupportedException;
import javax.validation.Valid;
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

    @Override
    public Opinion add(@Valid @RequestBody Opinion record) throws NotSupportedException {
        return opinionService.addOrUpdateOpinion(record);
    }

    @RequestMapping(value = "/addAll", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Opinion> addAll(@Valid @RequestBody OpinionsDTO opinions) throws NotSupportedException {
        return opinionService.addOrUpdateOpinions(opinions);
    }


    @RequestMapping(value = "/holder/{holderName}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Opinion> findAllByHolder(@PathVariable("holderName") String holderName) throws NotSupportedException {
        return opinionService.findAllOpinionsByHolderName(holderName);
    }

    @RequestMapping(value = "/entity/{entityName}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Opinion> findAllByEntity(@PathVariable("entityName") String entityName) throws NotSupportedException {
        return opinionService.findAllOpinionsByEntityName(entityName);
    }

}
