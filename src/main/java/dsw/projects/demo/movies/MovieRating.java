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

@Entity(name = "MovieRatingEntity")
@Table(name = "movies_rating")
@Getter
@NoArgsConstructor
public class MovieRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "movie_id")
    private Long movieId;

    @Column(name = "rating")
    private Double rating;

    public MovieRating(Long movieId) {
        this.movieId = movieId;
    }

    public void updateRating(Double rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MovieRating movie = (MovieRating) o;
        return Objects.equals(id, movie.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(movieId);
    }
}
