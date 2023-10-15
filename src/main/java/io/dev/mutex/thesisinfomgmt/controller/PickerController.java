package io.dev.mutex.thesisinfomgmt.controller;

import io.dev.mutex.thesisinfomgmt.dto.DegreeDTO;
import io.dev.mutex.thesisinfomgmt.service.PickerService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/picker")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class PickerController {

  private final PickerService pickerService;

  /**
   * Retrieves list of degrees for picker options
   *
   * @return 200 / OK if successful. Every other results indicates an error.
   */
  @GetMapping("/degrees")
  public ResponseEntity<List<DegreeDTO>> getDegreeOptions() {
    return ResponseEntity.ok(pickerService.getDegreeOptions());
  }
}
