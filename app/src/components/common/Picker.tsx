import React from "react";
import { Select, SelectProps } from "antd";

const Picker = ({ ...rest }: SelectProps) => {
  return (
    <Select
      showSearch
      filterOption={(input, option) =>
        (option?.text ?? "").toLowerCase().includes(input.toLowerCase())
      }
      {...rest}
    />
  );
};

export default Picker;
