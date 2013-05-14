/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package api.controller;

import api.controller.template.TemplateController;
import api.model.Community;
import api.service.CommunityService;
import java.util.List;
import javax.transaction.NotSupportedException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Alex Marchis
 */
@Controller
@RequestMapping("/community")
public class CommunityController extends TemplateController<Community> {
    
    @Autowired
    CommunityService communityService;
    
    @RequestMapping(value = "/findall", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Community> getAll() throws NotSupportedException {
        return communityService.findAllCommunities();
    }
    
    @Override
    public Community add(@Valid @RequestBody Community record) throws NotSupportedException {
        return communityService.addCommunity(record);
    }
    
    @RequestMapping(value = "/addAll", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Community> addAll(@Valid @RequestBody List<Community> communities) throws NotSupportedException {
        return communityService.addCommunities(communities);
    }
    
    @RequestMapping(value = "/holder/{holderName}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Community> findAllByHolder(@PathVariable("holderName") String holderName) throws NotSupportedException {
        return communityService.findAllCommunitiesByHolderName(holderName);
    }
    
    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Community findById(@PathVariable("id") String id) throws NotSupportedException {
        return communityService.findById(id);
    }
}
