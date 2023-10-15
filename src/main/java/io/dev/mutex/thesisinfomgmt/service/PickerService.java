package io.dev.mutex.thesisinfomgmt.service;

import io.dev.mutex.thesisinfomgmt.dto.DegreeDTO;
import java.util.List;

public interface PickerService {

  /**
   * Retrieve list of degrees for picker options
   *
   * @return list of degrees for picker options
   */
  List<DegreeDTO> getDegreeOptions();
}
