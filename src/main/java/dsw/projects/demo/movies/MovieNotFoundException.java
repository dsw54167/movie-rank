package dsw.projects.demo.movies;


public class MovieNotFoundException extends Exception {

    private Long id;

    public MovieNotFoundException(Long id) {
        super("Movie " + id + " not found");
        this.id = id;
    }
}
