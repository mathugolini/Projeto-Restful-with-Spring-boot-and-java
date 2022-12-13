package br.com.hugolini.model.factory;

import br.com.hugolini.model.Person;

public interface PersonFactory {

    static Person create(Long id, String firstName, String lastName, String address, String gender) {
        var person = new Person();
        person.setId(id);
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setAddress(address);
        person.setGender(gender);

        return person;
    }
}
