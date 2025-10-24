package com.example.evote;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.example.evote.service.UserService;
@SpringBootApplication
public class EvoteApplication {
  public static void main(String[] args) { SpringApplication.run(EvoteApplication.class, args); }
  @Bean CommandLineRunner init(UserService users){
    return args -> { users.ensureAdmin("admin","admin"); users.ensureVoter("voter","voter"); };
  }
}
