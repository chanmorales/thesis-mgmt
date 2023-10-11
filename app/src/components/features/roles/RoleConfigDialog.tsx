import React, { useEffect, useState } from "react";
import { Form, Input, Modal } from "antd";
import { Role } from "../../../types/Roles";
import { useForm } from "antd/es/form/Form";
import { RequestException } from "../../../types/Common";

interface RoleConfigDialogProps {
  open: boolean;
  activeRole?: Role;
  onCancel: () => void;
  onSubmit: (role: Role, roleId: number) => Promise<Role | void>;
  formError?: RequestException;
}

const RoleConfigDialog: React.FC<RoleConfigDialogProps> = ({
  open,
  activeRole,
  onCancel,
  onSubmit,
  formError,
}) => {
  const [submittable, setSubmittable] = useState(false);
  const [form] = useForm();
  const values = Form.useWatch([], form);

  useEffect(() => {
    if (!open) {
      form.resetFields();
    }
  }, [form, open]);

  useEffect(() => {
    form.setFieldsValue({
      name: activeRole?.name ?? "",
    });
  }, [form, activeRole]);

  useEffect(() => {
    form.validateFields({ validateOnly: true }).then(
      () => {
        setSubmittable(true);
      },
      () => {
        setSubmittable(false);
      }
    );
  }, [form, values]);

  useEffect(() => {
    if (formError && formError.field) {
      form.setFields([
        {
          name: formError.field,
          errors: [formError.message],
        },
      ]);
    }
  }, [formError, form]);

  const handleSubmit = async () => {
    const values = await form.validateFields();
    await onSubmit(
      {
        ...values,
      },
      activeRole?.id ?? -1
    );
  };

  return (
    <Modal
      open={open}
      title={activeRole ? "Update Role" : "Create New Role"}
      okText={activeRole ? "Update" : "Create"}
      onOk={handleSubmit}
      onCancel={onCancel}
      okButtonProps={{ disabled: !submittable }}
    >
      <Form
        className="mt-6"
        layout="horizontal"
        labelCol={{ span: 6 }}
        wrapperCol={{ span: 14 }}
        form={form}
        name="roleConfig"
      >
        <Form.Item
          name="name"
          label="Name"
          required
          rules={[{ required: true, message: "Role name is required." }]}
        >
          <Input />
        </Form.Item>
      </Form>
    </Modal>
  );
};

export default RoleConfigDialog;
