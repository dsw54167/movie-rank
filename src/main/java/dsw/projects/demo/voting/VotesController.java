package dsw.projects.demo.voting;

import dsw.projects.demo.movies.MovieReadModelRepository;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/votes")
@RequiredArgsConstructor
@Transactional
@CrossOrigin(origins = "http://localhost:5173")
public class VotesController {

    private final VoteRepository voteRepository;
    private final MovieReadModelRepository movieReadModelRepository;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @RateLimiter(name = "rateMovie")
    ResponseEntity<Void> rateMovie(
            @RequestBody VoteRequest request) throws VoteAlreadyExists, VoteForNonExistingMovieException {

        if (!movieReadModelRepository.existsById(request.movieId())) {
            throw new VoteForNonExistingMovieException("Movie %s not exists".formatted(request.movieId()));
        }

        if (voteRepository.existsByMovieIdAndEmail(request.movieId(), request.email())) {
            throw new VoteAlreadyExists("Email %s already voted for movie %s".formatted(request.email(), request.movieId()));
        }

        voteRepository.save(new Vote(request.movieId(), request.value(), request.email()));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
