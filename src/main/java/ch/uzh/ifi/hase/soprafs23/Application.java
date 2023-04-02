package ch.uzh.ifi.hase.soprafs23;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RestController
@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @GetMapping(value = "/", produces = MediaType.TEXT_PLAIN_VALUE)
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  public String helloWorld(){
      String secret="initialize";
      secret = System.getenv("MY_DOTENV");
//      try {
//          secret = System.getenv("TestAPIKey");
//          System.out.println(secret);
//      }
//      catch(Exception e) {
//          secret = "NO_ENV_VARIABLE";
//      }
//      try {
//          File myObj = new File("~/key.txt");
//          Scanner myReader = new Scanner(myObj);
//          String data = myReader.nextLine();
//          secret = data;
//          myReader.close();
//      } catch (FileNotFoundException e) {
//          secret = "An error occurred.";
//          e.printStackTrace();
//      }
//      System.out.println(secret);
//      GetSecret test = new GetSecret();
//      try {
//          test.getSecret();
//          secret="getSecret() success";
//      }
//      catch (Exception e) {
//          secret="getSecret() error";
//          e.printStackTrace();
//      }
      System.out.println("CHECKPOINT" + secret);
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

}
