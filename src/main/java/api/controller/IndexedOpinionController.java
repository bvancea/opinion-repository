package api.controller;

import api.controller.dto.OpinionsDTO;
import api.controller.template.TemplateController;
import api.model.Opinion;
import api.repository.OpinionRepository;
import api.service.ExpansionsService;
import api.service.IndexedOpinionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import shared.model.ExpansionsSet;

import javax.transaction.NotSupportedException;
import javax.validation.Valid;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 4/11/13
 * Time: 9:32 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/opinion/indexed")
public class IndexedOpinionController extends TemplateController<Opinion> {

    //ToDo create a service for solr repository access if business logic gets complicated
    @Autowired
    private OpinionRepository solrOpinionRepository;

    @Autowired
    private IndexedOpinionService indexedOpinionService;

    @Autowired
    private ExpansionsService expansionsService;

    @RequestMapping(value = "/findall", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Opinion> getAll() throws NotSupportedException {

        return indexedOpinionService.findAll(0, 10);
        //return solrOpinionRepository.findAll(new PageRequest(0,10)).getContent();
    }

    @RequestMapping(value = "/addAll", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Opinion> addAll(@Valid @RequestBody OpinionsDTO opinions) throws NotSupportedException {
        return indexedOpinionService.addOrUpdateOpinions(opinions);
    }

    @RequestMapping(value = "/holder/{holderName}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Opinion> findByHolder(@PathVariable("holderName") String holderName) throws NotSupportedException {
        return indexedOpinionService.findByHolderLike(holderName);
        //return solrOpinionRepository.findByHolderLike(holderName);
    }

    @RequestMapping(value = "/target/{target}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Opinion> findByTarget(@PathVariable("target") String target  ) throws NotSupportedException {
        //return solrOpinionRepository.findByEntityLike(target);
        return indexedOpinionService.findByEntityLike(target);
    }

    @RequestMapping(value = "/holder/{holderName}/target/{target}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Opinion> findByHolderAndTarget(@PathVariable("holderName") String holderName,
                                                  @PathVariable("target") String target  ) throws NotSupportedException {
        //return solrOpinionRepository.findByHolderAndEntityLike(holderName, target);
        return indexedOpinionService.findByHolderAndEntityLike(holderName, target);
    }

    @RequestMapping(value = "/expansion/", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Opinion> updateTargetExpansions(@RequestBody ExpansionsSet expansionsSet) {

        return expansionsService.updateExpansions(expansionsSet);
    }



}
