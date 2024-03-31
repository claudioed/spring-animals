package dev.claudioed.spring.animals;

import io.weaviate.client.WeaviateClient;
import io.weaviate.client.base.Result;
import io.weaviate.client.v1.data.model.WeaviateObject;
import io.weaviate.client.v1.graphql.model.GraphQLResponse;
import io.weaviate.client.v1.graphql.query.fields.Field;
import io.weaviate.client.v1.graphql.query.fields.Field.FieldBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class WeaviateAnimalRepository implements AnimalRepository{
  private final static String className = "Animals";
  private final WeaviateClient weaviateClient;
  public WeaviateAnimalRepository(WeaviateClient weaviateClient) {
    this.weaviateClient = weaviateClient;
  }

  @Override
  public List<Animal> animals(String query) {
    Result<GraphQLResponse> qlResponseResult = this.weaviateClient.graphQL().get()
        .withClassName(className).withFields(Field.builder().name("description").build(),Field.builder().name("name").build()).run();
    if (qlResponseResult.getResult().getData() != null) {
      Map<String, Object> data = (Map<String, Object>) qlResponseResult.getResult().getData();
      Map<String,Object> maps = (Map<String, Object>) data.get("Get");
      List<Map<String,String>>  result = (List<Map<String, String>>) maps.get("Animals");
      return result.stream().map(map -> new Animal(map.get("name"), map.get("description"))).collect(
          Collectors.toList());
    }
    return null;
  }
  @Override
  public void store(Animal animal) {
    Result<WeaviateObject> result = this.weaviateClient.data().creator()
        .withClassName(className)
        .withProperties(new HashMap<>() {{
          put("name", animal.name());
          put("description",
              animal.description()); // will be automatically added as a number property
        }}).withID(UUID.randomUUID().toString())
        .run();
    System.out.println(STR."Stored animal with id: \{result.getResult().getId()}");
  }

  @Override
  public Animal get(String id) {
    Result<List<WeaviateObject>> result = this.weaviateClient.data().objectsGetter()
        .withClassName(className)
        .withID(id)
        .run();
    var data = result.getResult().get(0);
    return new Animal(data.getProperties().get("name").toString(),
        data.getProperties().get("description").toString());
  }
}
