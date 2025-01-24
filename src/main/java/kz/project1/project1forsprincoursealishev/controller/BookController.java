package kz.project1.project1forsprincoursealishev.controller;

import jakarta.validation.Valid;
import kz.project1.project1forsprincoursealishev.models.Book;
import kz.project1.project1forsprincoursealishev.models.Person;
import kz.project1.project1forsprincoursealishev.repositories.BookRepository;
import kz.project1.project1forsprincoursealishev.services.BookService;
import kz.project1.project1forsprincoursealishev.services.PersonService;
import kz.project1.project1forsprincoursealishev.validators.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final BookValidator bookValidator;
    private final PersonService personService;
    private final BookRepository bookRepository;

    @Autowired
    public BookController(BookService bookService, BookValidator bookValidator, PersonService personService, BookRepository bookRepository) {
        this.bookService = bookService;
        this.bookValidator = bookValidator;
        this.personService = personService;
        this.bookRepository = bookRepository;
    }

    // Можно использовать это и не вызывать каждый раз валидатор при помощи bookValidator.validate(book, bindingResult);
    @InitBinder("book")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(bookValidator);
    }

    @GetMapping
    public String getAllBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "book/books";
    }

    @GetMapping("/{id}")
    public String getBook(@PathVariable("id") long id, Model model) {
        Book book = bookService.getBookById(id);

        List<Person> people = personService.getAllPeople();

        if (book != null) {
            model.addAttribute("book", book);
            model.addAttribute("people", people);
            return "book/bookDetail";
        }

        return "redirect:/books";
    }

    @PostMapping("/{id}")
    public String assignBook(@PathVariable("id") long id, @RequestParam("person") long personId) {
        Book book = bookService.getBookById(id);
        if (book == null) {
            throw new IllegalArgumentException("Book with ID " + id + " not found");
        }

        Person person = personService.getPersonById(personId);
        if (person == null) {
            throw new IllegalArgumentException("Person with ID " + personId + " not found");
        }

        // Присваиваем книгу человеку
        book.setPerson(person);
        bookRepository.save(book);

        return "redirect:/books/{id}";
    }

    @GetMapping("/new")
    public String showNewBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "book/bookForm";
    }

    @PostMapping("/new")
    public String newBook(@Valid @ModelAttribute Book book, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "book/bookForm";
        }

        bookService.saveBook(book, bindingResult);

        return "redirect:/books";
    }

    @PostMapping("/{id}/delete")
    public String deleteBook(@PathVariable("id") long id) {
        bookService.deleteBookById(id);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String showEditBookForm(@PathVariable long id, Model model) {
        Book book = bookService.getBookById(id);

        if (book != null) {
            model.addAttribute("book", book);
            return "book/bookEditForm";
        } else {
            return "redirect:/books";
        }
    }

    @PutMapping("/{id}/edit")
    public String updateBook(@PathVariable long id, @Valid @ModelAttribute Book book, BindingResult bindingResult) {
        book.setId(id);

        if (bindingResult.hasErrors()) return "book/bookEditForm";

        bookService.saveBook(book, bindingResult);

        return "redirect:/books";
    }

    @PostMapping("/release/{id}")
    public String releaseBook(@PathVariable long id) {
        bookService.releaseBook(id);

        return "redirect:/books";
    }
}
