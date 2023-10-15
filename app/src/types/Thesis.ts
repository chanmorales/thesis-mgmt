import { Degree } from "./Degree";

export interface Thesis {
  id: number;
  title: string;
  degree: Degree;
  dateSubmitted: YearMonth;
}

export interface YearMonth {
  year: number;
  month: number;
}
