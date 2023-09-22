package io.dev.mutex.thesisinfomgmt.util;

import static io.dev.mutex.thesisinfomgmt.common.Constants.BASIC_NAME_FORMAT;
import static io.dev.mutex.thesisinfomgmt.common.Constants.BASIC_NAME_FORMAT_NO_MI;
import static io.dev.mutex.thesisinfomgmt.common.Constants.EMPTY_STRING;

import io.dev.mutex.thesisinfomgmt.common.PaginatedData;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DataHelper {

  /**
   * Checks if provided string is either null or empty (including all whitespaces)
   *
   * @param value the string to be checked
   * @return <b>true</b> if string is either null or empty, otherwise, <b>false</b>
   */
  public static boolean isNullOrEmpty(String value) {
    return null == value || EMPTY_STRING.equals(value.trim());
  }

  /**
   * Checks if provided string is neither null nor empty (including all whitespaces)
   *
   * @param value the string to be checked
   * @return <b>true</b> if string is neither null nor empty, otherwise, <b>false</b>
   */
  public static boolean isNotNullNorEmpty(String value) {
    return !isNullOrEmpty(value);
  }

  /**
   * Formats name
   *
   * @param lastName   the last name
   * @param firstName  the first name
   * @param middleName the optional middle name
   * @return formatted name
   */
  public static String formatName(
      @NonNull String lastName,
      @NonNull String firstName,
      @Nullable String middleName
  ) {
    return isNullOrEmpty(middleName) ?
        String.format(BASIC_NAME_FORMAT_NO_MI, lastName, firstName) :
        String.format(BASIC_NAME_FORMAT, lastName, firstName, middleName.charAt(0));
  }

  /**
   * Converts page of entity to paginated dto data
   *
   * @param page        the page details
   * @param pageRequest the page request details
   * @param mapper      the data mapper
   * @param <T>         the type of page data
   * @param <R>         the type of the resulting paginated data
   * @return paginated data
   */
  public static <T, R> PaginatedData<R> toPaginatedData(
      Page<T> page, PageRequest pageRequest, Function<T, R> mapper
  ) {
    PaginatedData<R> result = new PaginatedData<>();
    result.setPage(pageRequest.getPageNumber());
    result.setPageSize(pageRequest.getPageSize());
    result.setTotal(page.getTotalElements());
    result.setLastPage(!page.hasNext());
    result.setData(page.get().map(mapper).collect(Collectors.toList()));

    return result;
  }
}
