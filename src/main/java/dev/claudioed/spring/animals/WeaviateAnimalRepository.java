package dev.claudioed.spring.animals;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WeaviateAnimalRepository implements AnimalRepository{
  @Override
  public List<Animal> animals(String query) {
    return null;
  }

}
