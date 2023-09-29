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
import io.dev.mutex.thesisinfomgmt.dto.RoleDTO;
import io.dev.mutex.thesisinfomgmt.exception.ThesisInfoServiceException;
import io.dev.mutex.thesisinfomgmt.model.Role;
import io.dev.mutex.thesisinfomgmt.repository.RoleRepository;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

  @Mock
  private RoleRepository roleRepository;

  @Mock
  private Role mockedRole1;
  @Mock
  private Role mockedRole2;
  @Mock
  private Page<Role> mockedPageOfRole;

  private RoleService roleService;

  @BeforeEach
  void setup() {
    roleService = new RoleServiceImpl(roleRepository);
  }

  @Nested
  @DisplayName("Get roles tests")
  class GetRolesTests {

    @Test
    @DisplayName("Should retrieve roles without query")
    void shouldRetrieveRolesWithoutQuery() {
      when(roleRepository.findAll(any(PageRequest.class)))
          .thenReturn(mockedPageOfRole);
      when(mockedPageOfRole.get())
          .thenReturn(Stream.of(mockedRole1, mockedRole2));
      when(mockedPageOfRole.getTotalElements()).thenReturn(2L);
      when(mockedPageOfRole.hasNext()).thenReturn(false);

      assertDoesNotThrow(() -> {
        PaginatedData<RoleDTO> actualResult =
            roleService.getRoles(0, 10, null);
        assertEquals(0, actualResult.getPage());
        assertEquals(10, actualResult.getPageSize());
        assertEquals(2, actualResult.getTotal());
        assertTrue(actualResult.isLastPage());
        assertEquals(2, actualResult.getData().size());
      });
    }

    @Test
    @DisplayName("Should retrieve roles with query")
    void shouldRetrieveRolesWithQuery() {
      when(roleRepository
          .findAllByNameContainsIgnoreCase(
              anyString(), any(PageRequest.class)))
          .thenReturn(mockedPageOfRole);
      when(mockedPageOfRole.get())
          .thenReturn(Stream.of(mockedRole1, mockedRole2));
      when(mockedPageOfRole.getTotalElements()).thenReturn(2L);
      when(mockedPageOfRole.hasNext()).thenReturn(false);

      assertDoesNotThrow(() -> {
        PaginatedData<RoleDTO> actualResult =
            roleService.getRoles(0, 10, "test");
        assertEquals(0, actualResult.getPage());
        assertEquals(10, actualResult.getPageSize());
        assertEquals(2, actualResult.getTotal());
        assertTrue(actualResult.isLastPage());
        assertEquals(2, actualResult.getData().size());
      });
    }
  }

  @Nested
  @DisplayName("Create role tests")
  class CreateRoleTests {

    @Test
    @DisplayName("Should fail when name is missing")
    void shouldFailWhenNameIsMissing() {
      RoleDTO inputRole = new RoleDTO();
      ThesisInfoServiceException exception = assertThrows(ThesisInfoServiceException.class,
          () -> roleService.createRole(inputRole));
      assertEquals("Role name is required.", exception.getMessage());
    }

    @Test
    @DisplayName("Should fail when name is duplicate")
    void shouldFailWhenNameIsDuplicate() {
      when(roleRepository.findByNameIgnoreCase(anyString()))
          .thenReturn(Optional.of(mockedRole1));
      when(mockedRole1.getId()).thenReturn(11L);

      RoleDTO inputRole = new RoleDTO()
          .withName("Adviser");
      ThesisInfoServiceException exception = assertThrows(ThesisInfoServiceException.class,
          () -> roleService.createRole(inputRole));
      assertEquals("Role with name: Adviser already exists.", exception.getMessage());
    }

    @Test
    @DisplayName("Should create role successfully")
    void shouldCreateRoleSuccessfully() {
      RoleDTO inputRole = new RoleDTO()
          .withName("Adviser");
      when(roleRepository.save(any(Role.class)))
          .thenReturn(mockedRole1);

      assertDoesNotThrow(() -> roleService.createRole(inputRole));
    }
  }

  @Nested
  @DisplayName("Get role tests")
  class GetRoleTests {

    @Test
    @DisplayName("Should fail when role does not exists")
    void shouldFailWhenRoleDoesNotExists() {
      when(roleRepository.findById(11L)).thenReturn(Optional.empty());

      ThesisInfoServiceException exception = assertThrows(ThesisInfoServiceException.class,
          () -> roleService.getRole(11));
      assertEquals("Role with id (11) not found.", exception.getMessage());
    }

    @Test
    @DisplayName("Should retrieve role successfully")
    void shouldRetrieveRoleSuccessfully() {
      when(roleRepository.findById(11L)).thenReturn(Optional.of(mockedRole1));

      assertDoesNotThrow(() -> roleService.getRole(11));
    }
  }

  @Test
  @DisplayName("Should delete role successfully")
  void shouldDeleteRoleSuccessfully() {
    doNothing().when(roleRepository).deleteById(11L);

    assertDoesNotThrow(() -> roleService.deleteRole(11));
  }

  @Nested
  @DisplayName("Update role tests")
  class UpdateRoleTests {

    @Test
    @DisplayName("Should fail when role does not exists")
    void shouldFailWhenRoleDoesNotExists() {
      when(roleRepository.findById(11L)).thenReturn(Optional.empty());

      RoleDTO inputRole = new RoleDTO();
      ThesisInfoServiceException exception = assertThrows(ThesisInfoServiceException.class,
          () -> roleService.updateRole(11, inputRole));
      assertEquals("Role with id (11) not found.", exception.getMessage());
    }

    @Test
    @DisplayName("Should successfully update role")
    void shouldSuccessfullyUpdateRole() {
      when(roleRepository.findById(11L)).thenReturn(Optional.of(mockedRole1));
      when(roleRepository.save(mockedRole1)).thenReturn(mockedRole1);

      RoleDTO inputRole = new RoleDTO()
          .withName("Adviser");
      assertDoesNotThrow(() -> {
        roleService.updateRole(11, inputRole);
        verify(roleRepository).save(mockedRole1);
      });
    }
  }
}
