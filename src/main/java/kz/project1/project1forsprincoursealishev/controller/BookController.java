package kz.project1.project1forsprincoursealishev.controller;

import jakarta.validation.Valid;
import kz.project1.project1forsprincoursealishev.models.Book;
import kz.project1.project1forsprincoursealishev.models.Person;
import kz.project1.project1forsprincoursealishev.repositories.BookRepository;
import kz.project1.project1forsprincoursealishev.services.BookService;
import kz.project1.project1forsprincoursealishev.services.PersonService;
import kz.project1.project1forsprincoursealishev.validators.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @InitBinder("book")
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(bookValidator);
    }

    @GetMapping
    public String getAllBooks(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "5") int booksPerPage,
                              @RequestParam(defaultValue = "false") boolean sortByYear,
                              Model model) {
        Sort sort = sortByYear ? Sort.by("year").ascending() : Sort.unsorted();
        Pageable pageable = PageRequest.of(page, booksPerPage, sort);
        Page<Book> bookPage = bookService.getAllBooks(pageable);

        model.addAttribute("books", bookPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", bookPage.getTotalPages());
        model.addAttribute("booksPerPage", booksPerPage);
        model.addAttribute("sortByYear", sortByYear);
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

        book.setPerson(person);

        if (book.getTakenAt() == null) {
            book.setTakenAt(LocalDateTime.now());
        }

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

    @GetMapping("/search")
    public String searchBooks(@RequestParam(name = "query", required = false) String query, Model model) {
        List<Book> books = (query != null && !query.isEmpty()) ? bookService.searchBookByTitle(query) : List.of();
        model.addAttribute("books", books);
        model.addAttribute("query", query);
        model.addAttribute("isEmpty", books.isEmpty());
        return "book/search";
    }
}
