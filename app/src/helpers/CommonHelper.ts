import { YearMonth } from "../types/Thesis";

export default {
  /**
   * Checks if data is JSON parseable
   *
   * @param data the data to be checked
   * @return <b>true</b> if data is JSON parseable, otherwise, <b>false</b>
   */
  isJsonParseable(data: string): boolean {
    try {
      JSON.parse(data);
      return true;
    } catch (e) {
      return false;
    }
  },
  /**
   * Format's date submitted (year month) to YYYY/MM
   *
   * @param dateSubmitted the thesis date submitted
   */
  formatDateSubmitted(dateSubmitted: YearMonth): string {
    return `${dateSubmitted.year}-${dateSubmitted.month}`;
  },
};
