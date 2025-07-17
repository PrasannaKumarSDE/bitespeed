package com.example.bitespeedBackendProject.Controller;


import com.example.bitespeedBackendProject.Service.ContactService;
import com.example.bitespeedBackendProject.dto.IdentifyRequest;
import com.example.bitespeedBackendProject.dto.IdentifyResponse;
import com.example.bitespeedBackendProject.entity.Contact;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

//Controller -> Controller is used to SpringMvc to Define the WebBrowser
//RestController -> Combines the Controller and ResponseBody To Simplify RestFull API Development

@RestController
@RequestMapping("/api")   //RequestMapping To Handle the HTTP requests Like (Get,Put,Post,Delete)
public class ContactController {

	//inject auto
    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }
    // Create new task or create the data from handle
    @PostMapping("/identify")
    public ResponseEntity<IdentifyResponse> identify(@RequestBody IdentifyRequest request) {
        return ResponseEntity.ok(contactService.identify(request));
    }
      
    // To Retrieve the Data from the Handle
    @GetMapping("/identify")
    public ResponseEntity<List<Contact>> getAll() {
        return ResponseEntity.ok(contactService.getAll());
    }

    
    // Delete the data from task
    @DeleteMapping("/contacts/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        contactService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    
 // Update Data from existing task
    @PutMapping("/contacts/{id}")
    public ResponseEntity<Contact> update(@PathVariable Long id, @RequestBody Contact contact) {
        return ResponseEntity.ok(contactService.update(id, contact));
    }
}
