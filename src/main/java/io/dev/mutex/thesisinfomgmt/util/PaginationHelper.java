package io.dev.mutex.thesisinfomgmt.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaginationHelper {

  /**
   * Creates a page request based on the specified page number and size
   *
   * @param page     the page number
   * @param pageSize the page size
   * @return created page request
   */
  public static PageRequest of(int page, int pageSize) {
    // Page should at least be 0
    page = Math.max(0, page);
    // Page size should at least be 1 and at most 100
    pageSize = Math.min(100, Math.max(1, pageSize));

    return PageRequest.of(page, pageSize);
  }
}
