package pl.debuguj.system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;


@SpringBootApplication
public class ParkingSpacesSystemApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ParkingSpacesSystemApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ParkingSpacesSystemApplication.class, args);
    }

    @Override
    public void run(String... strings) {

    }

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
}
