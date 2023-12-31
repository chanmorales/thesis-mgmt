package io.dev.mutex.thesisinfomgmt.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

@Entity
@Getter
@Setter
public class Degree {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NotNull
  private String code;

  @NotNull
  private String name;

  @Formula("(SELECT COUNT(*) FROM thesis t WHERE t.degree_id = id)")
  private int thesisCount;
}
