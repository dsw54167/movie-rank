package dsw.projects.demo.movies;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity(name = "MovieReadModelEntity")
@Table(name = "movies_read_model")
@Getter
@NoArgsConstructor
public class MovieReadModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "movie_id", unique = true, nullable = false)
    private Long movieId;

    public MovieReadModel(Long movieId) {
        this.movieId = movieId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MovieReadModel movie = (MovieReadModel) o;
        return Objects.equals(movieId, movie.movieId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(movieId);
    }
}
