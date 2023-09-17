import React, { useEffect, useState } from "react";
import { Layout } from "antd";
import AppSider from "./AppSider";
import { useLocation, useNavigate } from "react-router-dom";
import AppHeader from "./AppHeader";

const { Content } = Layout;

const AppLayout: React.FC = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const [activeMenuKey, setActiveMenuKey] = useState<string>();
  const [siderCollapsed, setSiderCollapsed] = useState(false);

  useEffect(() => {
    navigate(location.pathname);
    const menu = location.pathname.startsWith("/")
      ? location.pathname.slice(1)
      : location.pathname;
    if (menu) {
      setActiveMenuKey(menu);
    } else {
      setActiveMenuKey("theses");
    }
  }, []);

  return (
    <Layout>
      {activeMenuKey && (
        <>
          <AppHeader
            siderCollapsed={siderCollapsed}
            setSiderCollapsed={setSiderCollapsed}
          />
          <Layout>
            <AppSider
              siderCollapsed={siderCollapsed}
              setActiveMenuKey={(key) => setActiveMenuKey(key)}
              defaultSelectedKeys={[activeMenuKey]}
            />
            <Content
              className="p-2.5"
              style={{ minHeight: "calc(100vh - 64px)" }}
            >
              Content
            </Content>
          </Layout>
        </>
      )}
    </Layout>
  );
};

export default AppLayout;
