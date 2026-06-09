package dsw.projects.demo.movies;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record MovieCreatedEvent(Long id){

    @JsonCreator
    public MovieCreatedEvent(Long id) {
        this.id = id;
    }
}
