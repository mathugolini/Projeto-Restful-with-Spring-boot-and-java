package br.com.hugolini.util;

import br.com.hugolini.dto.PersonDto;
import br.com.hugolini.model.Person;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface PersonMapper {

    PersonDto personToDto(Person person);
    List<PersonDto> personToDto(List<Person> person);
    Person dtoToPerson(PersonDto personDto);
    List<Person> dtoToPerson(List<PersonDto> personDto);

}
