export interface PaginatedData<T> {
  data: T[];
  page: number;
  pageSize: number;
  total: number;
  lastPage: boolean;
}

export interface RequestException {
  message: string;
  field?: string;
}
