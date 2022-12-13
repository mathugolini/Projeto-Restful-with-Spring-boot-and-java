package br.com.hugolini.unittests.dto;


import br.com.hugolini.dto.PersonDto;
import br.com.hugolini.dto.factory.PersonDtoFactory;
import br.com.hugolini.model.Person;
import br.com.hugolini.model.factory.PersonFactory;
import br.com.hugolini.util.impl.PersonMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@ExtendWith(MockitoExtension.class)
class PersonDtoTest {

    @Spy
    PersonMapperImpl mapper;
    Person person;
    PersonDto personDto;

    @BeforeEach
    void setUp() {
        startPersonDto();
        startPerson();
    }

    @Test
    void shouldCreateAPersonWithAMapper() {
        var person = mapper.dtoToPerson(personDto);

        assertNotNull(person);
        assertEquals(personDto.getId(), person.getId());
        assertEquals(personDto.getFirstName(), person.getFirstName());
        assertEquals(personDto.getLastName(), person.getLastName());
        assertEquals(personDto.getAddress(), person.getAddress());
        assertEquals(personDto.getGender(), person.getGender());
    }

    @Test
    void shouldReturnAListOfPerson() {
        var personsDto = getListOfPeopleDto();
        var people = mapper.dtoToPerson(personsDto);

        assertNotNull(people);
        assertEquals(personsDto.size(), people.size());
    }

    @Test
    void shouldCreateAPersonDtoWithAMapper() {
        var personDto = mapper.personToDto(person);

        assertNotNull(personDto);
        assertEquals(person.getId(), personDto.getId());
        assertEquals(person.getFirstName(), personDto.getFirstName());
        assertEquals(person.getLastName(), personDto.getLastName());
        assertEquals(person.getAddress(), personDto.getAddress());
        assertEquals(person.getGender(), personDto.getGender());
    }

    @Test
    void shouldReturnAListOfPersonDto() {
        var people = getListOfPeople();
        var personsDto = mapper.personToDto(people);

        assertNotNull(personsDto);
        assertEquals(people.size(), personsDto.size());
    }

    private void startPerson() {
        person = PersonFactory.create(
                1L,
                "John",
                "Doe",
                "Spring Boot Street, 123",
                "Male"
        );
    }
    private void startPersonDto() {
        personDto = PersonDtoFactory.create(
                1L,
                "John",
                "Doe",
                "Spring Boot Street, 123",
                "Male"
        );
    }

    private List<Person> getListOfPeople() {
        var people = new ArrayList<Person>();

        people.add(person);

        return people;
    }

    private List<PersonDto> getListOfPeopleDto() {
        var people = new ArrayList<PersonDto>();

        people.add(personDto);

        return people;
    }
}