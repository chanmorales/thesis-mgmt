import React, { useState } from "react";
import DegreesTable from "../components/features/degrees/DegreesTable";
import { Degree } from "../types/Degrees";
import NotificationHelper from "../helpers/NotificationHelper";
import DegreeService from "../services/DegreeService";
import DegreeConfigDialog from "../components/features/degrees/DegreeConfigDialog";
import CommonHelper from "../helpers/CommonHelper";
import { RequestException } from "../types/Common";

const Degrees: React.FC = () => {
  const [activeDegree, setActiveDegree] = useState<Degree>();
  const [isDegreeConfigDialogOpen, setIsDegreeConfigDialogOpen] =
    useState(false);
  const [refetchData, setRefetchData] = useState(false);
  const [formError, setFormError] = useState<RequestException>();

  const onAddDegree = () => {
    setActiveDegree(undefined);
    setIsDegreeConfigDialogOpen(true);
  };

  const onUpdateDegree = (degree: Degree) => {
    setActiveDegree(degree);
    setIsDegreeConfigDialogOpen(true);
  };

  const onDeleteDegree = async (degreeId: number) => {
    try {
      await DegreeService.deleteDegree(degreeId);
      NotificationHelper.success("Degree successfully deleted.");
    } finally {
      setRefetchData(true);
    }
  };

  const onCancel = () => {
    setIsDegreeConfigDialogOpen(false);
  };

  const onSubmit = async (degree: Degree, degreeId: number) => {
    try {
      setFormError(undefined);
      const result =
        degreeId === -1
          ? await DegreeService.createDegree(degree)
          : await DegreeService.updateDegree(degreeId, degree);
      NotificationHelper.success(
        `Degree successfully ${degreeId === -1 ? "created" : "updated"}.`
      );
      setIsDegreeConfigDialogOpen(false);
      setRefetchData(true);
      return result;
    } catch (ex) {
      if (typeof ex === "string" && CommonHelper.isJsonParseable(ex)) {
        const errorDetails: RequestException = JSON.parse(ex);
        if (errorDetails.field) {
          setFormError(errorDetails);
        } else {
          NotificationHelper.error(errorDetails.message);
        }
      } else {
        console.error(ex);
      }
    }
  };

  return (
    <div id="degrees-container" className="h-full">
      <DegreesTable
        refetchData={refetchData}
        setRefetchData={setRefetchData}
        onAddDegree={onAddDegree}
        onUpdateDegree={onUpdateDegree}
        onDeleteDegree={onDeleteDegree}
      />
      <DegreeConfigDialog
        open={isDegreeConfigDialogOpen}
        activeDegree={activeDegree}
        onCancel={onCancel}
        onSubmit={onSubmit}
        formError={formError}
      />
    </div>
  );
};

export default Degrees;
