import { FillerElementModel } from './fillerelementmodel';

export interface PlaylistModel {

    name: string;

    id: number;

    listOfAllVideo: Array<number>;

    listOfFillerVideo: Array<number>;

    listOfFillerElements: Array<FillerElementModel>;

    lastViewedVideo: number;

    lastVideoViewedUntil: number;
}
