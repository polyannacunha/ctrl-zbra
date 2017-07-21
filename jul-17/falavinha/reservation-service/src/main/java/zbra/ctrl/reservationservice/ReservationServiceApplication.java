package zbra.ctrl.reservationservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.stream.Stream;

@EnableBinding(ReservationChannels.class)
@EnableEurekaClient
@SpringBootApplication
public class ReservationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservationServiceApplication.class, args);
	}
}

interface ReservationChannels {

    @Input
    SubscribableChannel input();

}

@MessageEndpoint
class ReservationProcessor {

    private final ReservationRepository reservationRepository;

    @Autowired
    ReservationProcessor(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @ServiceActivator(inputChannel = "input")
    public void onNewReservation(String reservationName) {
        reservationRepository.save(new Reservation(reservationName));
    }
}

@RestController
@RefreshScope
class MessageRestController {

    private final String value;

    @Autowired
    MessageRestController(@Value("${message}") String value) {
        this.value = value;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/message")
    public String read() {
        return this.value;
    }
}

@Component
class SampleDataCLR implements CommandLineRunner {

    private final ReservationRepository reservationRepository;

    @Autowired
    SampleDataCLR(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        Stream
            .of("Sérgio", "Sbragio", "Milton", "Lombardi", "Alexandre", "Dr. Who")
            .forEach(name -> reservationRepository.save(new Reservation(name)));

        reservationRepository.findAll().forEach(System.out::println);
    }
}

@RepositoryRestResource
interface ReservationRepository extends JpaRepository<Reservation, Long> {
}

@Entity
class Reservation {

    @Id
    @GeneratedValue
    private Long id;

    private String reservationName;

    Reservation() { // why JPA WHY?????
    }

    public Reservation(String reservationName) {
        this.reservationName = reservationName;
    }

    public Long getId() {
        return id;
    }

    public String getReservationName() {
        return reservationName;
    }

    @Override
    public String toString() {
        return reservationName;
    }
}
