package dsw.projects.demo.rating;

import dsw.projects.demo.movies.MovieRepository;
import dsw.projects.demo.voting.Vote;
import dsw.projects.demo.voting.VoteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RatingService {

    private final MovieRepository ratingRepository;
    private final VoteRepository voteRepository;

    @Scheduled(fixedRate = 30000)
    @Transactional
    void updateRatings() {
        log.info("Updating ratings has been started");
        ratingRepository.findAll().forEach(movie -> {
            var votes = voteRepository.findByMovieId(movie.getId());
            var rating = votes.stream().mapToInt(Vote::getValue).average();
            rating.ifPresent(movie::updateRating);
        });
        log.info("Updating ratings has been finished");
    }
}
