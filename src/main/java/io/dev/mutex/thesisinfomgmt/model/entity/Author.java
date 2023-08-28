package io.dev.mutex.thesisinfomgmt.model.entity;

import io.dev.mutex.thesisinfomgmt.util.DataHelper;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Getter
@Setter
public class Author {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @NotNull
  private String lastName;

  @NotNull
  private String firstName;

  private String middleName;

  @CreatedDate
  private Date created;

  @LastModifiedDate
  private Date updated;

  @Override
  public String toString() {
    return DataHelper.formatName(lastName, firstName, middleName);
  }
}
