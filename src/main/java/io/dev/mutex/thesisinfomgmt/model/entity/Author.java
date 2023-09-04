package io.dev.mutex.thesisinfomgmt.model.entity;

import io.dev.mutex.thesisinfomgmt.util.DataHelper;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Author extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @NotNull
  private String lastName;

  @NotNull
  private String firstName;

  private String middleName;

  @Override
  public String toString() {
    return DataHelper.formatName(lastName, firstName, middleName);
  }
}
