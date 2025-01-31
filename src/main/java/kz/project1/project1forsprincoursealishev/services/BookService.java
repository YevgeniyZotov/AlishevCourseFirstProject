package kz.project1.project1forsprincoursealishev.services;

import kz.project1.project1forsprincoursealishev.models.Book;
import kz.project1.project1forsprincoursealishev.models.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();

    Page<Book> getAllBooks(Pageable pageable);

    Book getBookById(Long id);

    void saveBook(Book book, BindingResult bindingResult);

    void deleteBookById(Long id);

    void updateBook(Book book);

    void releaseBook(Long id);

    List<Book> searchBookByTitle(String title);

    void assignBookToPerson(Long bookId, Person person);
}
