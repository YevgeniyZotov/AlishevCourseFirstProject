package kz.project1.project1forsprincoursealishev.controller;

import jakarta.validation.Valid;
import kz.project1.project1forsprincoursealishev.models.Book;
import kz.project1.project1forsprincoursealishev.services.BookService;
import kz.project1.project1forsprincoursealishev.validators.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    private final BookValidator bookValidator;

    @Autowired
    public BookController(BookService bookService, BookValidator bookValidator) {
        this.bookService = bookService;
        this.bookValidator = bookValidator;
    }

    @GetMapping
    public String getAllBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "books";
    }

    @GetMapping("/{id}")
    public String getBook(@PathVariable("id") long id, Model model) {
        Book book = bookService.getBookById(id);
        if (book != null) {
            model.addAttribute("book", book);
            return "bookDetail";
        }

        return "redirect:/books";
    }

    @GetMapping("/new")
    public String showNewBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "bookForm";
    }

    @PostMapping("/new")
    public String newBook(@Valid @ModelAttribute Book book, BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors()) {
            return "bookForm";
        }

        bookService.saveBook(book, bindingResult);

        return "redirect:/books";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") long id) {
        bookService.deleteBookById(id);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String showEditBookForm(@PathVariable long id, Model model) {
        Book book = bookService.getBookById(id);

        if (book != null) {
            model.addAttribute("book", book);
            return "editBookForm";
        } else {
            return "redirect:/books";
        }
    }

    @PutMapping("/{id}/edit")
    public String updateBook(@PathVariable long id,@Valid @ModelAttribute Book book, BindingResult bindingResult) {
        book.setId(id);

        if (bindingResult.hasErrors()) return "bookForm";

        bookService.saveBook(book, bindingResult);

        return "redirect:/books";
    }
}
