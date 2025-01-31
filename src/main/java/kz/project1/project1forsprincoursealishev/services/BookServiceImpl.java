package kz.project1.project1forsprincoursealishev.services;

import kz.project1.project1forsprincoursealishev.models.Book;
import kz.project1.project1forsprincoursealishev.models.Person;
import kz.project1.project1forsprincoursealishev.repositories.BookRepository;
import kz.project1.project1forsprincoursealishev.validators.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookValidator bookValidator;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, BookValidator bookValidator) {
        this.bookRepository = bookRepository;
        this.bookValidator = bookValidator;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public void saveBook(Book book, BindingResult bindingResult) {
        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> System.out.println(error.getDefaultMessage()));
        } else {
            bookRepository.save(book);
        }
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public void updateBook(Book book) {
        Book updatedBook = bookRepository.findById(book.getId()).orElse(null);

        if (updatedBook != null) {
            updatedBook.setTitle(book.getTitle());
            updatedBook.setAuthor(book.getAuthor());
            updatedBook.setYear(book.getYear());

            bookRepository.save(updatedBook);
        } else {
            throw new RuntimeException("Book not found with id: " + book.getId());
        }
    }

    @Override
    public void releaseBook(Long id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            book.setPerson(null);
            bookRepository.save(book);
        }
    }

    @Override
    public List<Book> searchBookByTitle(String title) {
        return bookRepository.searchByTitleStartingWith(title);
    }

    @Override
    public void assignBookToPerson(Long bookId, Person person) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Книга не найдена"));
        book.setPerson(person);

        if (book.getTakenAt() == null) {
            book.setTakenAt(LocalDateTime.now());
        }

        bookRepository.save(book);
    }
}
