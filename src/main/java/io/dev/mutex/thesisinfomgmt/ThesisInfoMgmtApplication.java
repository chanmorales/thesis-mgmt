package io.dev.mutex.thesisinfomgmt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ThesisInfoMgmtApplication {

  public static void main(String[] args) {
    SpringApplication.run(ThesisInfoMgmtApplication.class, args);
  }
}
