package kz.project1.project1forsprincoursealishev.repositories;

import kz.project1.project1forsprincoursealishev.models.Person;

import java.util.Optional;

public interface PersonCustomRepository {
    Optional<Person> findByIdWithBooks(long id);
}
