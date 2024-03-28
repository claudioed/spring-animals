package dev.claudioed.spring.animals;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AnimalService {
  private final AnimalRepository animalRepository;
  public AnimalService(AnimalRepository animalRepository) {
    this.animalRepository = animalRepository;
  }
  public List<Animal> animals(String query) {
    return animalRepository.animals(query);
  }

}
