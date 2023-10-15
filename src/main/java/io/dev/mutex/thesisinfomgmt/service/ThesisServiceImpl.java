package io.dev.mutex.thesisinfomgmt.service;

import static io.dev.mutex.thesisinfomgmt.common.Constants.ENTITY_DEGREE;
import static io.dev.mutex.thesisinfomgmt.common.Constants.ENTITY_THESIS;
import static io.dev.mutex.thesisinfomgmt.common.Constants.YEAR_MONTH_FORMAT;
import static io.dev.mutex.thesisinfomgmt.common.Errors.ENTITY_NOT_FOUND;
import static io.dev.mutex.thesisinfomgmt.common.Errors.PROPERTY_REQUIRED;

import io.dev.mutex.thesisinfomgmt.common.PaginatedData;
import io.dev.mutex.thesisinfomgmt.dto.DateSubmittedDTO;
import io.dev.mutex.thesisinfomgmt.dto.ThesisDTO;
import io.dev.mutex.thesisinfomgmt.exception.ThesisInfoServiceException;
import io.dev.mutex.thesisinfomgmt.model.Degree;
import io.dev.mutex.thesisinfomgmt.model.Thesis;
import io.dev.mutex.thesisinfomgmt.repository.ThesisRepository;
import io.dev.mutex.thesisinfomgmt.util.DataHelper;
import io.dev.mutex.thesisinfomgmt.util.PaginationHelper;
import java.time.Year;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ThesisServiceImpl implements ThesisService {

  private final DegreeService degreeService;
  private final ThesisRepository thesisRepository;

  /**
   * {@inheritDoc}
   */
  @Override
  public PaginatedData<ThesisDTO> getTheses(int page, int pageSize, String query) {
    // Construct the page request
    PageRequest pageRequest = PaginationHelper.of(page, pageSize);

    // Retrieve data based on query string
    Page<Thesis> theses;
    if (DataHelper.isNullOrEmpty(query)) {
      theses = thesisRepository.findAll(pageRequest);
    } else {
      theses = thesisRepository.findAllByTitleContainsIgnoreCase(query, pageRequest);
    }

    return DataHelper.toPaginatedData(theses, pageRequest, ThesisDTO::new);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ThesisDTO createThesis(ThesisDTO thesis) {
    // Validate the thesis to be created
    validateThesis(thesis);

    // Get the degree
    Optional<Degree> degree = degreeService.retrieveDegreeById(thesis.getDegree().getId());
    if (degree.isEmpty()) {
      throw new ThesisInfoServiceException(String.format(ENTITY_NOT_FOUND, ENTITY_DEGREE),
          "degree", HttpStatus.NOT_FOUND);
    }

    // Create the thesis
    Thesis newThesis = new Thesis();
    newThesis.setTitle(thesis.getTitle());
    newThesis.setDateSubmitted(
        String.format(YEAR_MONTH_FORMAT,
            thesis.getDateSubmitted().getYear(),
            thesis.getDateSubmitted().getMonth()));
    newThesis.setDegree(degree.get());
    newThesis = thesisRepository.save(newThesis);

    return new ThesisDTO(newThesis);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ThesisDTO getThesis(long id) {
    // Check if the thesis to be retrieved exists
    Optional<Thesis> thesis = thesisRepository.findById(id);
    if (thesis.isEmpty()) {
      throw new ThesisInfoServiceException(String.format(ENTITY_NOT_FOUND, ENTITY_THESIS),
          HttpStatus.NOT_FOUND);
    }

    return new ThesisDTO(thesis.get());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteThesis(long id) {
    thesisRepository.deleteById(id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void updateThesis(long id, ThesisDTO thesis) {
    // Check if thesis to be updated exists
    Optional<Thesis> updateThesis = thesisRepository.findById(id);
    if (updateThesis.isEmpty()) {
      throw new ThesisInfoServiceException(String.format(ENTITY_NOT_FOUND, ENTITY_THESIS),
          HttpStatus.NOT_FOUND);
    }

    // Check if degree exists
    Optional<Degree> degree = degreeService.retrieveDegreeById(thesis.getDegree().getId());
    if (degree.isEmpty()) {
      throw new ThesisInfoServiceException(String.format(ENTITY_NOT_FOUND, ENTITY_DEGREE),
          "degree", HttpStatus.NOT_FOUND);
    }

    // Validate the thesis to be updated
    validateThesis(thesis);

    // Update the thesis
    Thesis updatedThesis = updateThesis.get();
    updatedThesis.setTitle(thesis.getTitle());
    updatedThesis.setDateSubmitted(
        String.format(YEAR_MONTH_FORMAT,
            thesis.getDateSubmitted().getYear(),
            thesis.getDateSubmitted().getMonth()));
    updatedThesis.setDegree(degree.get());
    thesisRepository.save(updatedThesis);
  }

  /**
   * Validates thesis details
   *
   * @param thesis the thesis details to be validated
   */
  private void validateThesis(ThesisDTO thesis) {
    // Validate title
    validateThesisTitle(thesis.getTitle());

    // Validate date submitted
    validateThesisDateSubmitted(thesis.getDateSubmitted());
  }

  /**
   * Validates thesis title
   *
   * @param title the title of the thesis to be validated
   */
  private void validateThesisTitle(String title) {
    // Title is required
    if (DataHelper.isNullOrEmpty(title)) {
      throw new ThesisInfoServiceException(
          String.format(PROPERTY_REQUIRED, "Title"), "title", HttpStatus.BAD_REQUEST);
    }
  }

  /**
   * Validates thesis date submitted
   *
   * @param dateSubmitted the submitted date of the thesis to be validated
   */
  private void validateThesisDateSubmitted(DateSubmittedDTO dateSubmitted) {
    // Date submitted is required
    if (null == dateSubmitted) {
      throw new ThesisInfoServiceException(
          String.format(PROPERTY_REQUIRED, "Date submitted"), "dateSubmitted",
          HttpStatus.BAD_REQUEST);
    }

    // Year and Month should be valid
    if (dateSubmitted.getYear() < Year.MIN_VALUE ||
        dateSubmitted.getYear() > Year.MAX_VALUE ||
        dateSubmitted.getMonth() < 1 ||
        dateSubmitted.getMonth() > 12) {
      throw new ThesisInfoServiceException("Date submitted is invalid year month",
          "dateSubmitted", HttpStatus.BAD_REQUEST);
    }
  }
}
