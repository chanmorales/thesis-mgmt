import React, { useEffect, useState } from "react";
import { Degree } from "../../../types/Degree";
import { useForm } from "antd/es/form/Form";
import { Form, Input, Modal } from "antd";
import { RequestException } from "../../../types/Common";

interface DegreeConfigDialogProps {
  open: boolean;
  activeDegree?: Degree;
  onCancel: () => void;
  onSubmit: (degree: Degree, degreeId: number) => Promise<Degree | void>;
  formError?: RequestException;
}

const DegreeConfigDialog: React.FC<DegreeConfigDialogProps> = ({
  open,
  activeDegree,
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
      code: activeDegree?.code ?? "",
      name: activeDegree?.name ?? "",
    });
  }, [form, activeDegree]);

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
    await onSubmit({ ...values }, activeDegree?.id ?? -1);
  };

  return (
    <Modal
      open={open}
      title={activeDegree ? "Update Degree" : "Create New Degree"}
      okText={activeDegree ? "Update" : "Create"}
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
        name="degreeConfig"
      >
        <Form.Item
          name="code"
          label="Code"
          required
          rules={[{ required: true, message: "Degree code is required." }]}
        >
          <Input />
        </Form.Item>
        <Form.Item
          name="name"
          label="Name"
          required
          rules={[{ required: true, message: "Degree name is required." }]}
        >
          <Input />
        </Form.Item>
      </Form>
    </Modal>
  );
};

export default DegreeConfigDialog;
