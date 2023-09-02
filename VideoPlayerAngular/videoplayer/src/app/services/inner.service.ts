import { Injectable } from '@angular/core';
import { BehaviorSubject, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class InnerService {

  constructor() { }

  isPlaying = new BehaviorSubject<boolean>(false);
  isMouseOver = new BehaviorSubject<boolean>(false);
  checkCurrentTime = new BehaviorSubject<void>(undefined);
  videoProgress = new BehaviorSubject<number>(0);
  videoLength = new BehaviorSubject<number>(0);
}
