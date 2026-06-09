package dsw.projects.demo.movies;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRatingRepository extends JpaRepository<MovieRating, Long> {
    Optional<MovieRating> findByMovieId(Long id);
}
