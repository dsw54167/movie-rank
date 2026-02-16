package dsw.projects.demo.voting;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VoteRequest(
        @NotBlank String email,
        @NotNull Long movieId,
        @NotNull Integer value) {
}
