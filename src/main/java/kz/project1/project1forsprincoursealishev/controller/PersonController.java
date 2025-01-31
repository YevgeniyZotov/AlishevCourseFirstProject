package kz.project1.project1forsprincoursealishev.controller;

import kz.project1.project1forsprincoursealishev.models.Person;
import kz.project1.project1forsprincoursealishev.services.PersonService;
import kz.project1.project1forsprincoursealishev.validators.PersonValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PersonController {
    private final PersonService personService;
    private final PersonValidator personValidator;

    public PersonController(PersonService personService, PersonValidator personValidator) {
        this.personService = personService;
        this.personValidator = personValidator;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(personValidator);
    }

    @GetMapping
    public String getAllPeople(Model model) {
        model.addAttribute("people", personService.getAllPeople());
        return "people/people";
    }

    @GetMapping("/{id}")
    public String getPerson(@PathVariable("id") long id,  Model model) {
        Person person = personService.getPersonWithBooks(id);
        model.addAttribute("person", person);
        model.addAttribute("books", person.getBooks());
        return "people/person";
    }

    @GetMapping("/new")
    public String showNewPersonForm(Model model) {
        model.addAttribute("person", new Person());
        return "people/personForm";
    }

    @PostMapping("/new")
    public String saveNewPerson(@Valid @ModelAttribute Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "people/personForm";
        }

        personService.savePerson(person, bindingResult);

        return "redirect:/people";
    }

    @GetMapping("{id}/edit")
    public String showEditPersonForm(@PathVariable("id") long id, Model model) {
        Person person = personService.getPersonById(id);
        if (person == null) {
            return "redirect:/people";
        }

        model.addAttribute("person", person);
        return "people/personEditForm";
    }

    @PutMapping("{id}/edit")
    public String updatePerson(@PathVariable long id, @Valid @ModelAttribute Person person, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "people/personEditForm";
        }

        person.setId(id);
        personService.savePerson(person, bindingResult);
        return "redirect:/people/" + id;
    }

    @PostMapping("{id}/delete")
    public String deletePerson(@PathVariable long id) {
        personService.deletePersonById(id);
        return "redirect:/people";
    }
}

