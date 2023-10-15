package io.dev.mutex.thesisinfomgmt.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@XmlRootElement
@AllArgsConstructor
public class DateSubmittedDTO {

  @XmlElement
  private int year;

  @XmlElement
  private int month;
}
