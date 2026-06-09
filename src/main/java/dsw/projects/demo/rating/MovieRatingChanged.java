package dsw.projects.demo.rating;

import java.io.Serializable;

public record MovieRatingChanged(Long movieId, Double rating) implements Serializable {
}
