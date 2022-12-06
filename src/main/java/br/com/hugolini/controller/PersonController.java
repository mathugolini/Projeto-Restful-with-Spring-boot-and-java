package br.com.hugolini.controller;

import br.com.hugolini.model.Person;
import br.com.hugolini.services.PersonServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
   private PersonServices services;

    @RequestMapping(method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Person> findAll() {

        return services.findAll();
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Person findById(
            @PathVariable(value = "id") Long id) throws Exception {
        return services.findById(id);
    }

    @RequestMapping(method = RequestMethod.POST,
                    consumes = MediaType.APPLICATION_JSON_VALUE,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    public Person create(@RequestBody Person person) {
        return services.create(person);
    }

    @RequestMapping(method = RequestMethod.PUT,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Person update(@RequestBody Person person) {
        return services.update(person);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void delete(@PathVariable(value = "id") Long id) {
        services.delete(id);
    }
}
