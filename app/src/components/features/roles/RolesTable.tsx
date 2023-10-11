import React, { useCallback, useEffect, useState } from "react";
import { Button, Input, Popconfirm, Space, TablePaginationConfig } from "antd";
import {
  DeleteFilled,
  EditFilled,
  PlusCircleFilled,
  SearchOutlined,
} from "@ant-design/icons";
import Table from "../../common/Table";
import { Role } from "../../../types/Roles";
import { ColumnsType } from "antd/es/table";
import { DEFAULT_PAGE_SIZE } from "../../../common/constants";
import RoleService from "../../../services/RoleService";

interface RolesTableProps {
  refetchData: boolean;
  setRefetchData: (value: boolean) => void;
  onAddRole: () => void;
  onUpdateRole: (role: Role) => void;
  onDeleteRole: (roleId: number) => void;
}

const RolesTable: React.FC<RolesTableProps> = ({
  refetchData,
  setRefetchData,
  onAddRole,
  onUpdateRole,
  onDeleteRole,
}) => {
  const [roles, setRoles] = useState<Role[]>([]);
  const [isFetching, setIsFetching] = useState(false);
  const [searchText, setSearchText] = useState("");
  const [pagination, setPagination] = useState<TablePaginationConfig>({
    current: 1,
    pageSize: DEFAULT_PAGE_SIZE,
    showTotal: (total, range) => `${range[0]} - ${range[1]} of ${total} roles`,
  });

  const fetchRoles = useCallback(async () => {
    if (pagination && refetchData) {
      setIsFetching(true);
      const roles = await RoleService.getRoles(
        (pagination.current ?? 1) - 1,
        pagination.pageSize ?? DEFAULT_PAGE_SIZE,
        searchText
      );
      setRoles(roles.data);
      setPagination({
        ...pagination,
        current: roles.page + 1,
        pageSize: roles.pageSize,
        total: roles.total,
      });
      setIsFetching(false);
    }
  }, [pagination, refetchData, searchText]);

  useEffect(() => {
    setRefetchData(true);
  }, [setRefetchData]);

  useEffect(() => {
    if (refetchData) {
      fetchRoles().catch(console.error);
      setRefetchData(false);
    }
  }, [fetchRoles, refetchData, setRefetchData]);

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

  const columns: ColumnsType<Role> = [
    {
      title: "Name",
      key: "name",
      dataIndex: "name",
    },
    {
      key: "action",
      width: "25%",
      render: (_, role) => (
        <Space>
          <Button
            type="link"
            icon={<EditFilled />}
            onClick={() => onUpdateRole(role)}
          >
            Edit
          </Button>
          <Popconfirm
            title="Delete Role"
            description="Are you sure you want to delete this role?"
            icon={<DeleteFilled />}
            okText="Delete"
            okButtonProps={{ danger: true }}
            onConfirm={() => onDeleteRole(role.id)}
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
    <div id="roles-table-container">
      <div
        id="roles-controls-container"
        className="flex justify-between items-center"
      >
        <Button
          className="my-2"
          type="primary"
          icon={<PlusCircleFilled />}
          onClick={onAddRole}
        >
          Add Role
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
        dataSource={roles}
        pagination={pagination}
        onChange={onTableChange}
      />
    </div>
  );
};

export default RolesTable;
