import React, { useCallback, useEffect, useState } from "react";
import { DatePicker, Form, Modal } from "antd";
import { Thesis } from "../../../types/Thesis";
import { RequestException } from "../../../types/Common";
import { useForm } from "antd/es/form/Form";
import { Input } from "antd/lib";
import DegreePicker from "../../pickers/DegreePicker";
import dayjs from "dayjs";
import CommonHelper from "../../../helpers/CommonHelper";

const { TextArea } = Input;

const monthFormat = "YYYY-MM";

interface ThesisConfigDialogProps {
  open: boolean;
  activeThesis?: Thesis;
  onCancel: () => void;
  onSubmit: (thesis: Thesis, thesisId: number) => Promise<Thesis | void>;
  formError?: RequestException;
}

const ThesisConfigDialog: React.FC<ThesisConfigDialogProps> = ({
  open,
  activeThesis,
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
      title: activeThesis?.title ?? "",
      degree: activeThesis?.degree,
      dateSubmitted: activeThesis?.dateSubmitted
        ? dayjs(CommonHelper.formatDateSubmitted(activeThesis.dateSubmitted))
        : dayjs(),
    });
  }, [form, activeThesis]);

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
        dateSubmitted: {
          year: values.dateSubmitted.$y,
          month: values.dateSubmitted.$M + 1,
        },
      },
      activeThesis?.id ?? -1
    );
  };

  const handleDegreeChange = useCallback(
    (value: number) => {
      form.setFieldsValue({
        ...values,
        degree: {
          id: value,
        },
      });
    },
    [form, values]
  );

  return (
    <Modal
      open={open}
      title={activeThesis ? "Update Thesis" : "Create New Thesis"}
      okText={activeThesis ? "Update" : "Create"}
      onOk={handleSubmit}
      onCancel={onCancel}
      okButtonProps={{ disabled: !submittable }}
      width="600px"
      destroyOnClose
    >
      <Form
        className="mt-6"
        layout="horizontal"
        labelCol={{ span: 6 }}
        wrapperCol={{ span: 14 }}
        form={form}
        name="thesisConfig"
      >
        <Form.Item
          name="title"
          label="Title"
          required
          rules={[{ required: true, message: "Thesis title is required." }]}
        >
          <TextArea />
        </Form.Item>
        <Form.Item
          name="degree"
          label="Degree"
          required
          rules={[{ required: true, message: "Degree is required." }]}
        >
          <DegreePicker
            onChange={handleDegreeChange}
            selectedDegree={activeThesis?.degree}
          />
        </Form.Item>
        <Form.Item
          name="dateSubmitted"
          label="Date Submitted"
          required
          rules={[{ required: true, message: "Date submitted is required." }]}
        >
          <DatePicker
            picker="month"
            className="w-[322px]"
            format={monthFormat}
            allowClear={false}
          />
        </Form.Item>
      </Form>
    </Modal>
  );
};

export default ThesisConfigDialog;
