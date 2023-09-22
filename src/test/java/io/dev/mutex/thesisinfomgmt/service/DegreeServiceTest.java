package io.dev.mutex.thesisinfomgmt.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.dev.mutex.thesisinfomgmt.common.PaginatedData;
import io.dev.mutex.thesisinfomgmt.dto.DegreeDTO;
import io.dev.mutex.thesisinfomgmt.exception.ThesisInfoServiceException;
import io.dev.mutex.thesisinfomgmt.model.Degree;
import io.dev.mutex.thesisinfomgmt.repository.DegreeRepository;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class DegreeServiceTest {

  @Mock
  private DegreeRepository degreeRepository;

  @Mock
  private Degree mockedDegree1;
  @Mock
  private Degree mockedDegree2;
  @Mock
  private Page<Degree> mockedPageOfDegree;

  private DegreeService degreeService;

  @BeforeEach
  void setup() {
    degreeService = new DegreeServiceImpl(degreeRepository);
  }

  @Nested
  @DisplayName("Get degrees tests")
  class GetDegreesTests {

    @Test
    @DisplayName("Should retrieve degrees without query")
    void shouldRetrieveDegreesWithoutQuery() {
      when(degreeRepository.findAll(any(PageRequest.class)))
          .thenReturn(mockedPageOfDegree);
      when(mockedPageOfDegree.get())
          .thenReturn(Stream.of(mockedDegree1, mockedDegree2));
      when(mockedPageOfDegree.getTotalElements()).thenReturn(2L);
      when(mockedPageOfDegree.hasNext()).thenReturn(false);

      assertDoesNotThrow(() -> {
        PaginatedData<DegreeDTO> actualResult =
            degreeService.getDegrees(0, 10, null);
        assertEquals(0, actualResult.getPage());
        assertEquals(10, actualResult.getPageSize());
        assertEquals(2, actualResult.getTotal());
        assertTrue(actualResult.isLastPage());
        assertEquals(2, actualResult.getData().size());
      });
    }

    @Test
    @DisplayName("Should retrieve degrees with query")
    void shouldRetrieveDegreesWithQuery() {
      when(degreeRepository
          .findAllByCodeContainsIgnoreCaseOrNameContainsIgnoreCase(
              anyString(), anyString(), any(PageRequest.class)))
          .thenReturn(mockedPageOfDegree);
      when(mockedPageOfDegree.get())
          .thenReturn(Stream.of(mockedDegree1, mockedDegree2));
      when(mockedPageOfDegree.getTotalElements()).thenReturn(2L);
      when(mockedPageOfDegree.hasNext()).thenReturn(false);

      assertDoesNotThrow(() -> {
        PaginatedData<DegreeDTO> actualResult =
            degreeService.getDegrees(0, 10, "test");
        assertEquals(0, actualResult.getPage());
        assertEquals(10, actualResult.getPageSize());
        assertEquals(2, actualResult.getTotal());
        assertTrue(actualResult.isLastPage());
        assertEquals(2, actualResult.getData().size());
      });
    }
  }

  @Nested
  @DisplayName("Create degree tests")
  class CreateDegreeTests {

    @ParameterizedTest
    @DisplayName("Should fail when invalid details")
    @CsvSource({
        "Degree code is required.,,Bachelor of Science in Computer Science",
        "Degree name is required.,BSCS,"
    })
    void shouldFailWhenCodeOrNameIsMissing(String errorMessage, String code, String name) {
      DegreeDTO inputDegree = new DegreeDTO()
          .withCode(code)
          .withName(name);
      ThesisInfoServiceException exception = assertThrows(ThesisInfoServiceException.class,
          () -> degreeService.createDegree(inputDegree));
      assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Should fail when code is duplicate")
    void shouldFailWhenCodeIsDuplicate() {
      when(degreeRepository.findByCodeIgnoreCase(anyString()))
          .thenReturn(Optional.of(mockedDegree1));
      when(mockedDegree1.getId()).thenReturn(11L);

      DegreeDTO inputDegree = new DegreeDTO()
          .withCode("BSCS")
          .withName("Computer Science");
      ThesisInfoServiceException exception = assertThrows(ThesisInfoServiceException.class,
          () -> degreeService.createDegree(inputDegree));
      assertEquals("Degree with code: BSCS already exists.", exception.getMessage());
    }

    @Test
    @DisplayName("Should fail when name is duplicate")
    void shouldFailWhenNameIsDuplicate() {
      when(degreeRepository.findByNameIgnoreCase(anyString()))
          .thenReturn(Optional.of(mockedDegree1));
      when(mockedDegree1.getId()).thenReturn(11L);

      DegreeDTO inputDegree = new DegreeDTO()
          .withCode("BSCS")
          .withName("Computer Science");
      ThesisInfoServiceException exception = assertThrows(ThesisInfoServiceException.class,
          () -> degreeService.createDegree(inputDegree));
      assertEquals("Degree with name: Computer Science already exists.",
          exception.getMessage());
    }

    @Test
    @DisplayName("Should create degree successfully")
    void shouldCreateDegreeSuccessfully() {
      DegreeDTO inputDegree = new DegreeDTO()
          .withCode("BSCS")
          .withName("Computer Science");
      when(degreeRepository.save(any(Degree.class)))
          .thenReturn(mockedDegree1);

      assertDoesNotThrow(() -> degreeService.createDegree(inputDegree));
    }
  }

  @Nested
  @DisplayName("Get degree tests")
  class GetDegreeTests {

    @Test
    @DisplayName("Should fail when degree does not exists")
    void shouldFailWhenDegreeDoesNotExists() {
      when(degreeRepository.findById(11L)).thenReturn(Optional.empty());

      ThesisInfoServiceException exception = assertThrows(ThesisInfoServiceException.class,
          () -> degreeService.getDegree(11));
      assertEquals("Degree with id (11) not found.", exception.getMessage());
    }

    @Test
    @DisplayName("Should retrieve degree successfully")
    void shouldRetrieveDegreeSuccessfully() {
      when(degreeRepository.findById(11L)).thenReturn(Optional.of(mockedDegree1));

      assertDoesNotThrow(() -> degreeService.getDegree(11));
    }
  }

  @Test
  @DisplayName("Should delete degree successfully")
  void shouldDeleteDegreeSuccessfully() {
    doNothing().when(degreeRepository).deleteById(11L);

    assertDoesNotThrow(() -> degreeService.deleteDegree(11));
  }

  @Nested
  @DisplayName("Update degree tests")
  class UpdateDegreeTests {

    @Test
    @DisplayName("Should fail when degree does not exists")
    void shouldFailWhenDegreeDoesNotExists() {
      when(degreeRepository.findById(11L)).thenReturn(Optional.empty());

      DegreeDTO inputDegree = new DegreeDTO();
      ThesisInfoServiceException exception = assertThrows(ThesisInfoServiceException.class,
          () -> degreeService.updateDegree(11, inputDegree));
      assertEquals("Degree with id (11) not found.", exception.getMessage());
    }

    @Test
    @DisplayName("Should successfully update degree")
    void shouldSuccessfullyUpdateDegree() {
      when(degreeRepository.findById(11L)).thenReturn(Optional.of(mockedDegree1));
      when(degreeRepository.save(mockedDegree1)).thenReturn(mockedDegree1);

      DegreeDTO inputDegree = new DegreeDTO()
          .withCode("BSCS")
          .withName("Computer Science");
      assertDoesNotThrow(() -> {
        degreeService.updateDegree(11, inputDegree);
        verify(degreeRepository).save(mockedDegree1);
      });
    }
  }
}
