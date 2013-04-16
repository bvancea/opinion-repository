package api.controller;

import api.controller.template.TemplateController;
import api.model.Opinion;
import api.repository.OpinionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.NotSupportedException;
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

    @RequestMapping(value = "/findall", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Opinion> getAll() throws NotSupportedException {
        return solrOpinionRepository.findAll(new PageRequest(0,10)).getContent();
    }

    @RequestMapping(value = "/holder/{holderName}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Opinion> findByHolder(@PathVariable("holderName") String holderName) throws NotSupportedException {
        return solrOpinionRepository.findByHolderLike(holderName);
    }

    @RequestMapping(value = "/target/{target}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Opinion> findByTarget(@PathVariable("target") String target  ) throws NotSupportedException {
        return solrOpinionRepository.findByEntityLike(target);
    }

    @RequestMapping(value = "/holder/{holderName}/target/{target}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Opinion> findByHolderAndTarget(@PathVariable("holderName") String holderName,
                                                  @PathVariable("target") String target  ) throws NotSupportedException {
        return solrOpinionRepository.findByHolderAndEntityLike(holderName, target);
    }

}
