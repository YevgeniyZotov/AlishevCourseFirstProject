package kz.project1.project1forsprincoursealishev.validators;

import kz.project1.project1forsprincoursealishev.models.Book;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BookValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;

        // Проверка для title
        if (book.getTitle() == null || book.getTitle().isEmpty()) {
            errors.rejectValue("title", "book.title.empty", "Title cannot be empty");
        } else if (book.getTitle().length() < 2 || book.getTitle().length() > 200) {
            errors.rejectValue("title", "book.title.size", "Title must be between 2 and 200 characters");
        }

        // Проверка для author
        if (book.getAuthor() == null || book.getAuthor().isEmpty()) {
            errors.rejectValue("author", "book.author.empty", "Author cannot be empty");
        } else if (book.getAuthor().length() < 2 || book.getAuthor().length() > 100) {
            errors.rejectValue("author", "book.author.size", "Author must be between 2 and 100 characters");
        }

        // Проверка для year
        if (book.getYear() < 1000 || book.getYear() > 2025) {
            errors.rejectValue("year", "book.year.size", "Year must be between 1000 and 2025");
        }
    }
}
