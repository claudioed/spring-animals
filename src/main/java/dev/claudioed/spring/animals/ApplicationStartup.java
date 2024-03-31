package dev.claudioed.spring.animals;

import java.util.List;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup {
  private final AnimalRepository animalRepository;

  // Give the list of animals to the zoo following the instructions name and description (copilot hint)
  public List<Animal> zoo = List.of(new Animal("Lion", "The king of the jungle"),
      new Animal("Elephant", "The largest land animal on Earth"),
      new Animal("Giraffe", "The tallest land animal on Earth"),
      new Animal("Zebra", "Known for their black and white stripes"),
      new Animal("Hippo", "The third largest land animal on Earth"),
      new Animal("Rhino", "The second largest land animal on Earth"),
      new Animal("Gorilla", "The largest primate on Earth"),
      new Animal("Penguin", "Flightless birds that live in the Southern Hemisphere"),
      new Animal("Kangaroo", "Marsupials that carry their young in a pouch"),
      new Animal("Koala", "Marsupials that eat eucalyptus leaves"));

  public ApplicationStartup(AnimalRepository animalRepository) {
    this.animalRepository = animalRepository;
  }
  @EventListener(ApplicationReadyEvent.class)
  public void animalsInitializer() {
    zoo.forEach(animalRepository::store);
  }

}
