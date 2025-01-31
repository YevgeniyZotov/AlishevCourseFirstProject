package kz.project1.project1forsprincoursealishev.repositories;

import kz.project1.project1forsprincoursealishev.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long>, BookCustomRepository {

}
