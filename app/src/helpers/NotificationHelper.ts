import { notification } from "antd";

export default {
  success(message: string, description: string, duration = 3) {
    notification.success({
      message,
      description,
      duration,
    });
  },
};
