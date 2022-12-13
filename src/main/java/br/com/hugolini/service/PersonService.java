package br.com.hugolini.service;

import br.com.hugolini.dto.PersonDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PersonService {

    PersonDto findById(Long id);
    Page<PersonDto> findAll(Pageable pageable);
    PersonDto save(PersonDto personDto);
    PersonDto update(PersonDto personDto);
    void deleteById(Long id);
}
