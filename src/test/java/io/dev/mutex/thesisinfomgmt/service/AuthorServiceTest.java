package io.dev.mutex.thesisinfomgmt.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

  @Mock
  private AuthorRepository authorRepository;
  @Mock
  private Page<Author> authors;

  private AuthorService authorService;

  @BeforeEach
  void setup() {
    authorService = new AuthorServiceImpl(authorRepository);
  }

  @Test
  @DisplayName("Get authors without query successfully")
  void testGetAuthorsWithoutQuery() {
    Author author = Mockito.mock(Author.class);
    when(authorRepository.findAll(any(PageRequest.class))).thenReturn(authors);
    when(authors.get()).thenReturn(Stream.of(author));

    assertDoesNotThrow(() -> {
      PaginatedData<AuthorDTO> actualResult =
          authorService.getAuthors(0, 10, null);
      assertEquals(1, actualResult.getData().size());
    });
  }

  @Test
  @DisplayName("Get authors with query successfully")
  void testGetAuthorsWithQuery() {
    Author author = Mockito.mock(Author.class);
    when(authorRepository.findAllByLastNameContainsIgnoreCaseOrFirstNameContainsIgnoreCase(
        anyString(), anyString(), any(PageRequest.class))).thenReturn(authors);
    when(authors.get()).thenReturn(Stream.of(author));

    assertDoesNotThrow(() -> {
      PaginatedData<AuthorDTO> actualResult =
          authorService.getAuthors(0, 10, "test");
      assertEquals(1, actualResult.getData().size());
    });
  }

  @Test
  @DisplayName("Create author fails - missing last name")
  void testCreateAuthorFailedMissingLastName() {
    AuthorDTO createAuthor = new AuthorDTO()
        .withFirstName("John");

    ThesisInfoServiceException exception = assertThrows(ThesisInfoServiceException.class, () ->
        authorService.createAuthor(createAuthor));
    assertEquals("Last name is required.", exception.getMessage());
  }

  @Test
  @DisplayName("Create author fails - missing last name")
  void testCreateAuthorFailedMissingFirstName() {
    AuthorDTO createAuthor = new AuthorDTO()
        .withLastName("Doe");

    ThesisInfoServiceException exception = assertThrows(ThesisInfoServiceException.class, () ->
        authorService.createAuthor(createAuthor));
    assertEquals("First name is required.", exception.getMessage());
  }

  @Test
  @DisplayName("Create author successfully")
  void testCreateAuthorSuccessfully() {
    AuthorDTO createAuthor = new AuthorDTO()
        .withLastName("Doe")
        .withFirstName("John");
    Author mockedAuthor = Mockito.mock(Author.class);
    when(mockedAuthor.getLastName()).thenReturn("Doe");
    when(mockedAuthor.getFirstName()).thenReturn("John");
    when(authorRepository.save(any(Author.class))).thenReturn(mockedAuthor);

    assertDoesNotThrow(() -> {
      AuthorDTO actualResult = authorService.createAuthor(createAuthor);
      assertEquals("Doe", actualResult.getLastName());
      assertEquals("John", actualResult.getFirstName());
    });
  }

  @Test
  @DisplayName("Get author failed - not found")
  void testGetAuthorNotFound() {
    when(authorRepository.findById(11L)).thenReturn(Optional.empty());

    ThesisInfoServiceException exception = assertThrows(ThesisInfoServiceException.class, () ->
        authorService.getAuthor(11));
    assertEquals("Author with id (11) not found.", exception.getMessage());
  }

  @Test
  @DisplayName("Get author successfully")
  void testGetAuthorSuccessfully() {
    Author mockedAuthor = Mockito.mock(Author.class);
    when(mockedAuthor.getLastName()).thenReturn("Doe");
    when(mockedAuthor.getFirstName()).thenReturn("John");
    when(authorRepository.findById(11L)).thenReturn(Optional.of(mockedAuthor));

    assertDoesNotThrow(() -> {
      AuthorDTO actualResult = authorService.getAuthor(11);
      assertEquals("Doe", actualResult.getLastName());
      assertEquals("John", actualResult.getFirstName());
    });
  }

  @Test
  @DisplayName("Delete author successfully")
  void testDeleteAuthorSuccessfully() {
    doNothing().when(authorRepository).deleteById(11L);

    authorService.deleteAuthor(11);
    verify(authorRepository).deleteById(11L);
  }

  @Test
  @DisplayName("Update author failed - not found")
  void testUpdateAuthorFailedNotFound() {
    when(authorRepository.findById(11L)).thenReturn(Optional.empty());

    AuthorDTO updatedAuthor = new AuthorDTO();
    ThesisInfoServiceException exception = assertThrows(ThesisInfoServiceException.class, () ->
        authorService.updateAuthor(11, updatedAuthor));
    assertEquals("Author with id (11) not found.", exception.getMessage());
  }

  @Test
  @DisplayName("Update author successfully")
  void testUpdateAuthorSuccessfully() {
    Author mockedAuthor = Mockito.mock(Author.class);
    when(authorRepository.findById(11L)).thenReturn(Optional.of(mockedAuthor));
    when(authorRepository.save(mockedAuthor)).thenReturn(mockedAuthor);

    AuthorDTO updatedAuthor = new AuthorDTO()
        .withLastName("Smith")
        .withFirstName("John");
    assertDoesNotThrow(() -> {
      authorService.updateAuthor(11, updatedAuthor);
      verify(authorRepository).save(mockedAuthor);
    });
  }
}
