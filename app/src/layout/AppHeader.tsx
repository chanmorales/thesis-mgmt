import React from "react";
import { Button, Layout } from "antd";
import { MenuFoldOutlined, MenuUnfoldOutlined } from "@ant-design/icons";

const { Header } = Layout;

interface AppHeaderProps {
  siderCollapsed: boolean;
  setSiderCollapsed: (value: boolean) => void;
}

const AppHeader: React.FC<AppHeaderProps> = ({
  siderCollapsed,
  setSiderCollapsed,
}) => {
  return (
    <Header
      className="p-0 text-white font-bold"
      style={{ background: "#001529" }}
    >
      <Button
        className="text-base mx-[25px]"
        type="text"
        icon={
          siderCollapsed ? (
            <MenuFoldOutlined className="text-white" />
          ) : (
            <MenuUnfoldOutlined className="text-white" />
          )
        }
        onClick={() => setSiderCollapsed(!siderCollapsed)}
      />
      Thesis Information Management System
    </Header>
  );
};

export default AppHeader;
