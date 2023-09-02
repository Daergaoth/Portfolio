import { VideoMainListModel } from './VideoMainListModel';

export interface FolderModel {
    id: number;

    name: string;

    path: string;

    videos: Array<VideoMainListModel>;
}
