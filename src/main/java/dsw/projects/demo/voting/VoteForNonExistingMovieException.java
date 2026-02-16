package dsw.projects.demo.voting;

public class VoteForNonExistingMovieException extends Exception {
    public VoteForNonExistingMovieException(String message) {
        super(message);
    }
}
