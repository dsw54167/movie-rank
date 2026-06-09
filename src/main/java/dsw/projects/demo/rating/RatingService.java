package dsw.projects.demo.rating;

import dsw.projects.demo.movies.MovieRating;
import dsw.projects.demo.movies.MovieRatingRepository;
import dsw.projects.demo.movies.MovieReadModelRepository;
import dsw.projects.demo.voting.Vote;
import dsw.projects.demo.voting.VoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.OptionalDouble;

@Service
@Slf4j
@RequiredArgsConstructor
public class RatingService {

    private final MovieReadModelRepository ratingRepository;
    private final VoteRepository voteRepository;
    private final RabbitTemplate rabbitTemplate;
    private final MovieRatingRepository movieRatingRepository;

    @Scheduled(fixedRate = 30000)
    @Transactional
    void updateRatings() {
        log.info("Updating ratings has been started");
        ratingRepository.findAll().forEach(movie -> {
            var votes = voteRepository.findByMovieId(movie.getId());
            OptionalDouble average = votes.stream().mapToInt(Vote::getValue).average();
            movieRatingRepository.findByMovieId(movie.getId()).ifPresent(movieRating -> {
                 if (average.isPresent() && !Objects.equals(movieRating.getRating(),  Double.parseDouble(String.format("%.2f", average.getAsDouble())))) {
                    updateRating(movieRating, average.getAsDouble());
                }
            });
        });

        log.info("Updating ratings has been finished");
    }

    private void updateRating(MovieRating movieRating, double newRating) {
        movieRating.updateRating(newRating);
        rabbitTemplate.convertAndSend("movie-rating", "movie-rating-queue-rk", new MovieRatingChanged(movieRating.getMovieId(), movieRating.getRating()));
    }
}
