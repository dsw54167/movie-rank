package dsw.projects.demo;

import dsw.projects.demo.movies.MovieController;
import dsw.projects.demo.movies.MovieNotFoundException;
import dsw.projects.demo.voting.VoteAlreadyExists;
import dsw.projects.demo.voting.VoteForNonExistingMovieException;
import dsw.projects.demo.voting.VotesController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@Slf4j
@RestControllerAdvice(assignableTypes = {VotesController.class, MovieController.class})
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<Void> handleMovieNotFound(MovieNotFoundException exception) {
        log.warn(exception.getMessage());
        return ResponseEntity
                .notFound()
                .build();
    }

    @ExceptionHandler(VoteForNonExistingMovieException.class)
    public ResponseEntity<ErrorResponse> handleMovieNotFound(VoteForNonExistingMovieException exception) {
        log.warn(exception.getMessage());
        return ResponseEntity
                .unprocessableContent()
                .body(new ErrorResponse(exception.getMessage()));
    }

    @ExceptionHandler(VoteAlreadyExists.class)
    public ResponseEntity<ErrorResponse> handleVoteAlreadyExists(VoteAlreadyExists exception) {
        log.warn(exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorResponse(exception.getMessage()));
    }

}
