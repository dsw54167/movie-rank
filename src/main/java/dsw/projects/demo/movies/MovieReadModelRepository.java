package dsw.projects.demo.movies;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieReadModelRepository extends JpaRepository<MovieReadModel, Long> {

    boolean existsById(Long id);
}
