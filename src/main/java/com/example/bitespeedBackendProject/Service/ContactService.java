package com.example.bitespeedBackendProject.Service;



import com.example.bitespeedBackendProject.Repository.ContactRepository;
import com.example.bitespeedBackendProject.dto.IdentifyRequest;
import com.example.bitespeedBackendProject.dto.IdentifyResponse;
import com.example.bitespeedBackendProject.entity.Contact;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service   //It is an business logic for service layer components
public class ContactService {
    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public IdentifyResponse identify(IdentifyRequest request) {
        String email = request.getEmail();
        String phone = request.getPhoneNumber();
        if (email == null && phone == null) throw new IllegalArgumentException("At least one of email or phoneNumber must be provided");

        List<Contact> matched = contactRepository.findByEmailOrPhoneNumber(email, phone);
        Contact primary;
        if (matched.isEmpty()) {
            Contact newContact = new Contact(phone, email, null, "primary", LocalDateTime.now(), LocalDateTime.now(), null);
            contactRepository.save(newContact);
            return new IdentifyResponse(new IdentifyResponse.ContactDTO(newContact.getId(), List.of(email), List.of(phone), List.of()));
        } else {
            primary = matched.stream()
                    .filter(c -> "primary".equals(c.getLinkPrecedence()))
                    .min(Comparator.comparing(Contact::getCreatedAt))
                    .orElse(matched.get(0));

            Long primaryId = primary.getId();
            for (Contact c : matched) {
                if (!c.getId().equals(primaryId) && "primary".equals(c.getLinkPrecedence())) {
                    c.setLinkPrecedence("secondary");
                    c.setLinkedId(primaryId);
                    c.setUpdatedAt(LocalDateTime.now());
                    contactRepository.save(c);
                }
            }

            boolean alreadyExists = matched.stream().anyMatch(c -> Objects.equals(c.getEmail(), email) && Objects.equals(c.getPhoneNumber(), phone));
            if (!alreadyExists) {
                Contact secondary = new Contact(phone, email, primaryId, "secondary", LocalDateTime.now(), LocalDateTime.now(), null);
                contactRepository.save(secondary);
            }

            List<Contact> allRelated = contactRepository.findAll().stream()
                    .filter(c -> Objects.equals(c.getId(), primaryId) || Objects.equals(c.getLinkedId(), primaryId))
                    .toList();

            Set<String> emails = new HashSet<>();
            Set<String> phones = new HashSet<>();
            List<Long> secondaryIds = new ArrayList<>();
            for (Contact c : allRelated) {
                if (c.getEmail() != null) emails.add(c.getEmail());
                if (c.getPhoneNumber() != null) phones.add(c.getPhoneNumber());
                if (!Objects.equals(c.getId(), primaryId)) secondaryIds.add(c.getId());
            }

            return new IdentifyResponse(new IdentifyResponse.ContactDTO(primaryId, new ArrayList<>(emails), new ArrayList<>(phones), secondaryIds));
        }
    }

    public List<Contact> getAll() {
        return contactRepository.findAll();
    }

    public void deleteById(Long id) {
        contactRepository.deleteById(id);
    }

    public Contact update(Long id, Contact updated) {
        Contact existing = contactRepository.findById(id).orElseThrow();
        existing.setEmail(updated.getEmail());
        existing.setPhoneNumber(updated.getPhoneNumber());
        existing.setUpdatedAt(LocalDateTime.now());
        return contactRepository.save(existing);
    }
}