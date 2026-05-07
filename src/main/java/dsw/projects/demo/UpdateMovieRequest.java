package dsw.projects.demo;

import jakarta.validation.constraints.NotBlank;

public record UpdateMovieRequest(@NotBlank String title) {
}
