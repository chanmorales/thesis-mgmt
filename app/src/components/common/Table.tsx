import React from "react";
import { Table as AntdTable, TableProps as AntdTableProps } from "antd";

const Table = <T extends object>({
  pagination,
  ...rest
}: AntdTableProps<T>) => {
  return (
    <AntdTable
      rowKey="id"
      scroll={{ y: 480 }}
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
