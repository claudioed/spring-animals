package dev.claudioed.spring.animals;

import java.util.List;

public interface AnimalRepository {
  List<Animal> animals(String query);
  void store(Animal animal);
  Animal get(String id);

}
