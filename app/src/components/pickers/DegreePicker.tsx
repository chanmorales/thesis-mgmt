import React, { useEffect, useState } from "react";
import Picker from "../common/Picker";
import { Degree } from "../../types/Degree";
import PickerService from "../../services/PickerService";

interface DegreePickerProps {
  onChange: (value: number) => void;
  selectedDegree?: Degree;
}

const DegreePicker: React.FC<DegreePickerProps> = ({
  onChange,
  selectedDegree,
}) => {
  const [degreeOptions, setDegreeOptions] = useState<Degree[]>([]);

  useEffect(() => {
    const fetchDegrees = async () => {
      const degrees = await PickerService.getDegrees();
      setDegreeOptions(degrees);
    };
    fetchDegrees().catch(console.error);
  }, []);

  return (
    <Picker
      options={degreeOptions.map((degree) => ({
        value: degree.id,
        label: (
          <span>
            <b>{degree.code}</b> {degree.name}
          </span>
        ),
        text: `${degree.code} ${degree.name}`,
      }))}
      onChange={onChange}
      defaultValue={selectedDegree?.id}
    />
  );
};

export default DegreePicker;
