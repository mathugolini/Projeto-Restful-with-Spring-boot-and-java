package br.com.hugolini.util.impl;

import br.com.hugolini.dto.PersonDto;
import br.com.hugolini.dto.factory.PersonDtoFactory;
import br.com.hugolini.model.Person;
import br.com.hugolini.model.factory.PersonFactory;
import br.com.hugolini.util.PersonMapper;

import java.util.List;

public class PersonMapperImpl implements PersonMapper {

    @Override
    public PersonDto personToDto(Person person) {
        return PersonDtoFactory.create(
                person.getId(),
                person.getFirstName(),
                person.getLastName(),
                person.getAddress(),
                person.getGender()
        );
    }

    @Override
    public List<PersonDto> personToDto(List<Person> person) {
        return person.stream().map(this::personToDto).toList();
    }

    @Override
    public Person dtoToPerson(PersonDto personDto) {

        return PersonFactory.create(
                personDto.getId(),
                personDto.getFirstName(),
                personDto.getLastName(),
                personDto.getAddress(),
                personDto.getGender()
        );
    }

    @Override
    public List<Person> dtoToPerson(List<PersonDto> personDto) {
        return personDto.stream().map(this::dtoToPerson).toList();
    }
}
