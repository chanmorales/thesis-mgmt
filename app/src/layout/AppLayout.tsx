import React, { useEffect, useState } from "react";
import { Layout } from "antd";
import AppSider from "./AppSider";
import { Route, Routes, useLocation, useNavigate } from "react-router-dom";
import AppHeader from "./AppHeader";
import Theses from "../pages/Theses";
import Authors from "../pages/Authors";
import Degrees from "../pages/Degrees";
import Roles from "../pages/Roles";
import "../css/common.css";

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
  }, [location.pathname, navigate]);

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
              <Routes>
                <Route path="/" element={<Theses />} />
                <Route path="/authors" element={<Authors />} />
                <Route path="/degrees" element={<Degrees />} />
                <Route path="/roles" element={<Roles />} />
              </Routes>
            </Content>
          </Layout>
        </>
      )}
    </Layout>
  );
};

export default AppLayout;
