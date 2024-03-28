package dev.claudioed.spring.animals;

import io.weaviate.client.Config;
import io.weaviate.client.WeaviateClient;
import io.weaviate.client.base.Result;
import io.weaviate.client.v1.schema.model.WeaviateClass;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WeaviateProducer {
  @Bean
  public WeaviateClient client(){
    Config config = new Config("http", "localhost:8080");
    return new WeaviateClient(config);
  }
  @Bean
  public Result<Boolean> createSchema(WeaviateClient client) {
    String className = "Animals";
    WeaviateClass emptyClass = WeaviateClass.builder()
        .className(className)
        .build();
    Result<Boolean> result = client.schema().classCreator()
        .withClass(emptyClass)
        .run();
    return result;
  }

}
