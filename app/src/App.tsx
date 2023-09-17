import React from "react";
import { ConfigProvider } from "antd";
import AppLayout from "./layout/AppLayout";
import { BrowserRouter as Router } from "react-router-dom";

const App: React.FC = () => {
  return (
    <ConfigProvider>
      <Router>
        <AppLayout />
      </Router>
    </ConfigProvider>
  );
};

export default App;
