import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SubtitleModel } from '../models/subtitleModel';
import { MainListModel } from '../models/mainListModel';
import { FolderModel } from '../models/FolderModel';
import { PlaylistModel } from '../models/playlistmodel';

@Injectable({
  providedIn: 'root'
})
export class HttpServiceService {

 baseUrl = environment.BASE_URL;

  constructor(private http: HttpClient) {}

  getSubtitle(id: number): Observable<SubtitleModel> {

    const url = this.baseUrl + '/video/subtitles/' + id;
    return this.http.get<SubtitleModel>(url);
  }

  getMainList(): Observable<MainListModel> {
    return this.http.get<MainListModel>(this.baseUrl + '/api/folders/all');
  }

  getFolder(id: number): Observable<FolderModel> {
    return this.http.get<FolderModel>(this.baseUrl + '/api/folders/' + id);
  }

  refreshDatabase(): Observable<void> {
    return this.http.get<void>(this.baseUrl + '/api/main/');
  }

  getWatchedTime(id: number): Observable<number> {
    return this.http.get<number>(this.baseUrl + '/video/watched/' + id);
  }

  setWatchedTime(id: number, value: number): Observable<void> {
    return this.http.get<void>(this.baseUrl + '/video/watched/' + id + '/' + value);
  }

  getAllPlaylist(): Observable<Array<PlaylistModel>> {
    return this.http.get<Array<PlaylistModel>>(this.baseUrl + '/api/folders/playlist');
  }

  shutdownServer(): Observable<void> {
    return this.http.get<void>(this.baseUrl + '/api/shutdown');
  }

  updatePlaylist(id: number, videoID: number): Observable<void> {
    return this.http.get<void>(this.baseUrl + '/api/folders/playlistUpdateState/' + id + '/' + videoID);
  }

  getVTTFile(videoID: number): any {
    let response = this.http.get(this.baseUrl + '/video/subtitlesFile/26', {responseType: 'blob'});

    console.log(response);
    return response;
  }

  resetPlaylist(id: number): Observable<{}> {
    return this.http.get<{}>(this.baseUrl + '/api/folders/playlistReset/' + id,{observe : 'response'}).pipe();
  }
}
