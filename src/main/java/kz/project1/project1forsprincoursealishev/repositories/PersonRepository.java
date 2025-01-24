package kz.project1.project1forsprincoursealishev.repositories;

import kz.project1.project1forsprincoursealishev.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    @Query("SELECT p FROM Person p LEFT JOIN FETCH p.books WHERE p.id = :id")
    Optional<Person> findByIdWithBooks(@Param("id") long id);
}
