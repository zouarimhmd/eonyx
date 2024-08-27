package com.eonix.personManagement.mapper;

import com.eonix.personManagement.model.Person;
import com.eonix.personManagement.dto.PersonDto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class PersonMapperTest {

    @Autowired
    private PersonMapper mapper;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testConvertToDto_HappyPath() {
        Person person = new Person();
        person.setFirstName("Carlos");
        person.setLastName("Alvarez");

        PersonDto expectedDto = new PersonDto();
        expectedDto.setFirstName("Carlos");
        expectedDto.setLastName("Alvarez");

        PersonDto actualDto = mapper.convertToDto(person);

        assertEquals(expectedDto.getFirstName(), actualDto.getFirstName());
        assertEquals(expectedDto.getLastName(), actualDto.getLastName());
    }

    @Test
    public void testConvertToEntity_HappyPath() {
        PersonDto personDto = new PersonDto();
        personDto.setFirstName("Carlita");
        personDto.setLastName("Alvarez");

        Person expectedPerson = new Person();
        expectedPerson.setFirstName("Carlita");
        expectedPerson.setLastName("Alvarez");

        Person actualPerson = mapper.convertToEntity(personDto);

        assertEquals(expectedPerson.getFirstName(), actualPerson.getFirstName());
        assertEquals(expectedPerson.getLastName(), actualPerson.getLastName());
    }

    @Test
    public void testConvertToDto_NullPerson() {
        PersonDto actualDto = mapper.convertToDto(null);
        assertNull(actualDto);
    }

    @Test
    public void testConvertToEntity_NullPersonDto() {
        Person actualPerson = mapper.convertToEntity(null);
        assertNull(actualPerson);
    }
}
