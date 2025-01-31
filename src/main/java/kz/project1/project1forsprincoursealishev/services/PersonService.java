package kz.project1.project1forsprincoursealishev.services;

import kz.project1.project1forsprincoursealishev.models.Book;
import kz.project1.project1forsprincoursealishev.models.Person;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface PersonService {
    Person getPersonById(Long id);

    void savePerson(Person person, BindingResult bindingResult);

    void deletePersonById(Long id);

    List<Person> getAllPeople();

    Person getPersonWithBooks(Long id);

    void updatePerson(Person person, BindingResult bindingResult);
}
