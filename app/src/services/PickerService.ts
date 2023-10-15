import { Degree } from "../types/Degree";
import RequestHelper from "../helpers/RequestHelper";

class PickerService {
  resourcePath = "picker";

  async getDegrees(): Promise<Degree[]> {
    return await RequestHelper.doRemoteCallAsync(
      `${this.resourcePath}/degrees`,
      "get degrees"
    );
  }
}

export default new PickerService();
