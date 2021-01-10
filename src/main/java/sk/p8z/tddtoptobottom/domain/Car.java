package sk.p8z.tddtoptobottom.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Car implements Serializable {

  @Id
  @GeneratedValue
  Long id;

  String name;
  String type;

  public Car(String name, String type) {
    this.name = name;
    this.type = type;
  }
}
