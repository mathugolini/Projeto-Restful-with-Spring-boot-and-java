package br.com.hugolini.service.impl;

import br.com.hugolini.controller.PersonController;
import br.com.hugolini.dto.PersonDto;

import br.com.hugolini.exceptions.RequiredObjectIsNullException;
import br.com.hugolini.exceptions.ResourceNotFoundException;
import br.com.hugolini.repositories.PersonRepository;
import br.com.hugolini.service.PersonService;
import br.com.hugolini.util.impl.PersonMapperImpl;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class PersonServiceImpl implements PersonService {

    Logger LOGGER = Logger.getLogger(PersonServiceImpl.class.getName());
    private final PersonRepository personRepository;
    private final PersonMapperImpl mapper;

    public PersonServiceImpl(PersonRepository personRepository, PersonMapperImpl mapper) {
        this.personRepository = personRepository;
        this.mapper = mapper;
    }

    @Override
    public PersonDto findById(Long id) {
        LOGGER.info("Find person by id: " + id);

        var personDto = mapper.personToDto(personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + id)));

        personDto.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());

        return personDto;
    }

    @Override
    public Page<PersonDto> findAll(Pageable pageable) {
        LOGGER.info("Find all people");

        var people = personRepository.findAll(pageable);
        var peopleDto = people.map(mapper::personToDto);

        peopleDto.forEach(personDto -> personDto.add(linkTo(methodOn(PersonController.class)
                .findById(personDto.getId()))
                .withSelfRel()));

        return peopleDto;
    }

    @Transactional
    @Override
    public PersonDto save(PersonDto personDto) {
        LOGGER.info("Save person");

        isPersonNull(personDto);

        var personDtoSaved = mapper.personToDto(personRepository.save(mapper.dtoToPerson(personDto)));
        personDto.add(linkTo(methodOn(PersonController.class).findById(personDtoSaved.getId())).withSelfRel());

        return personDtoSaved;
    }

    @Transactional
    @Override
    public PersonDto update(PersonDto personDto) {
        LOGGER.info("Update person");

        isPersonNull(personDto);

        var entity = personRepository.findById(personDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Person not found with id: " + personDto.getId()));

        entity.setFirstName(personDto.getFirstName());
        entity.setLastName(personDto.getLastName());
        entity.setAddress(personDto.getAddress());
        entity.setGender(personDto.getGender());

        var personDtoUpdated = mapper.personToDto(personRepository.save(entity));
        personDtoUpdated.add(linkTo(methodOn(PersonController.class).findById(personDtoUpdated.getId())).withSelfRel());

        return personDtoUpdated;
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        LOGGER.info("Delete person by id: " + id);
        findById(id);
        personRepository.deleteById(id);
    }

    private static void isPersonNull(PersonDto personDto) {
        if (personDto == null) {
            throw new RequiredObjectIsNullException("It cannot persist a null object");
        }
    }
}
