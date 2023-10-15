import { PaginatedData } from "../types/Common";
import { Role } from "../types/Role";
import RequestHelper from "../helpers/RequestHelper";
import qs from "qs";
import { RequestMethod } from "../common/constants";

class RoleService {
  resourcePath = "roles";

  async getRoles(
    page: number,
    pageSize: number,
    query: string
  ): Promise<PaginatedData<Role>> {
    const queryParams = {
      page: page,
      pageSize: pageSize,
      query: query,
    };
    return await RequestHelper.doRemoteCallAsync(
      `${this.resourcePath}?${qs.stringify(queryParams)}`,
      "get roles"
    );
  }

  async createRole(role: Role): Promise<Role> {
    return await RequestHelper.doRemoteCallAsync(
      this.resourcePath,
      "create role",
      RequestMethod.POST,
      role
    );
  }

  async updateRole(roleId: number, role: Role): Promise<void> {
    return await RequestHelper.doRemoteCallAsync(
      `${this.resourcePath}/${roleId}`,
      "update role",
      RequestMethod.PUT,
      role
    );
  }

  async deleteRole(roleId: number): Promise<void> {
    return await RequestHelper.doRemoteCallAsync(
      `${this.resourcePath}/${roleId}`,
      "delete role",
      RequestMethod.DELETE
    );
  }
}

export default new RoleService();
