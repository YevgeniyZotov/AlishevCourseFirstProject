package kz.project1.project1forsprincoursealishev.repositories;

import kz.project1.project1forsprincoursealishev.models.Book;
import kz.project1.project1forsprincoursealishev.models.Person;

import java.util.List;

public interface BookCustomRepository {
    List<Book> searchByTitleStartingWith(String title);
}
