package dev.claudioed.spring.animals;

import io.weaviate.client.WeaviateClient;
import io.weaviate.client.base.Result;
import io.weaviate.client.v1.data.model.WeaviateObject;
import io.weaviate.client.v1.graphql.model.GraphQLResponse;
import io.weaviate.client.v1.graphql.query.fields.Field;
import io.weaviate.client.v1.graphql.query.fields.Field.FieldBuilder;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
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
    var questions = Field.builder().name("_additional")
        .fields(Field.builder().name("description").build())
        .fields(Field.builder().name("name").build())
        .build();
    Result<GraphQLResponse> qlResponseResult = this.weaviateClient.graphQL().get()
        .withFields(questions).run();
    if (qlResponseResult.getResult().getData() != null) {
      System.out.println(qlResponseResult.getResult().getData());
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
