import { PaginatedData } from "../types/Common";
import { Thesis } from "../types/Thesis";
import RequestHelper from "../helpers/RequestHelper";
import qs from "qs";
import { RequestMethod } from "../common/constants";

class ThesisService {
  resourcePath = "theses";

  async getTheses(
    page: number,
    pageSize: number,
    query: string
  ): Promise<PaginatedData<Thesis>> {
    const queryParams = {
      page: page,
      pageSize: pageSize,
      query: query,
    };
    return await RequestHelper.doRemoteCallAsync(
      `${this.resourcePath}?${qs.stringify(queryParams)}`,
      "get theses"
    );
  }

  async createThesis(thesis: Thesis): Promise<Thesis> {
    return await RequestHelper.doRemoteCallAsync(
      this.resourcePath,
      "create thesis",
      RequestMethod.POST,
      thesis
    );
  }

  async updateThesis(thesisId: number, thesis: Thesis): Promise<void> {
    return await RequestHelper.doRemoteCallAsync(
      `${this.resourcePath}/${thesisId}`,
      "update thesis",
      RequestMethod.PUT,
      thesis
    );
  }

  async deleteThesis(thesisId: number): Promise<void> {
    return await RequestHelper.doRemoteCallAsync(
      `${this.resourcePath}/${thesisId}`,
      "delete thesis",
      RequestMethod.DELETE
    );
  }
}

export default new ThesisService();
