import { PaginatedData } from "../types/Common";
import { Degree } from "../types/Degrees";
import RequestHelper from "../helpers/RequestHelper";
import qs from "qs";
import { RequestMethod } from "../common/constants";

class DegreeService {
  resourcePath = "degrees";

  async getDegrees(
    page: number,
    pageSize: number,
    query: string
  ): Promise<PaginatedData<Degree>> {
    const queryParams = {
      page: page,
      pageSize: pageSize,
      query: query,
    };
    return await RequestHelper.doRemoteCallAsync(
      `${this.resourcePath}?${qs.stringify(queryParams)}`,
      "get degrees"
    );
  }

  async createDegree(degree: Degree): Promise<Degree> {
    return await RequestHelper.doRemoteCallAsync(
      this.resourcePath,
      "create degree",
      RequestMethod.POST,
      degree
    );
  }

  async updateDegree(degreeId: number, degree: Degree): Promise<void> {
    return await RequestHelper.doRemoteCallAsync(
      `${this.resourcePath}/${degreeId}`,
      "update degree",
      RequestMethod.PUT,
      degree
    );
  }

  async deleteDegree(degreeId: number): Promise<void> {
    return await RequestHelper.doRemoteCallAsync(
      `${this.resourcePath}/${degreeId}`,
      "delete degree",
      RequestMethod.DELETE
    );
  }
}

export default new DegreeService();
