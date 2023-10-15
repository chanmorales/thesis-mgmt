package io.dev.mutex.thesisinfomgmt.service;

import io.dev.mutex.thesisinfomgmt.dto.DegreeDTO;
import io.dev.mutex.thesisinfomgmt.repository.DegreeRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PickerServiceImpl implements PickerService {

  private final DegreeRepository degreeRepository;

  /**
   * {@inheritDoc}
   */
  @Override
  public List<DegreeDTO> getDegreeOptions() {
    return degreeRepository
        .findAll()
        .stream()
        .map(DegreeDTO::new)
        .collect(Collectors.toList());
  }
}
