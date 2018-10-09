package com.services.direct.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.services.direct.bean.Contact;
import com.services.direct.data.ContactInputDto;
import com.services.direct.exception.BusinessException;
import com.services.direct.service.ContactService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/contacts")
@Api(value="onlinestore", description="API REST contact")
public class ContactController {
	
	private ContactService contactService;
	
	 
	@Autowired
	public ContactController(final ContactService contactService){
	  this.contactService = contactService;
	}
	
    @ApiOperation(value = "View a list of available contact",response = Iterable.class)
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Successfully retrieved list"),
//            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
//            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
//            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
//    })
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public List<Contact> getAllContacts() throws BusinessException {
		return this.contactService.getAllContacts();
	}

	@RequestMapping(value = "/{contactId}", method = RequestMethod.GET)
    @ResponseBody
    public Contact getContactById(@PathVariable final Integer contactId)  throws BusinessException {
        return this.contactService.getContactById(contactId);
    }
	
	 
	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Contact> addContact(@RequestBody ContactInputDto contactDto) throws BusinessException{
		Contact contact = this.contactService.addContact(contactDto);
		if (contact != null) {
			return new ResponseEntity<>(contact, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}	
	}
	
	@RequestMapping(value="/contacts/{id}", method=RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
    public void deleteContact(@PathVariable Integer id) {
        contactService.deleteContact(id);
    }
}
