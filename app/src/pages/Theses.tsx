import React, { useState } from "react";
import ThesesTable from "../components/features/theses/ThesesTable";
import { Thesis } from "../types/Thesis";
import ThesisConfigDialog from "../components/features/theses/ThesisConfigDialog";
import { RequestException } from "../types/Common";
import ThesisService from "../services/ThesisService";
import NotificationHelper from "../helpers/NotificationHelper";
import CommonHelper from "../helpers/CommonHelper";

const Theses: React.FC = () => {
  const [activeThesis, setActiveThesis] = useState<Thesis>();
  const [isThesisConfigDialogOpen, setIsOpenConfigDialogOpen] = useState(false);
  const [refetchData, setRefetchData] = useState(false);

  const onAddThesis = () => {
    setActiveThesis(undefined);
    setIsOpenConfigDialogOpen(true);
  };

  const onUpdateThesis = (thesis: Thesis) => {
    setActiveThesis(thesis);
    setIsOpenConfigDialogOpen(true);
  };

  const onDeleteThesis = async (thesisId: number) => {
    try {
      await ThesisService.deleteThesis(thesisId);
      NotificationHelper.success("Thesis successfully deleted.");
    } finally {
      setRefetchData(true);
    }
  };

  const onCancel = () => {
    setActiveThesis(undefined);
    setIsOpenConfigDialogOpen(false);
  };

  const onSubmit = async (thesis: Thesis, thesisId: number) => {
    try {
      const result =
        thesisId === -1
          ? await ThesisService.createThesis(thesis)
          : await ThesisService.updateThesis(thesisId, thesis);
      NotificationHelper.success(
        `Thesis successfully ${thesisId === -1 ? "created" : "updated"}.`
      );
      setIsOpenConfigDialogOpen(false);
      setRefetchData(true);
      return result;
    } catch (ex) {
      if (typeof ex === "string" && CommonHelper.isJsonParseable(ex)) {
        const errorDetails: RequestException = JSON.parse(ex);
        NotificationHelper.error(errorDetails.message);
      } else {
        console.error(ex);
      }
    }
  };

  return (
    <div id="thesis-container" className="h-full">
      <ThesesTable
        refetchData={refetchData}
        setRefetchData={setRefetchData}
        onAddThesis={onAddThesis}
        onUpdateThesis={onUpdateThesis}
        onDeleteThesis={onDeleteThesis}
      />
      <ThesisConfigDialog
        open={isThesisConfigDialogOpen}
        activeThesis={activeThesis}
        onCancel={onCancel}
        onSubmit={onSubmit}
      />
    </div>
  );
};

export default Theses;
