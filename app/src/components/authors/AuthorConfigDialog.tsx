import React, { useEffect, useState } from "react";
import { Form, Input, Modal } from "antd";
import { Author } from "../../types/Authors";
import { useForm } from "antd/es/form/Form";

interface AuthorConfigDialogProps {
  open: boolean;
  activeAuthor?: Author;
  onCancel: () => void;
  onSubmit: (author: Author, authorId: number) => Promise<Author | void>;
}

const AuthorConfigDialog: React.FC<AuthorConfigDialogProps> = ({
  open,
  activeAuthor,
  onCancel,
  onSubmit,
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
      lastName: activeAuthor?.lastName ?? "",
      firstName: activeAuthor?.firstName ?? "",
      middleName: activeAuthor?.middleName ?? "",
    });
  }, [form, activeAuthor]);

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

  const handleSubmit = async () => {
    const values = await form.validateFields();
    await onSubmit(
      {
        ...values,
      },
      activeAuthor ? activeAuthor.id : -1
    );
  };

  return (
    <Modal
      open={open}
      title={activeAuthor ? "Update Author" : "Create New Author"}
      okText={activeAuthor ? "Update" : "Create"}
      onOk={handleSubmit}
      onCancel={onCancel}
      okButtonProps={{ disabled: !submittable }}
    >
      <Form
        layout="horizontal"
        labelCol={{ span: 6 }}
        wrapperCol={{ span: 14 }}
        form={form}
        name="authorConfig"
      >
        <Form.Item
          name="lastName"
          label="Last Name"
          required
          rules={[{ required: true, message: "Last name is required." }]}
        >
          <Input />
        </Form.Item>
        <Form.Item
          name="firstName"
          label="First Name"
          required
          rules={[{ required: true, message: "First name is required." }]}
        >
          <Input />
        </Form.Item>
        <Form.Item name="middleName" label="Middle Name">
          <Input />
        </Form.Item>
      </Form>
    </Modal>
  );
};

export default AuthorConfigDialog;
