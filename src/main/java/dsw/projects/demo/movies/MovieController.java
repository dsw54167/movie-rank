package dsw.projects.demo.movies;

import dsw.projects.demo.CreateMovieRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(path = "/movies")
@RequiredArgsConstructor
@Tag(name = "Movies", description = "Movie management API for retrieving and creating movies with ratings")
public class MovieController {

    private final MovieRepository repository;

    @Operation(
            summary = "Get movie by ID",
            description = "Retrieves a single movie by its unique identifier"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Movie found and returned successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = MovieDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Movie not found with the provided ID",
                    content = @Content
            )
    })
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MovieDto> getById(
            @Parameter(description = "Unique identifier of the movie", required = true, example = "1")
            @PathVariable Long id) throws MovieNotFoundException {
        MovieDto movieDto = repository.findById(id)
                .map(movie -> new MovieDto(movie.getId(), movie.getTitle(), movie.getRating()))
                .orElseThrow(() -> new MovieNotFoundException(id));
        return ResponseEntity.ok(movieDto);
    }

    @Operation(
            summary = "Get all movies",
            description = "Retrieves a paginated list of all movies sorted by rating in descending order by default"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Movies retrieved successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Page.class))
            )
    })
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Page<MovieDto>> getAll(
            @Parameter(description = "Pagination and sorting parameters (page, size, sort). Default sort: rating,desc")
            @SortDefault(sort = "rating", direction = Sort.Direction.DESC) @ParameterObject Pageable pageable) {
        Page<Movie> all = repository.findAll(pageable);
        Page<MovieDto> dtoPage = all.map(movie -> new MovieDto(movie.getId(), movie.getTitle(), movie.getRating()));
        return ResponseEntity.ok(dtoPage);
    }

    @Operation(
            summary = "Create a new movie",
            description = "Creates a new movie with the provided title and returns the created movie with generated ID and default rating"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Movie created successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = MovieDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request body or missing required fields",
                    content = @Content
            )
    })
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<MovieDto> create(
            UriComponentsBuilder uriBuilder,
            @Parameter(description = "Movie creation request containing the movie title", required = true)
            @RequestBody CreateMovieRequest request) {
        var movie = repository.save(new Movie(request.title()));
        var dto = new MovieDto(movie.getId(), movie.getTitle(), movie.getRating());
        return ResponseEntity.created(
                        uriBuilder.path("/movies/{id}")
                                .buildAndExpand(movie.getId())
                                .toUri())
                .body(dto);
    }
}
