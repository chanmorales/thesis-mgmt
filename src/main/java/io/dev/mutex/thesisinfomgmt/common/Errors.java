package io.dev.mutex.thesisinfomgmt.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Errors {

  // Common Errors
  public static final String ENTITY_NOT_FOUND = "%s not found. Please check and ensure that the"
      + " resource you are trying to access exists.";
  public static final String PROPERTY_REQUIRED = "%s is required.";
  public static final String PROPERTY_SHOULD_BE_UNIQUE = "%s already exists.";
}
