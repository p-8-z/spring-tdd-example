package sk.p8z.tddtoptobottom;

import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.times;
import static org.mockito.BDDMockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import sk.p8z.tddtoptobottom.domain.Car;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@AutoConfigureTestDatabase
public class CachingTest {

  @Autowired
  private CarService service;

  @MockBean
  private CarRepository carRepository;

  @Test
  public void caching() {
    given(carRepository.findCarByName(anyString())).willReturn(new Car("prius", "hybrid"));

    service.getCarDetails("prius");
    service.getCarDetails("prius");

    verify(carRepository, times(1)).findCarByName("prius");
  }
}
