package dsw.projects.demo.movies;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MovieCreatedListener {

    private final MovieReadModelRepository movieReadModelRepository;
    private final MovieRatingRepository movieRatingRepository;

    @RabbitListener(queues = "movie-created-queue")
    @Transactional
    public void receiveMessage(MovieCreatedEvent event) {
        log.info("Read model for movie with id {} saved", event.id());
        movieReadModelRepository.save(new MovieReadModel(event.id()));
        movieRatingRepository.save(new MovieRating(event.id()));
    }


}
