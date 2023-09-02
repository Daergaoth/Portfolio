import { SubtitleElementModel } from './subtitleElementModel';

export interface SubtitleModel{
  title: string;
  language: string;
  elements: Array<SubtitleElementModel>;
}
