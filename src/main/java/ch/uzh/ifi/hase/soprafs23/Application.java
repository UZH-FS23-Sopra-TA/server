package ch.uzh.ifi.hase.soprafs23;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.google.cloud.secretmanager.v1.Secret;
import com.google.cloud.secretmanager.v1.SecretManagerServiceClient;
import com.google.cloud.secretmanager.v1.SecretName;
import java.io.IOException;


@RestController
@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @GetMapping(value = "/", produces = MediaType.TEXT_PLAIN_VALUE)
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public String helloWorld() throws IOException{
      String projectId = "sopra-fs23-ta-m1-server"; // projects/393893293106/secrets/TestAPIKey
      String secretId = "projects/393893293106/secrets/TestAPIKey\n";
      String secret = getSecret(projectId, secretId);

      return "The application is running.\nAPI KEY : " + secret;
  }

  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("*");
      }
    };
  }

    // Get an existing secret.
    public static String getSecret(String projectId, String secretId) throws IOException {
        // Initialize client that will be used to send requests. This client only needs to be created
        // once, and can be reused for multiple requests. After completing all of your requests, call
        // the "close" method on the client to safely clean up any remaining background resources.
        try (SecretManagerServiceClient client = SecretManagerServiceClient.create()) {
            // Build the name.
            SecretName secretName = SecretName.of(projectId, secretId);

            // Create the secret.
            Secret secret = client.getSecret(secretName);

            // Get the replication policy.
            String replication = "";
            if (secret.getReplication().getAutomatic() != null) {
                replication = "AUTOMATIC";
            } else if (secret.getReplication().getUserManaged() != null) {
                replication = "MANAGED";
            } else {
                throw new IllegalStateException("Unknown replication type");
            }

            System.out.printf("Secret %s, replication %s\n", secret.getName(), replication);
            return secret.getName();
        }
    }

}
