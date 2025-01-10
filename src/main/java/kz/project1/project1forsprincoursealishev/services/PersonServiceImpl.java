package kz.project1.project1forsprincoursealishev.services;

import kz.project1.project1forsprincoursealishev.models.Person;
import kz.project1.project1forsprincoursealishev.repositories.PersonRepository;
import kz.project1.project1forsprincoursealishev.validators.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    private final PersonValidator personValidator;

    @Autowired
    public PersonServiceImpl(PersonRepository personRepository, PersonValidator personValidator) {
        this.personRepository = personRepository;
        this.personValidator = personValidator;
    }

    @Override
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @Override
    public Person getPersonById(Long id) {
        return personRepository.findById(id).orElse(null);
    }

    @Override
    public void savePerson(Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
        } else {
            personRepository.save(person);
        }
    }

    @Override
    public void deleteBookById(Long id) {
        personRepository.deleteById(id);
    }
}
