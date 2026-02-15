package dsw.projects.demo;

import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(path = "/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieRepository repository;

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MovieDto> getById(@PathVariable Long id) throws MovieNotFoundException {
        MovieDto movieDto = repository.findById(id)
                .map(movie -> new MovieDto(movie.getId(), movie.getTitle(), movie.getRating()))
                .orElseThrow(() -> new MovieNotFoundException(id));
        return ResponseEntity.ok(movieDto);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Page<MovieDto>> getAll(
            @SortDefault(sort = "rating", direction = Sort.Direction.DESC) @ParameterObject Pageable pageable) {
        Page<Movie> all = repository.findAll(pageable);
        Page<MovieDto> dtoPage = all.map(movie -> new MovieDto(movie.getId(), movie.getTitle(), movie.getRating()));
        return ResponseEntity.ok(dtoPage);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MovieDto> create(
            UriComponentsBuilder uriBuilder,
            @RequestBody CreateMovieRequest request) {
        var movie = repository.save(new Movie(request.title()));
        var dto = new MovieDto(movie.getId(), movie.getTitle(), movie.getRating());
        return ResponseEntity.created(
                        uriBuilder.path("/movies/{id}")
                                .buildAndExpand(movie.getId())
                                .toUri())
                .body(dto);
    }
}
