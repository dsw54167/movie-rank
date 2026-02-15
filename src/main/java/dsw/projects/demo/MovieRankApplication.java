package dsw.projects.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MovieRankApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieRankApplication.class, args);
	}

}
