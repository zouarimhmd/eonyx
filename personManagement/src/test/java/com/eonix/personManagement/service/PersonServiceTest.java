package com.eonix.personManagement.service;


import com.eonix.personManagement.model.Person;
import com.eonix.personManagement.repository.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PersonServiceTest {

    @InjectMocks
    private PersonService personService;

    @Mock
    private PersonRepository personRepository;

    private Person person;
    private long personId;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        personId = 1L;
        person = new Person(personId, "Carlos", "Alvarez");
    }

    @Test
    public void testGetAllPersons_WithFilters_HappyPath() {
        Person person = new Person(personId, "Carlos", "Alvarez");
        List<Person> persons = Collections.singletonList(person);
        when(personRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase("Carlos", "Alvarez"))
                .thenReturn(persons);
        List<Person> result = personService.getAllPersons("Carlos", "Alvarez");
        assertEquals(1, result.size(), "The result list should contain exactly one person.");
        assertEquals("Carlos", result.get(0).getFirstName(), "First name should be Carlos.");
        assertEquals("Alvarez", result.get(0).getLastName(), "Last name should be Alvarez.");
    }

    @Test
    public void testGetAllPersons_NoFilters_HappyPath() {
        List<Person> persons = Collections.singletonList(person);
        when(personRepository.findAll()).thenReturn(persons);

        List<Person> result = personService.getAllPersons(null, null);
        assertEquals(1, result.size());
        assertEquals("Carlos", result.get(0).getFirstName());
        assertEquals("Alvarez", result.get(0).getLastName());
    }

    @Test
    public void testGetPersonById_HappyPath() {
        when(personRepository.findById(personId)).thenReturn(Optional.of(person));

        Optional<Person> result = personService.getPersonById(personId);
        assertTrue(result.isPresent());
        assertEquals("Carlos", result.get().getFirstName());
        assertEquals("Alvarez", result.get().getLastName());
    }

    @Test
    public void testGetPersonById_UnhappyPath() {
        when(personRepository.findById(personId)).thenReturn(Optional.empty());

        Optional<Person> result = personService.getPersonById(personId);
        assertFalse(result.isPresent());
    }

    @Test
    public void testAddPerson_HappyPath() {
        when(personRepository.save(any(Person.class))).thenReturn(person);

        Person result = personService.addPerson(person);
        assertNotNull(result);
        assertEquals("Carlos", result.getFirstName());
        assertEquals("Alvarez", result.getLastName());
    }

    @Test
    public void testUpdatePerson_HappyPath() {
        Person updatedPerson = new Person(personId, "Jane", "Alvarez");
        when(personRepository.findById(personId)).thenReturn(Optional.of(person));
        when(personRepository.save(any(Person.class))).thenReturn(updatedPerson);

        Person result = personService.updatePerson(personId, updatedPerson);
        assertNotNull(result);
        assertEquals("Jane", result.getFirstName());
    }

    @Test
    public void testUpdatePerson_UnhappyPath() {
        when(personRepository.findById(personId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            personService.updatePerson(personId, person);
        });

        assertEquals("Person not found with id " + personId, exception.getMessage());
    }

    @Test
    public void testDeletePerson_HappyPath() {
        doNothing().when(personRepository).deleteById(personId);

        assertDoesNotThrow(() -> personService.deletePerson(personId));
        verify(personRepository, times(1)).deleteById(personId);
    }
}