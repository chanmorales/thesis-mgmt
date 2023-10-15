import React, { useCallback, useEffect, useState } from "react";
import Table from "../../common/Table";
import AuthorService from "../../../services/AuthorService";
import { Author } from "../../../types/Author";
import { ColumnsType } from "antd/es/table";
import { Button, Input, Popconfirm, Space, TablePaginationConfig } from "antd";
import {
  DeleteFilled,
  EditFilled,
  PlusCircleFilled,
  SearchOutlined,
} from "@ant-design/icons";
import { DEFAULT_PAGE_SIZE } from "../../../common/constants";

interface AuthorsTableProps {
  refetchData: boolean;
  setRefetchData: (value: boolean) => void;
  onAddAuthor: () => void;
  onUpdateAuthor: (author: Author) => void;
  onDeleteAuthor: (authorId: number) => void;
}

const AuthorsTable: React.FC<AuthorsTableProps> = ({
  refetchData,
  setRefetchData,
  onAddAuthor,
  onUpdateAuthor,
  onDeleteAuthor,
}) => {
  const [authors, setAuthors] = useState<Author[]>([]);
  const [isFetching, setIsFetching] = useState(false);
  const [searchText, setSearchText] = useState("");
  const [pagination, setPagination] = useState<TablePaginationConfig>({
    current: 1,
    pageSize: DEFAULT_PAGE_SIZE,
    showTotal: (total, range) =>
      `${range[0]} - ${range[1]} of ${total} authors`,
  });

  const fetchAuthors = useCallback(async () => {
    if (pagination && refetchData) {
      setIsFetching(true);
      const authors = await AuthorService.getAuthors(
        (pagination.current ?? 1) - 1,
        pagination.pageSize ?? DEFAULT_PAGE_SIZE,
        searchText
      );
      setAuthors(authors.data);
      setPagination({
        ...pagination,
        current: authors.page + 1,
        pageSize: authors.pageSize,
        total: authors.total,
      });
      setIsFetching(false);
    }
  }, [pagination, refetchData, searchText]);

  useEffect(() => {
    setRefetchData(true);
  }, [setRefetchData]);

  useEffect(() => {
    if (refetchData) {
      fetchAuthors().catch(console.error);
      setRefetchData(false);
    }
  }, [fetchAuthors, refetchData, setRefetchData]);

  const onTableSearch = (value: string) => {
    setSearchText(value);
    setPagination({
      ...pagination,
      current: 1,
    });
    setRefetchData(true);
  };

  const onTableChange = (newPagination: TablePaginationConfig) => {
    setPagination({
      ...pagination,
      current: newPagination.current,
      pageSize: newPagination.pageSize,
      total: newPagination.total,
    });
    setRefetchData(true);
  };

  const columns: ColumnsType<Author> = [
    {
      title: "Name",
      key: "name",
      render: (_, author) => (
        <Space>{`${author.lastName}, ${author.firstName} ${
          author.middleName ?? ""
        }`}</Space>
      ),
    },
    {
      key: "action",
      width: "25%",
      render: (_, author) => (
        <Space>
          <Button
            type="link"
            icon={<EditFilled />}
            onClick={() => onUpdateAuthor(author)}
          >
            Edit
          </Button>
          <Popconfirm
            title="Delete Author"
            description="Are you sure you want to delete this author?"
            icon={<DeleteFilled />}
            okText="Delete"
            okButtonProps={{ danger: true }}
            onConfirm={() => onDeleteAuthor(author.id)}
          >
            <Button danger type="text" icon={<DeleteFilled />}>
              Delete
            </Button>
          </Popconfirm>
        </Space>
      ),
    },
  ];

  return (
    <div id="authors-table-container">
      <div
        id="authors-controls-container"
        className="flex justify-between items-center"
      >
        <Button
          className="my-2"
          type="primary"
          icon={<PlusCircleFilled />}
          onClick={onAddAuthor}
        >
          Add Author
        </Button>
        <Input
          className="w-80"
          allowClear
          prefix={<SearchOutlined />}
          onChange={(e) => onTableSearch(e.target.value)}
        />
      </div>
      <Table
        loading={isFetching}
        columns={columns}
        dataSource={authors}
        pagination={pagination}
        onChange={onTableChange}
      />
    </div>
  );
};

export default AuthorsTable;
