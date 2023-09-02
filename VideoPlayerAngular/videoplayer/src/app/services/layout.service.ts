import { Injectable } from '@angular/core';
import { BehaviorSubject, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LayoutService {

  isPlaying = new BehaviorSubject<boolean>(true);
  playOther = new Subject<boolean>();
  changeCurrentTime = new Subject<number>();
  fullScreen = new Subject<void>();
  home = new Subject<void>();
  subtitle = new Subject<boolean>();
  subtitleBack = new Subject<boolean>();
  volume = new Subject<boolean>();
  fullScreenBack = new Subject<void>();
  isPlayingBack = new Subject<boolean>();
  eventHappend = new Subject<void>();

  constructor() { }
}
