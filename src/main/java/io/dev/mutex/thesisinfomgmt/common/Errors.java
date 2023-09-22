package io.dev.mutex.thesisinfomgmt.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Errors {

  // Common Errors
  public static final String ENTITY_NOT_FOUND = "%s with %s (%s) not found.";
  public static final String PROPERTY_REQUIRED = "%s is required.";
  public static final String PROPERTY_SHOULD_BE_UNIQUE = "%s with %s: %s already exists.";
}
