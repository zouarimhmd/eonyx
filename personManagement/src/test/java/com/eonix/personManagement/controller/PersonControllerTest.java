package com.eonix.personManagement.controller;

import com.eonix.personManagement.dto.PersonDto;
import com.eonix.personManagement.mapper.PersonMapper;
import com.eonix.personManagement.model.Person;
import com.eonix.personManagement.service.PersonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class PersonControllerTest {

    @InjectMocks
    private PersonController personController;

    @Mock
    private PersonService personService;

    @Mock
    private PersonMapper mapper;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    private PersonDto personDto;
    private long personId;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
        objectMapper = new ObjectMapper();
        personId = 1L;
        personDto = new PersonDto();
        personDto.setFirstName("Carlos");
        personDto.setLastName("Alvarez");
    }

    @Test
    public void testGetPersonById_HappyPath() throws Exception {
        Person person = new Person(personId, "Carlos", "Alvarez");
        PersonDto personDto = new PersonDto("Carlos", "Alvarez");

        when(personService.getPersonById(personId)).thenReturn(Optional.of(person));
        when(mapper.convertToDto(person)).thenReturn(personDto);

        mockMvc.perform(get("/api/persons/{id}", personId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Carlos"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Alvarez"))
                .andReturn();
    }

    @Test
    public void testGetPersonById_UnhappyPath() throws Exception {
        when(personService.getPersonById(personId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/persons/{id}", personId))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testAddPerson_HappyPath() throws Exception {
        Person person = new Person(personId, "Carlos", "Alvarez");
        PersonDto personDto = new PersonDto("Carlos", "Alvarez");

        when(mapper.convertToEntity(personDto)).thenReturn(person);
        when(personService.addPerson(person)).thenReturn(person);
        when(mapper.convertToDto(person)).thenReturn(personDto);

        mockMvc.perform(post("/api/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Carlos"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Alvarez"));
    }

    @Test
    public void testUpdatePerson_HappyPath() throws Exception {
        Person person = new Person(personId, "Carlos", "Alvarez");
        PersonDto personDto = new PersonDto("Carlos", "Alvarez");

        when(mapper.convertToEntity(personDto)).thenReturn(person);
        when(personService.updatePerson(eq(personId), any(Person.class))).thenReturn(person);
        when(mapper.convertToDto(person)).thenReturn(personDto);

        mockMvc.perform(put("/api/persons/{id}", personId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(personDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Carlos"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Alvarez"));
    }

    @Test
    public void testDeletePerson_HappyPath() throws Exception {
        doNothing().when(personService).deletePerson(personId);

        mockMvc.perform(delete("/api/persons/{id}", personId))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
