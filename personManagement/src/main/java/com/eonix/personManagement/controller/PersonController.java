package com.eonix.personManagement.controller;

import com.eonix.personManagement.dto.PersonDto;
import com.eonix.personManagement.mapper.PersonMapper;
import com.eonix.personManagement.model.Person;
import com.eonix.personManagement.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    private final PersonMapper mapper;

    @GetMapping
    public List<PersonDto> getAllPersons(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName) {

        return personService.getAllPersons(firstName, lastName).stream()
                .map(mapper::convertToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDto> getPersonById(@PathVariable long id) {
        Optional<Person> person = personService.getPersonById(id);
        return person.map(p -> ResponseEntity.ok(mapper.convertToDto(p)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public PersonDto addPerson(@RequestBody PersonDto personDto) {
        if (personDto.getFirstName().isEmpty() || personDto.getLastName().isEmpty()) {
            throw new RuntimeException("First name and last name cannot be empty");
        }
        Person person = mapper.convertToEntity(personDto);
        Person savedPerson = personService.addPerson(person);
        return mapper.convertToDto(savedPerson);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDto> updatePerson(@PathVariable long id, @RequestBody PersonDto personDto) {
        try {
            Person person = mapper.convertToEntity(personDto);
            Person updatedPerson = personService.updatePerson(id, person);
            return ResponseEntity.ok(mapper.convertToDto(updatedPerson));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable long id) {
        personService.deletePerson(id);
        return ResponseEntity.noContent().build();
    }

}