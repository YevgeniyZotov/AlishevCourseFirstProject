package kz.project1.project1forsprincoursealishev.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import kz.project1.project1forsprincoursealishev.models.Person;
import kz.project1.project1forsprincoursealishev.repositories.PersonCustomRepository;

import java.util.Optional;

public class PersonCustomRepositoryImpl implements PersonCustomRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Person> findByIdWithBooks(long id) {
        TypedQuery<Person> query = entityManager.createQuery(
                "SELECT p FROM Person p LEFT JOIN FETCH p.books WHERE p.id = :id", Person.class);
        query.setParameter("id", id);
        return query.getResultStream().findFirst();
    }
}
