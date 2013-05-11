
package api.controller;

import api.controller.template.TemplateController;
import api.model.Document;
import api.model.Holder;
import api.service.HolderService;
import java.util.List;
import javax.transaction.NotSupportedException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 4/10/13
 * Time: 1:07 PM
 *
 * Handle document REST requests.
 */

@Controller
@RequestMapping("/holder")
public class HolderController extends TemplateController<Holder>{

    @Autowired
    private HolderService holderService;

    @Override
    @RequestMapping(value = "/findall", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Holder> getAll() throws NotSupportedException {
        return holderService.findAll();
    }

    @Override
    public Holder get(@PathVariable("name") String name) throws EmptyResultDataAccessException, NotSupportedException{
        return holderService.findByName(name);
    }

    @Override
    public Holder add(@Valid @RequestBody Holder record) throws NotSupportedException{
        return holderService.add(record);
    }

    
}