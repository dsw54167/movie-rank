package dsw.projects.demo.voting;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;

public record VoteRequest(
        @NotBlank String email,
        @NotNull Long movieId,
        @NotNull
        @Range(min = 1, max = 10) Integer value) {
}
