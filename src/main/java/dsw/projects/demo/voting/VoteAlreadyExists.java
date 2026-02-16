package dsw.projects.demo.voting;

public class VoteAlreadyExists extends Exception {
    public VoteAlreadyExists(String message) {
        super(message);
    }
}
