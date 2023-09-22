import React from "react";
import { Table as AntdTable, TableProps as AntdTableProps } from "antd";

const Table = <T extends object>({
  pagination,
  ...rest
}: AntdTableProps<T>) => {
  return (
    <AntdTable
      className="h-full"
      pagination={{
        ...pagination,
        hideOnSinglePage: true,
        showSizeChanger: false,
      }}
      {...rest}
    />
  );
};

export default Table;
