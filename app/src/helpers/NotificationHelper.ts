import { notification } from "antd";

export default {
  success(description: string, message = "Success", duration = 3) {
    notification.success({
      message,
      description,
      duration,
    });
  },
};
