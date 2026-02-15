package dsw.projects.demo;

import jakarta.validation.constraints.NotBlank;

public record CreateMovieRequest(@NotBlank String title) {
}
