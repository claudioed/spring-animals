package dev.claudioed.spring.animals;


import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/animals")
public class AnimalsController {
  private final AnimalService animalService;
  public AnimalsController(AnimalService animalService) {
    this.animalService = animalService;
  }
  @GetMapping
  public List<Animal> animals(@RequestParam("query") String query) {
    return this.animalService.animals(query);
  }
  @GetMapping ("/{id}")
  public Animal get(@PathVariable("id") String id) {
    return this.animalService.get(id);
  }

}
