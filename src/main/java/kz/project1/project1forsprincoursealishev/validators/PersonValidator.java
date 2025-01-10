package kz.project1.project1forsprincoursealishev.validators;

import kz.project1.project1forsprincoursealishev.models.Person;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

@Component
public class PersonValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        // Проверка на fullName
        if (person.getFullName() == null || person.getFullName().trim().isEmpty() || person.getFullName().length() < 2 || person.getFullName().length() > 100) {
            errors.rejectValue("fullName", "person.fullName.invalid", "Name should be between 2 and 100 characters");
        }

        // Проверка года рождения
        int currentYear = LocalDate.now().getYear();
        if (person.getId() < 1900 || person.getBirthYear() > currentYear) {
            errors.rejectValue("birthYear", "person.birthYear.invalid", "Birth year should be between 1900 and the current year");
        }
    }
}
