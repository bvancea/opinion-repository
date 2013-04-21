
package repository.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.NotSupportedException;
import javax.validation.Valid;
import java.util.List;
import repository.controller.template.TemplateController;
import repository.model.Document;
import repository.service.DocumentService;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 4/10/13
 * Time: 1:07 PM
 *
 * Handle document REST requests.
 */

@Controller
@RequestMapping("/document")
public class DocumentController extends TemplateController<Document>{

    @Autowired
    private DocumentService documentService;

    @Override
    @RequestMapping(value = "/findall", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<Document> getAll() throws NotSupportedException {
        return documentService.findAll();
    }

    @Override
    public Document get(@PathVariable("id") String id) throws EmptyResultDataAccessException, NotSupportedException{
        return documentService.findById(id);
    }

    @Override
    public Document add(@Valid @RequestBody Document record) throws NotSupportedException{
        return documentService.add(record);
    }

    public void delete(@PathVariable("id") String id) throws EmptyResultDataAccessException, NotSupportedException{
        documentService.deleteById(id);
    }

}