package kz.project1.project1forsprincoursealishev.repositories.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import kz.project1.project1forsprincoursealishev.models.Book;
import kz.project1.project1forsprincoursealishev.repositories.BookCustomRepository;

import java.util.List;

public class BookCustomRepositoryImpl implements BookCustomRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Book> searchByTitleStartingWith(String title) {
        String queryStr = "SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(:title)";
        TypedQuery<Book> query = entityManager.createQuery(queryStr, Book.class);
        query.setParameter("title", title + "%");
        return query.getResultList();
    }
}
