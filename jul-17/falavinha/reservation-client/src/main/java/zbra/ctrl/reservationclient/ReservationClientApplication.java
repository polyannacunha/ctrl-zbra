package zbra.ctrl.reservationclient;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.hateoas.Resources;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.MessageChannel;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

//@EnableResourceServer
@IntegrationComponentScan
@EnableBinding(ReservationChannels.class)
@EnableCircuitBreaker
@EnableFeignClients
@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
public class ReservationClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReservationClientApplication.class, args);
	}
}

interface ReservationChannels {

    @Output
    MessageChannel output(); // create multiple channels
}

@FeignClient("reservation-service")
interface ReservationReader {

    @RequestMapping(method = RequestMethod.GET, value = "/reservations")
    Resources<Reservation> read();

}

class Reservation {
    private String reservationName;

    public String getReservationName() {
        return reservationName;
    }

    public void setReservationName(String reservationName) {
        this.reservationName = reservationName;
    }
}

@MessagingGateway
interface ReservationWriter {

    @Gateway(requestChannel = "output")
    void write(String rn);

}

@RestController
@RequestMapping("/reservations")
class ReservationApiGateway {

    private final ReservationReader reader;
    private final ReservationWriter writer;

    @Autowired
    ReservationApiGateway(ReservationReader reader, ReservationWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public Collection<String> fallback() {
        return new ArrayList<>();
    }

    @HystrixCommand(fallbackMethod = "fallback")
    @RequestMapping(method = RequestMethod.GET, value = "/names")
    public Collection<String> names() {
        return reader
            .read()
            .getContent()
            .stream()
            .map(Reservation::getReservationName)
            .collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST)
    public void write(@RequestBody Reservation reservation) {
        writer.write(reservation.getReservationName());
    }
}
