import React from "react";
import { Menu, MenuProps } from "antd";
import {
  FileTextOutlined,
  TrophyOutlined,
  UsergroupAddOutlined,
  UserOutlined,
} from "@ant-design/icons";

interface SideMenuProps {
  collapsed: boolean;
}

const SideMenu: React.FC<SideMenuProps> = ({ collapsed }) => {
  const items: MenuProps["items"] = [
    { label: "Theses", key: "theses", icon: <FileTextOutlined /> },
    { type: "divider" },
    {
      label: collapsed ? (
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
          label: "Degree",
          key: "degree",
          icon: <TrophyOutlined />,
        },
        {
          label: "Role",
          key: "role",
          icon: <UsergroupAddOutlined />,
        },
      ],
    },
  ];

  const onClick: MenuProps["onClick"] = (e) => {
    console.log("click", e);
  };

  return <Menu theme="dark" onClick={onClick} items={items}></Menu>;
};

export default SideMenu;
