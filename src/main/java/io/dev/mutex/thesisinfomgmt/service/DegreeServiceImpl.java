package io.dev.mutex.thesisinfomgmt.service;

import static io.dev.mutex.thesisinfomgmt.common.Constants.ATTRIBUTE_ID;
import static io.dev.mutex.thesisinfomgmt.common.Constants.ENTITY_DEGREE;
import static io.dev.mutex.thesisinfomgmt.common.Errors.ENTITY_NOT_FOUND;
import static io.dev.mutex.thesisinfomgmt.common.Errors.PROPERTY_REQUIRED;
import static io.dev.mutex.thesisinfomgmt.common.Errors.PROPERTY_SHOULD_BE_UNIQUE;

import io.dev.mutex.thesisinfomgmt.common.PaginatedData;
import io.dev.mutex.thesisinfomgmt.dto.DegreeDTO;
import io.dev.mutex.thesisinfomgmt.exception.ThesisInfoServiceException;
import io.dev.mutex.thesisinfomgmt.model.Degree;
import io.dev.mutex.thesisinfomgmt.repository.DegreeRepository;
import io.dev.mutex.thesisinfomgmt.util.DataHelper;
import io.dev.mutex.thesisinfomgmt.util.ModelMapper;
import io.dev.mutex.thesisinfomgmt.util.PaginationHelper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DegreeServiceImpl implements DegreeService {

  private final DegreeRepository degreeRepository;

  /**
   * {@inheritDoc}
   */
  @Override
  public PaginatedData<DegreeDTO> getDegrees(int page, int pageSize, String query) {
    // Construct the page request
    PageRequest pageRequest = PaginationHelper.of(page, pageSize);

    // Retrieve data based on the query string
    Page<Degree> degrees;
    if (DataHelper.isNullOrEmpty(query)) {
      degrees = degreeRepository.findAll(pageRequest);
    } else {
      degrees = degreeRepository
          .findAllByCodeContainsIgnoreCaseOrNameContainsIgnoreCase(
              query, query, pageRequest);
    }

    return DataHelper.toPaginatedData(degrees, pageRequest, DegreeDTO::new);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DegreeDTO createDegree(DegreeDTO degree) {
    // Validate the degree to be created
    validateDegree(degree, NumberUtils.INTEGER_MINUS_ONE);

    // Create the degree
    Degree newDegree = ModelMapper.map(degree);
    newDegree = degreeRepository.save(newDegree);
    return new DegreeDTO(newDegree);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public DegreeDTO getDegree(long id) {
    // Check if the degree to be retrieved exists
    Optional<Degree> degree = degreeRepository.findById(id);
    if (degree.isEmpty()) {
      handleDegreeNotFound(id);
    }

    return new DegreeDTO(degree.get());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteDegree(long id) {
    degreeRepository.deleteById(id);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void updateDegree(long id, DegreeDTO degree) {
    // Check if degree to be updated exists
    Optional<Degree> updateDegree = degreeRepository.findById(id);
    if (updateDegree.isEmpty()) {
      handleDegreeNotFound(id);
    }

    // Validate the updated degree details
    validateDegree(degree, id);

    // Update the degree
    Degree updatedDegree = updateDegree.get();
    updatedDegree.setCode(degree.getCode());
    updatedDegree.setName(degree.getName());
    degreeRepository.save(updatedDegree);
  }

  /**
   * Validates degree details
   *
   * @param degree the degree details to be validated
   * @param id     the id of the degree
   */
  private void validateDegree(DegreeDTO degree, long id) {
    // Code is required
    if (DataHelper.isNullOrEmpty(degree.getCode())) {
      throw new ThesisInfoServiceException(
          String.format(PROPERTY_REQUIRED, "Degree code"), "code", HttpStatus.BAD_REQUEST);
    }

    // Code should be unique
    Optional<Degree> duplicateCodeDegree = degreeRepository.findByCodeIgnoreCase(degree.getCode());
    if (duplicateCodeDegree.isPresent() &&
        duplicateCodeDegree.get().getId() != id) {
      throw new ThesisInfoServiceException(
          String.format(PROPERTY_SHOULD_BE_UNIQUE, "Degree", "code", degree.getCode()), "code",
          HttpStatus.BAD_REQUEST);
    }

    // Name is required
    if (DataHelper.isNullOrEmpty(degree.getName())) {
      throw new ThesisInfoServiceException(
          String.format(PROPERTY_REQUIRED, "Degree name"), "name", HttpStatus.BAD_REQUEST);
    }

    // Name should be unique
    Optional<Degree> duplicateNameDegree = degreeRepository.findByNameIgnoreCase(degree.getName());
    if (duplicateNameDegree.isPresent() &&
        duplicateNameDegree.get().getId() != id) {
      throw new ThesisInfoServiceException(
          String.format(PROPERTY_SHOULD_BE_UNIQUE, "Degree", "name", degree.getName()), "name",
          HttpStatus.BAD_REQUEST);
    }
  }

  /**
   * Handles throwing of exception when degree is not found
   *
   * @param id the id of the non-existing degree
   */
  private void handleDegreeNotFound(long id) {
    throw new ThesisInfoServiceException(
        String.format(ENTITY_NOT_FOUND, ENTITY_DEGREE, ATTRIBUTE_ID, id),
        HttpStatus.NOT_FOUND);
  }
}
