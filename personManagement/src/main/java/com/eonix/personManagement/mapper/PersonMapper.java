package com.eonix.personManagement.mapper;

import com.eonix.personManagement.dto.PersonDto;
import com.eonix.personManagement.model.Person;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {

    public PersonDto convertToDto(Person person) {
        if (person == null) {
            return null;
        }
        PersonDto dto = new PersonDto();
        dto.setFirstName(person.getFirstName());
        dto.setLastName(person.getLastName());
        return dto;
    }

    public Person convertToEntity(PersonDto dto) {
        if (dto == null) {
            return null;
        }
        Person person = new Person();
        person.setFirstName(dto.getFirstName());
        person.setLastName(dto.getLastName());
        return person;
    }
}