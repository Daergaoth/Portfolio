import {PlaylistModel} from "./playlistmodel";

export interface MainListElementModel {
  playlistModel: PlaylistModel;
  resetButtonStyle: {
    color: string
  }
}
