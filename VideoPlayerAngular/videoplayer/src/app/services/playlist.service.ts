import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { PlaylistModel } from '../models/playlistmodel';

@Injectable({
  providedIn: 'root'
})
export class PlaylistService {

  static playlist: PlaylistModel;

  constructor() { }
}
