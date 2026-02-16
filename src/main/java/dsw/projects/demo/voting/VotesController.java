package dsw.projects.demo.voting;

import dsw.projects.demo.movies.MovieDto;
import dsw.projects.demo.movies.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(path = "/votes")
@RequiredArgsConstructor
@Transactional
public class VotesController {

    private final VoteRepository repository;
    private final MovieRepository movieRepository;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MovieDto> create(
            UriComponentsBuilder uriBuilder,
            @RequestBody VoteRequest request) throws VoteAlreadyExists, VoteForNonExistingMovieException {
        if (repository.existsByMovieIdAndEmail(request.movieId(), request.email())) {
            throw new VoteAlreadyExists("Email %s already voted for movie %s".formatted(request.email(), request.movieId()));
        }
        if (!movieRepository.existsById(request.movieId())) {
            throw new VoteForNonExistingMovieException("Movie %s not exists".formatted(request.movieId()));
        }

        repository.save(new Vote(request.movieId(), request.value(), request.email()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
