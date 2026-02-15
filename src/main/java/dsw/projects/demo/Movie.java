package dsw.projects.demo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity(name = "MovieEntity")
@Table(name = "movies")
@Getter
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "rating")
    private Float rating;

    public Movie(String title) {
        this.title = title;
    }

}
