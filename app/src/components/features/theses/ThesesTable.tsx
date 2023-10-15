import React, { useCallback, useEffect, useState } from "react";
import {
  Button,
  Input,
  Popconfirm,
  Space,
  TablePaginationConfig,
  Typography,
} from "antd";
import {
  DeleteFilled,
  EditFilled,
  PlusCircleFilled,
  SearchOutlined,
} from "@ant-design/icons";
import Table from "../../common/Table";
import { Thesis } from "../../../types/Thesis";
import { ColumnsType } from "antd/es/table";
import { DEFAULT_PAGE_SIZE } from "../../../common/constants";
import ThesisService from "../../../services/ThesisService";

interface ThesesTableProps {
  refetchData: boolean;
  setRefetchData: (value: boolean) => void;
  onAddThesis: () => void;
  onUpdateThesis: (thesis: Thesis) => void;
  onDeleteThesis: (thesisId: number) => void;
}

const { Paragraph, Text } = Typography;

const ThesesTable: React.FC<ThesesTableProps> = ({
  refetchData,
  setRefetchData,
  onAddThesis,
  onUpdateThesis,
  onDeleteThesis,
}) => {
  const [theses, setTheses] = useState<Thesis[]>([]);
  const [isFetching, setIsFetching] = useState(false);
  const [searchText, setSearchText] = useState("");
  const [pagination, setPagination] = useState<TablePaginationConfig>({
    current: 1,
    pageSize: DEFAULT_PAGE_SIZE,
    showTotal: (total, range) => `${range[0]} - ${range[1]} of ${total} theses`,
  });

  const fetchTheses = useCallback(async () => {
    if (pagination && refetchData) {
      setIsFetching(true);
      const theses = await ThesisService.getTheses(
        (pagination.current ?? 1) - 1,
        pagination.pageSize ?? DEFAULT_PAGE_SIZE,
        searchText
      );
      setTheses(theses.data);
      setPagination({
        ...pagination,
        current: theses.page + 1,
        pageSize: theses.pageSize,
        total: theses.total,
      });
      setIsFetching(false);
    }
  }, [pagination, refetchData, searchText]);

  useEffect(() => {
    setRefetchData(true);
  }, [setRefetchData]);

  useEffect(() => {
    if (refetchData) {
      fetchTheses().catch(console.error);
      setRefetchData(false);
    }
  }, [fetchTheses, refetchData, setRefetchData]);

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

  const columns: ColumnsType<Thesis> = [
    {
      title: "Title",
      key: "title",
      dataIndex: "title",
    },
    {
      title: "Degree",
      key: "degree",
      width: "30%",
      render: (_, thesis) => (
        <Space>
          <Paragraph>
            <Text strong>{thesis.degree.code} </Text>
            {thesis.degree.name}
          </Paragraph>
        </Space>
      ),
    },
    {
      title: "Date Submitted",
      key: "dateSubmitted",
      width: "15%",
      render: (_, thesis) => (
        <Space>{`${
          thesis.dateSubmitted.year
        }-${thesis.dateSubmitted.month.toLocaleString("en-US", {
          minimumIntegerDigits: 2,
          useGrouping: false,
        })}`}</Space>
      ),
    },
    {
      key: "action",
      width: "20%",
      render: (_, thesis) => (
        <Space>
          <Button
            type="link"
            icon={<EditFilled />}
            onClick={() => onUpdateThesis(thesis)}
          >
            Edit
          </Button>
          <Popconfirm
            title="Delete Thesis"
            description="Are you sure you want to delete this thesis?"
            icon={<DeleteFilled />}
            okText="Delete"
            okButtonProps={{ danger: true }}
            onConfirm={() => onDeleteThesis(thesis.id)}
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
    <div id="theses-table-container">
      <div
        id="theses-controls-container"
        className="flex justify-between items-center"
      >
        <Button
          className="my-2"
          type="primary"
          icon={<PlusCircleFilled />}
          onClick={onAddThesis}
        >
          Add Thesis
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
        dataSource={theses}
        pagination={pagination}
        onChange={onTableChange}
      />
    </div>
  );
};

export default ThesesTable;
