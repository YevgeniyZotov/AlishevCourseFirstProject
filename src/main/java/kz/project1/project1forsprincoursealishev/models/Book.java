package kz.project1.project1forsprincoursealishev.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.Objects;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    @Size(min = 2, max = 200)
    @Column(name = "title", nullable = false)
    private String title;

    @NotEmpty
    @Size(min = 2, max = 100)
    @Column(name = "author", nullable = false)
    private String author;

    @Min(1000)
    @Max(2025)
    @Column(name = "year", nullable = false)
    private int year;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = true) // НЕ ЗАБЫТЬ УБРАТЬ nullable на false
    private Person person;

    public Book() {
    }

    public Book(long id, String title, String author, int year, Person person) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.person = person;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                ", person=" + person.getFullName() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && year == book.year && title.equals(book.title) && author.equals(book.author) && person.equals(book.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, year, person);
    }
}
