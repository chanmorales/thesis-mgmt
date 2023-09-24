import React, { useCallback, useEffect, useState } from "react";
import Table from "../common/Table";
import { ColumnsType } from "antd/es/table";
import { Degree } from "../../types/Degrees";
import { Button, Input, Popconfirm, Space, TablePaginationConfig } from "antd";
import { DEFAULT_PAGE_SIZE } from "../../common/constants";
import {
  DeleteFilled,
  EditFilled,
  PlusCircleFilled,
  SearchOutlined,
} from "@ant-design/icons";
import DegreeService from "../../services/DegreeService";

interface DegreesTableProps {
  refetchData: boolean;
  setRefetchData: (value: boolean) => void;
  onAddDegree: () => void;
  onUpdateDegree: (degree: Degree) => void;
  onDeleteDegree: (degreeId: number) => void;
}

const DegreesTable: React.FC<DegreesTableProps> = ({
  refetchData,
  setRefetchData,
  onAddDegree,
  onUpdateDegree,
  onDeleteDegree,
}) => {
  const [degrees, setDegrees] = useState<Degree[]>([]);
  const [isFetching, setIsFetching] = useState(false);
  const [searchText, setSearchText] = useState("");
  const [pagination, setPagination] = useState<TablePaginationConfig>({
    current: 1,
    pageSize: DEFAULT_PAGE_SIZE,
    showTotal: (total, range) =>
      `${range[0]} - ${range[1]} of ${total} degrees`,
  });

  const fetchDegrees = useCallback(async () => {
    if (pagination && refetchData) {
      setIsFetching(true);
      const degrees = await DegreeService.getDegrees(
        (pagination.current ?? 1) - 1,
        pagination.pageSize ?? DEFAULT_PAGE_SIZE,
        searchText
      );
      setDegrees(degrees.data);
      setPagination({
        ...pagination,
        current: degrees.page + 1,
        pageSize: degrees.pageSize,
        total: degrees.total,
      });
      setIsFetching(false);
    }
  }, [pagination, refetchData, searchText]);

  useEffect(() => {
    setRefetchData(true);
  }, [setRefetchData]);

  useEffect(() => {
    if (refetchData) {
      fetchDegrees().catch(console.error);
      setRefetchData(false);
    }
  }, [fetchDegrees, refetchData, setRefetchData]);

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

  const columns: ColumnsType<Degree> = [
    {
      title: "Code",
      key: "code",
      dataIndex: "code",
      width: "15%",
    },
    {
      title: "Name",
      key: "name",
      dataIndex: "name",
    },
    {
      key: "action",
      width: "25%",
      render: (_, degree) => (
        <Space>
          <Button
            type="link"
            icon={<EditFilled />}
            onClick={() => onUpdateDegree(degree)}
          >
            Edit
          </Button>
          <Popconfirm
            title="Delete Degree"
            description="Are you sure you want to delete this degree?"
            icon={<DeleteFilled />}
            okText="Delete"
            okButtonProps={{ danger: true }}
            onConfirm={() => onDeleteDegree(degree.id)}
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
    <div id="degrees-table-container">
      <div
        id="degrees-controls-container"
        className="flex justify-between items-center"
      >
        <Button
          className="my-2"
          type="primary"
          icon={<PlusCircleFilled />}
          onClick={onAddDegree}
        >
          Add Degree
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
        dataSource={degrees}
        rowKey="id"
        scroll={{ y: 480 }}
        pagination={pagination}
        onChange={onTableChange}
      />
    </div>
  );
};

export default DegreesTable;
