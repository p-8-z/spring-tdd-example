package sk.p8z.tddtoptobottom;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import sk.p8z.tddtoptobottom.domain.Car;

@RestController
public class CarController {

  private final CarService carService;

  public CarController(CarService carService) {
    this.carService = carService;
  }

  @GetMapping("/cars/{name}")
  private Car getCar(@PathVariable String name) {
    return carService.getCarDetails(name);
  }

}
