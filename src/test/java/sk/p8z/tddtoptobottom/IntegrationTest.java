package sk.p8z.tddtoptobottom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import sk.p8z.tddtoptobottom.domain.Car;

/**
 * 1. ---- High level integration Test Describes behavior of our endpoint Tests if everything in the
 * app works fine Should test every happy scenario ----
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class IntegrationTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private CarRepository carRepository;

  @BeforeEach
  public void setUp() {
    carRepository.save(new Car("prius", "hybrid"));
  }

  @Test
  public void getCar_returnsCarDetails() throws Exception {
    ResponseEntity<Car> response = restTemplate.getForEntity("/cars/prius", Car.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(response.getBody().getName()).isEqualTo("prius");
    assertThat(response.getBody().getType()).isEqualTo("hybrid");
  }
}
