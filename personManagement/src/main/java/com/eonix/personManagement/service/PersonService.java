package com.eonix.personManagement.service;

import com.eonix.personManagement.model.Person;
import com.eonix.personManagement.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonService {

    private final PersonRepository personRepository;

    public List<Person> getAllPersons(String firstName, String lastName) {
        if (firstName != null || lastName != null) {
            return personRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(firstName, lastName);
        }
        return personRepository.findAll();
    }

    public Optional<Person> getPersonById(long id) {
        return personRepository.findById(id);
    }

    public Person addPerson(Person person) {
        return personRepository.save(person);
    }

    public Person updatePerson(long id, Person personDetails) {
        return personRepository.findById(id).map(person -> {
            person.setFirstName(personDetails.getFirstName());
            person.setLastName(personDetails.getLastName());
            return personRepository.save(person);
        }).orElseThrow(() -> new RuntimeException("Person not found with id " + id));
    }

    public void deletePerson(long id) {
        personRepository.deleteById(id);
    }
}