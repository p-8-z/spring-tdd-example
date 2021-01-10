package sk.p8z.tddtoptobottom;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import sk.p8z.tddtoptobottom.domain.Car;

@Service
public class CarService {

  private final CarRepository carRepository;

  public CarService(CarRepository carRepository) {
    this.carRepository = carRepository;
  }

  @Cacheable("cars")
  public Car getCarDetails(String name) {
    Car car = carRepository.findCarByName(name);
    if (car == null) {
      throw new CarNotFoundException();
    }
    return car;
  }
}
