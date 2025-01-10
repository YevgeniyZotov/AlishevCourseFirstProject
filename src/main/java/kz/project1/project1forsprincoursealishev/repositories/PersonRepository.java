package kz.project1.project1forsprincoursealishev.repositories;

import kz.project1.project1forsprincoursealishev.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
