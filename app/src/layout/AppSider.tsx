import React from "react";
import { Layout, Menu, MenuProps } from "antd";
import {
  FileTextOutlined,
  TrophyOutlined,
  UsergroupAddOutlined,
  UserOutlined,
} from "@ant-design/icons";
import { useNavigate } from "react-router-dom";

interface AppSiderProps {
  siderCollapsed: boolean;
  setActiveMenuKey: (key: string) => void;
  defaultSelectedKeys: string[];
}

const { Sider } = Layout;

const AppSider: React.FC<AppSiderProps> = ({
  siderCollapsed,
  setActiveMenuKey,
  defaultSelectedKeys,
}) => {
  const navigate = useNavigate();

  const items: MenuProps["items"] = [
    { label: "Theses", key: "theses", icon: <FileTextOutlined /> },
    { type: "divider" },
    {
      label: siderCollapsed ? (
        ""
      ) : (
        <span className="text-gray-400 text-xs">ADMINISTRATION</span>
      ),
      key: "administration",
      type: "group",
      children: [
        {
          label: "Authors",
          key: "authors",
          icon: <UserOutlined />,
        },
        {
          label: "Degrees",
          key: "degrees",
          icon: <TrophyOutlined />,
        },
        {
          label: "Roles",
          key: "roles",
          icon: <UsergroupAddOutlined />,
        },
      ],
    },
  ];

  const onMenuSelect: MenuProps["onClick"] = (e) => {
    setActiveMenuKey(e.key);
    if (e.key === "theses") {
      navigate("/");
    } else {
      navigate(`/${e.key}`);
    }
  };

  return (
    <Sider trigger={null} collapsible collapsed={siderCollapsed}>
      <Menu
        theme="dark"
        onClick={onMenuSelect}
        items={items}
        defaultSelectedKeys={defaultSelectedKeys}
      />
    </Sider>
  );
};

export default AppSider;
