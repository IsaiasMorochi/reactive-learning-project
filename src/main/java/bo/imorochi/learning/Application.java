package bo.imorochi.learning;

import bo.imorochi.learning.reactor.Example6;
import bo.imorochi.learning.reactor.ReactorExample;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) {

		ReactorExample example = new Example6();
		example.run();

	}

}
