package io.dev.mutex.thesisinfomgmt.service;

import static io.dev.mutex.thesisinfomgmt.common.Errors.ENTITY_NOT_FOUND;
import static io.dev.mutex.thesisinfomgmt.common.Errors.PROPERTY_REQUIRED;

import io.dev.mutex.thesisinfomgmt.common.PaginatedData;
import io.dev.mutex.thesisinfomgmt.exception.ThesisInfoServiceException;
import io.dev.mutex.thesisinfomgmt.model.dto.AuthorDTO;
import io.dev.mutex.thesisinfomgmt.model.entity.Author;
import io.dev.mutex.thesisinfomgmt.repository.AuthorRepository;
import io.dev.mutex.thesisinfomgmt.util.DataHelper;
import io.dev.mutex.thesisinfomgmt.util.ModelMapper;
import io.dev.mutex.thesisinfomgmt.util.PaginationHelper;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

  private final AuthorRepository authorRepository;

  /**
   * {@inheritDoc}
   */
  @Override
  public PaginatedData<AuthorDTO> getAuthors(int page, int pageSize, String query) {
    // Construct the page request
    PageRequest pageRequest = PaginationHelper.of(page, pageSize);

    // Check if there's a query string
    Page<Author> authors;
    if (DataHelper.isNullOrEmpty(query)) {
      authors = authorRepository.findAll(pageRequest);
    } else {
      authors = authorRepository.findAllByLastNameContainsIgnoreCaseOrFirstNameContainsIgnoreCase(
          query, query, pageRequest);
    }

    PaginatedData<AuthorDTO> result = new PaginatedData<>();
    result.setPage(pageRequest.getPageNumber());
    result.setPageSize(pageRequest.getPageSize());
    result.setTotal(authors.getTotalElements());
    result.setLastPage(!authors.hasNext());
    result.setData(authors.get().map(AuthorDTO::new).collect(Collectors.toList()));

    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AuthorDTO createAuthor(AuthorDTO author) {
    // Validate the author to be created
    validateAuthor(author);

    // Create the author
    Author newAuthor = ModelMapper.map(author);
    newAuthor = authorRepository.save(newAuthor);
    return new AuthorDTO(newAuthor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AuthorDTO getAuthor(long id) {
    // Check if the author to be retrieved exists
    Optional<Author> author = authorRepository.findById(id);
    if (author.isEmpty()) {
      handleAuthorNotFound(id);
    }

    return new AuthorDTO(author.get());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteAuthor(long id) {
    authorRepository.deleteById(id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void updateAuthor(long id, AuthorDTO author) {
    // Check if author to be updated exists
    Optional<Author> updateAuthor = authorRepository.findById(id);
    if (updateAuthor.isEmpty()) {
      handleAuthorNotFound(id);
    }

    // Validate the updated author details
    validateAuthor(author);

    // Update the author
    Author updatedAuthor = updateAuthor.get();
    updatedAuthor.setLastName(author.getLastName());
    updatedAuthor.setFirstName(author.getFirstName());
    updatedAuthor.setMiddleName(author.getMiddleName());
    authorRepository.save(updatedAuthor);
  }

  /**
   * Validate author details
   *
   * @param author the author details to be validated
   */
  private void validateAuthor(AuthorDTO author) {
    // Last name is required
    if (DataHelper.isNullOrEmpty(author.getLastName())) {
      throw new ThesisInfoServiceException(
          String.format(PROPERTY_REQUIRED, "Last name"), "lastName", HttpStatus.BAD_REQUEST);
    }

    // Last name is required
    if (DataHelper.isNullOrEmpty(author.getFirstName())) {
      throw new ThesisInfoServiceException(
          String.format(PROPERTY_REQUIRED, "First name"), "firstName", HttpStatus.BAD_REQUEST);
    }
  }

  /**
   * Handles author not found
   *
   * @param id the id of the non-existing author
   */
  private void handleAuthorNotFound(long id) {
    throw new ThesisInfoServiceException(
        String.format(ENTITY_NOT_FOUND, "Author", "id", id),
        HttpStatus.NOT_FOUND);
  }
}
