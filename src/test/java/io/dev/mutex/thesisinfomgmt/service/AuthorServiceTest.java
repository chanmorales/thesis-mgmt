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
import io.dev.mutex.thesisinfomgmt.exception.ThesisInfoServiceException;
import io.dev.mutex.thesisinfomgmt.model.dto.AuthorDTO;
import io.dev.mutex.thesisinfomgmt.model.entity.Author;
import io.dev.mutex.thesisinfomgmt.repository.AuthorRepository;
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
class AuthorServiceTest {

  @Mock
  private AuthorRepository authorRepository;

  @Mock
  private Author mockedAuthor1;
  @Mock
  private Author mockedAuthor2;
  @Mock
  private Page<Author> mockedPageOfAuthors;

  private AuthorService authorService;

  @BeforeEach
  void setup() {
    authorService = new AuthorServiceImpl(authorRepository);
  }

  @Nested
  @DisplayName("Get authors tests")
  class GetAuthorsTests {

    @Test
    @DisplayName("Should retrieve authors without query")
    void shouldRetrieveAuthorsWithoutQuery() {
      when(authorRepository.findAll(any(PageRequest.class)))
          .thenReturn(mockedPageOfAuthors);
      when(mockedPageOfAuthors.get())
          .thenReturn(Stream.of(mockedAuthor1, mockedAuthor2));
      when(mockedPageOfAuthors.getTotalElements()).thenReturn(2L);
      when(mockedPageOfAuthors.hasNext()).thenReturn(false);

      assertDoesNotThrow(() -> {
        PaginatedData<AuthorDTO> actualResult =
            authorService.getAuthors(0, 10, null);
        assertEquals(0, actualResult.getPage());
        assertEquals(10, actualResult.getPageSize());
        assertEquals(2, actualResult.getTotal());
        assertTrue(actualResult.isLastPage());
        assertEquals(2, actualResult.getData().size());
      });
    }

    @Test
    @DisplayName("Should retrieve authors with query")
    void shouldRetrieveAuthorsWithQuery() {
      when(authorRepository
          .findAllByLastNameContainsIgnoreCaseOrFirstNameContainsIgnoreCase(
              anyString(), anyString(), any(PageRequest.class)))
          .thenReturn(mockedPageOfAuthors);
      when(mockedPageOfAuthors.get())
          .thenReturn(Stream.of(mockedAuthor1, mockedAuthor2));
      when(mockedPageOfAuthors.getTotalElements()).thenReturn(2L);
      when(mockedPageOfAuthors.hasNext()).thenReturn(false);

      assertDoesNotThrow(() -> {
        PaginatedData<AuthorDTO> actualResult =
            authorService.getAuthors(0, 10, "test");
        assertEquals(0, actualResult.getPage());
        assertEquals(10, actualResult.getPageSize());
        assertEquals(2, actualResult.getTotal());
        assertTrue(actualResult.isLastPage());
        assertEquals(2, actualResult.getData().size());
      });
    }
  }

  @Nested
  @DisplayName("Create author tests")
  class CreateAuthorTests {

    @ParameterizedTest
    @DisplayName("Should fail when first name or last name is missing")
    @CsvSource({
        "First name is required.,,Doe",
        "Last name is required.,John,"
    })
    void shouldFailWhenLastNameIsMissing(
        String errorMessage, String firstName, String lastName
    ) {
      AuthorDTO inputAuthor = new AuthorDTO()
          .withFirstName(firstName)
          .withLastName(lastName);

      ThesisInfoServiceException exception = assertThrows(ThesisInfoServiceException.class,
          () -> authorService.createAuthor(inputAuthor));
      assertEquals(errorMessage, exception.getMessage());
    }

    @Test
    @DisplayName("Should successfully create author")
    void shouldSuccessfullyCreateAuthor() {
      AuthorDTO inputAuthor = new AuthorDTO()
          .withFirstName("John")
          .withLastName("Doe");
      when(authorRepository.save(any(Author.class)))
          .thenReturn(mockedAuthor1);

      assertDoesNotThrow(() -> authorService.createAuthor(inputAuthor));
    }
  }

  @Nested
  @DisplayName("Get author tests")
  class GetAuthorTests {

    @Test
    @DisplayName("Should fail when author does not exists")
    void shouldFailWhenAuthorDoesNotExists() {
      when(authorRepository.findById(11L)).thenReturn(Optional.empty());

      ThesisInfoServiceException exception = assertThrows(ThesisInfoServiceException.class,
          () -> authorService.getAuthor(11));
      assertEquals("Author with id (11) not found.", exception.getMessage());
    }

    @Test
    @DisplayName("Should retrieve author successfully")
    void shouldRetrieveAuthorSuccessfully() {
      when(authorRepository.findById(11L)).thenReturn(Optional.of(mockedAuthor1));

      assertDoesNotThrow(() -> authorService.getAuthor(11));
    }
  }

  @Test
  @DisplayName("Should delete author successfully whether it exists or not")
  void shouldDeleteAuthorSuccessfully() {
    doNothing().when(authorRepository).deleteById(11L);

    authorService.deleteAuthor(11);
    verify(authorRepository).deleteById(11L);
  }

  @Nested
  @DisplayName("Update author tests")
  class UpdateAuthorTests {

    @Test
    @DisplayName("Should fail when author does not exists")
    void shouldFailWhenAuthorDoesNotExists() {
      when(authorRepository.findById(11L)).thenReturn(Optional.empty());

      AuthorDTO inputAuthor = new AuthorDTO();
      ThesisInfoServiceException exception = assertThrows(ThesisInfoServiceException.class, () ->
          authorService.updateAuthor(11, inputAuthor));
      assertEquals("Author with id (11) not found.", exception.getMessage());
    }

    @Test
    @DisplayName("Should successfully update author")
    void shouldSuccessfullyUpdateAuthor() {
      when(authorRepository.findById(11L)).thenReturn(Optional.of(mockedAuthor1));
      when(authorRepository.save(mockedAuthor1)).thenReturn(mockedAuthor1);

      AuthorDTO inputAuthor = new AuthorDTO()
          .withLastName("Smith")
          .withFirstName("John");
      assertDoesNotThrow(() -> {
        authorService.updateAuthor(11, inputAuthor);
        verify(authorRepository).save(mockedAuthor1);
      });
    }
  }
}
