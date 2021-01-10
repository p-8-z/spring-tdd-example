package sk.p8z.tddtoptobottom;

import org.springframework.data.repository.CrudRepository;
import sk.p8z.tddtoptobottom.domain.Car;

public interface CarRepository extends CrudRepository<Car, Long> {

  Car findCarByName(String name);
}
