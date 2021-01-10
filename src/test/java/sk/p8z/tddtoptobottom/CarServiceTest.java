package sk.p8z.tddtoptobottom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.given;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sk.p8z.tddtoptobottom.domain.Car;


/**
 * 3.<br/> Lightweight unit-test - Don't involve spring in this test Test very basic interaction
 * with repository and the carController
 */
@ExtendWith(MockitoExtension.class)
class CarServiceTest {

  @Mock
  private CarRepository carRepository;

  private CarService carService;

  @BeforeEach
  public void setUp() {
    this.carService = new CarService(carRepository);
  }

  @Test
  public void getCarDetails_returnsCarInfo() {
    given(carRepository.findCarByName(anyString())).willReturn(new Car("prius", "hybrid"));

    Car car = carService.getCarDetails("prius");

    assertThat(car.getName()).isEqualTo("prius");
    assertThat(car.getType()).isEqualTo("hybrid");
  }

  @Test
  public void getCarDetails_whenCarNotFound() {
    given(carRepository.findCarByName(anyString())).willReturn(null);

    assertThrows(CarNotFoundException.class, () -> {
      carService.getCarDetails("prius");
    });
  }
}
