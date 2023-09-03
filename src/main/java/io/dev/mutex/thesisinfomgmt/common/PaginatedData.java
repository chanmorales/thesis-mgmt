package io.dev.mutex.thesisinfomgmt.common;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaginatedData<T> {

  private List<T> data;

  private int page;

  private int pageSize;

  private long total;

  private boolean lastPage;
}
