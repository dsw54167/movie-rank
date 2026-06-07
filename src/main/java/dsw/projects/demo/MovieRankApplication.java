package dsw.projects.demo;

import dsw.projects.demo.movies.Movie;
import dsw.projects.demo.movies.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.security.autoconfigure.UserDetailsServiceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = UserDetailsServiceAutoConfiguration.class)
@EnableScheduling
@EnableCaching
public class MovieRankApplication {



    public static void main(String[] args) {
        SpringApplication.run(MovieRankApplication.class, args);
    }

    @Bean
    CommandLineRunner init(MovieRepository movieRepository) {
        return args -> {
            movieRepository.save(new Movie("Królestwo Planety Małp"));
            movieRepository.save(new Movie("Twisters"));
            movieRepository.save(new Movie("Beetlejuice Beetlejuice"));
            movieRepository.save(new Movie("Mission: Impossible – Ostateczne Rozliczenie"));
            movieRepository.save(new Movie("Diuna: Część druga"));
            movieRepository.save(new Movie("Outer Banks"));
            movieRepository.save(new Movie("W głowie się nie mieści 2"));
            movieRepository.save(new Movie("Nosferatu"));
            movieRepository.save(new Movie("Avatar 3"));
            movieRepository.save(new Movie("Gladiator 2"));
            movieRepository.save(new Movie("Kapitan Ameryka: Nowy wspaniały świat"));
            movieRepository.save(new Movie("Thunderbolts"));
            movieRepository.save(new Movie("Venom: Ostatni taniec"));
            movieRepository.save(new Movie("Obcy: Romulus"));
            movieRepository.save(new Movie("Mickey 17"));
            movieRepository.save(new Movie("Deadpool i Wolverine"));
            movieRepository.save(new Movie("Joker: Folie à Deux"));
            movieRepository.save(new Movie("Furiosa: Saga Mad Max"));
            movieRepository.save(new Movie("Minecraft: Film"));
            movieRepository.save(new Movie("Fantastyczna Czwórka"));
            movieRepository.save(new Movie("Substancja"));
        };
    }
}
