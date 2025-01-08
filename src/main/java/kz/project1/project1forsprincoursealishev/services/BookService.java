package kz.project1.project1forsprincoursealishev.services;

import kz.project1.project1forsprincoursealishev.models.Book;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();
    Book getBookById(Long id);
    void saveBook(Book book, BindingResult bindingResult);
    void deleteBookById(Long id);
    void updateBook(Book book);
}
