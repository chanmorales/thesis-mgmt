package io.dev.mutex.thesisinfomgmt.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;

@XmlRootElement
@Data
@JsonInclude(Include.NON_NULL)
public class ExceptionDTO {

  @XmlElement
  private String message;

  @XmlElement
  private String field;
}
