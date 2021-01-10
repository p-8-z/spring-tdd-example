package sk.p8z.tddtoptobottom;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import sk.p8z.tddtoptobottom.domain.Car;

/**
 * 4.<br/> Test repository
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
class CarRepositoryTest {

  @Autowired
  private CarRepository repository;

  @Autowired
  private TestEntityManager entityManager;

  @Test
  public void findCarByName_returnsCar() {
    Car savedCar = entityManager.persistFlushFind(new Car("prius", "hybrid"));
    Car car = repository.findCarByName("prius");

    assertThat(car.getName()).isEqualTo(savedCar.getName());
    assertThat(car.getType()).isEqualTo(savedCar.getType());

  }

}
