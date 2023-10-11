import React, { useState } from "react";
import RolesTable from "../components/features/roles/RolesTable";
import { Role } from "../types/Roles";
import RoleConfigDialog from "../components/features/roles/RoleConfigDialog";
import RoleService from "../services/RoleService";
import NotificationHelper from "../helpers/NotificationHelper";
import CommonHelper from "../helpers/CommonHelper";
import { RequestException } from "../types/Common";

const Roles: React.FC = () => {
  const [activeRole, setActiveRole] = useState<Role>();
  const [isRoleConfigDialogOpen, setIsRoleConfigDialogOpen] = useState(false);
  const [refetchData, setRefetchData] = useState(false);
  const [formError, setFormError] = useState<RequestException>();

  const onAddRole = () => {
    setActiveRole(undefined);
    setIsRoleConfigDialogOpen(true);
  };

  const onUpdateRole = (role: Role) => {
    setActiveRole(role);
    setIsRoleConfigDialogOpen(true);
  };

  const onCancel = () => {
    setIsRoleConfigDialogOpen(false);
  };

  const onSubmit = async (role: Role, roleId: number) => {
    try {
      setFormError(undefined);
      const result =
        roleId === -1
          ? await RoleService.createRole(role)
          : await RoleService.updateRole(roleId, role);
      NotificationHelper.success(
        `Author successfully ${roleId === -1 ? "created" : "updated"}.`
      );
      setIsRoleConfigDialogOpen(false);
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

  const onDeleteRole = async (roleId: number) => {
    try {
      await RoleService.deleteRole(roleId);
      NotificationHelper.success("Role successfully deleted.");
    } finally {
      setRefetchData(true);
    }
  };

  return (
    <div id="roles-container" className="h-full">
      <RolesTable
        refetchData={refetchData}
        setRefetchData={setRefetchData}
        onAddRole={onAddRole}
        onUpdateRole={onUpdateRole}
        onDeleteRole={onDeleteRole}
      />
      <RoleConfigDialog
        open={isRoleConfigDialogOpen}
        activeRole={activeRole}
        onCancel={onCancel}
        onSubmit={onSubmit}
        formError={formError}
      />
    </div>
  );
};

export default Roles;
