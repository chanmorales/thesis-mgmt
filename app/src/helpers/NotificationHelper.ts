import { notification } from "antd";

export default {
  success(description: string, message = "Success", duration = 3) {
    notification.success({
      message,
      description,
      duration,
    });
  },
  error(description: string, message = "Error", duration = 0) {
    notification.error({
      message,
      description,
      duration,
    });
  },
};
