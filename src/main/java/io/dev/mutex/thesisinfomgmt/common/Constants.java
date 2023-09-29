package io.dev.mutex.thesisinfomgmt.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

  // Defined characters
  public static final String EMPTY_STRING = "";

  // Formatters
  public static final String BASIC_NAME_FORMAT = "%s, %s %s.";
  public static final String BASIC_NAME_FORMAT_NO_MI = "%s, %s";

  // Entity names
  public static final String ENTITY_AUTHOR = "Author";
  public static final String ENTITY_DEGREE = "Degree";
  public static final String ENTITY_ROLE = "Role";

  // Attributes
  public static final String ATTRIBUTE_ID = "id";
}
