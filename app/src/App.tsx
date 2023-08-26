import React, { useState } from "react";
import { Button, ConfigProvider, Layout, theme } from "antd";
import { MenuFoldOutlined, MenuUnfoldOutlined } from "@ant-design/icons";
import SideMenu from "./component/SideMenu";

const { Sider, Header, Content } = Layout;

const App: React.FC = () => {
  const [collapsed, setCollapsed] = useState(false);
  const {
    token: { colorBgContainer },
  } = theme.useToken();

  return (
    <ConfigProvider>
      <Layout>
        <Sider trigger={null} collapsible collapsed={collapsed}>
          <Button
            className="text-base h-16 ml-1.5"
            type="text"
            icon={
              collapsed ? (
                <MenuUnfoldOutlined className="text-white" />
              ) : (
                <MenuFoldOutlined className="text-white" />
              )
            }
            onClick={() => setCollapsed(!collapsed)}
            style={{ width: "64px" }}
          />
          <SideMenu collapsed={collapsed} />
        </Sider>
        <Layout>
          <Header className="p-0" style={{ background: colorBgContainer }}>
            Header
          </Header>
          <Content className="" style={{ minHeight: "calc(100vh - 64px)" }}>
            Content
          </Content>
        </Layout>
      </Layout>
    </ConfigProvider>
  );
};

export default App;
