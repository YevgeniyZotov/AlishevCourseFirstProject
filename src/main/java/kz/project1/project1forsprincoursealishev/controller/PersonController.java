/*
package kz.project1.project1forsprincoursealishev.controller;

import kz.project1.project1forsprincoursealishev.services.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/person")
public class PersonController {
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    public String getAllPersons(Model model) {
        model.addAttribute("persons", personService.getAllPersons());
        return "persons";
    }
}
*/
