package io.dev.mutex.thesisinfomgmt.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DataHelperTest {

  @ParameterizedTest
  @DisplayName("Check null and empty strings")
  @ValueSource(strings = {"", " ", "\t", "\n"})
  @NullSource
  void testNullAndEmptyString(String value) {
    assertTrue(DataHelper.isNullOrEmpty(value));
    assertFalse(DataHelper.isNotNullNorEmpty(value));
  }

  @Test
  @DisplayName("Check non null nor empty string")
  void testNonNullNorEmptyString() {
    String value = "TEST";
    assertFalse(DataHelper.isNullOrEmpty(value));
    assertTrue(DataHelper.isNotNullNorEmpty(value));
  }

  @Test
  @DisplayName("Format name with middle initial")
  void testFormatNameWithMI() {
    assertEquals(
        "Doe, John K.",
        DataHelper.formatName("Doe", "John", "Kennedy"));
  }

  @Test
  @DisplayName("Format name without middle initial")
  void testFormatNameWithoutMI() {
    assertEquals(
        "Doe, John",
        DataHelper.formatName("Doe", "John", null));
  }
}
