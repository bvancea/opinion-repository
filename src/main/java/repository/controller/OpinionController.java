package repository.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import repository.controller.template.TemplateController;
import repository.model.Opinion;
import repository.service.OpinionService;

import javax.transaction.NotSupportedException;
import javax.validation.Valid;
import java.util.List;
import repository.controller.dto.OpinionQueryExDTO;
import repository.controller.dto.OpinionsDTO;

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
        System.out.println("HAIIIIIIII THER!!!");
        return opinionService.findAllOpinions();
    }
    
    @Override
    public Opinion add(@Valid @RequestBody Opinion record) throws NotSupportedException {
        return opinionService.addOpinion(record);
    }
    
    @RequestMapping(value = "/addAll", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Opinion> addAll(@Valid @RequestBody OpinionsDTO opinions) throws NotSupportedException {
        return opinionService.addOpinions(opinions);
    }
    
    @RequestMapping(value = "/addExpansions", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Opinion> addExpansions(@Valid @RequestBody OpinionQueryExDTO opinionsExpansion) throws NotSupportedException {
        return opinionService.addExpandedOpinions(opinionsExpansion);
    }

    @RequestMapping(value = "/holder/{holderName}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Opinion> findAllByHolder(@PathVariable("holderName") String holderName) throws NotSupportedException {
        return opinionService.findAllOpinionsByHolderName(holderName);
    }
    
    @RequestMapping(value = "/holder/{holderName}/target/{targetEntity}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Opinion> findAllByHolderAndtarget(@PathVariable("holderName") String holderName, 
        @PathVariable("targetEntity") String targetEntity) throws NotSupportedException {
        return opinionService.findAllOpinionsByHolderAndTarget(holderName, targetEntity);
    }
    
    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Opinion findById(@PathVariable("id") String id) throws NotSupportedException {
        return opinionService.findById(id);
    }
    
    @RequestMapping(value = "/unexpanded", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Opinion> findAllUnexpanded() throws NotSupportedException {
        return opinionService.findAllUnexpanded();
    }



}
