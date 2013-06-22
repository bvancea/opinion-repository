package api.controller;

import java.util.List;
import javax.transaction.NotSupportedException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import api.controller.template.TemplateController;
import api.model.CommunityContradiction;
import api.model.Contradiction;
import api.service.ContradictionService;

@Controller
@RequestMapping("/contradiction")
public class ContradictionController extends TemplateController<Contradiction> {

    @Autowired
    ContradictionService contradictionService;

    @RequestMapping(value = "/findall", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Contradiction> getAll() throws NotSupportedException {
        return contradictionService.findAllContradictions();
    }
    
    @RequestMapping(value = "/community/findall", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<CommunityContradiction> getAllCommunity() throws NotSupportedException {
        return contradictionService.findAllCommunityContradictions();
    }
    
    @Override
    public Contradiction add(@Valid @RequestBody Contradiction record) throws NotSupportedException {
        return contradictionService.addContradiction(record);
    }
    
    @RequestMapping(value = "/community/", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public CommunityContradiction addCommunityContradiction(@Valid @RequestBody CommunityContradiction record) throws NotSupportedException {
        return contradictionService.addCommunityContradiction(record);
    }
       
}
