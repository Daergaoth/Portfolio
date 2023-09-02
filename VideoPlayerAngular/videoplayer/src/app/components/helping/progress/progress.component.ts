import { Component, OnInit, Input } from '@angular/core';
import { InnerService } from 'src/app/services/inner.service';

@Component({
  selector: 'app-progress',
  templateUrl: './progress.component.html',
  styleUrls: ['./progress.component.css']
})
export class ProgressComponent implements OnInit {

  maxLength: number = 0;
  currentValue: number = 0;
  stringValue: string = '';
  checkProgress = true;
  interval: any;
  @Input() videoLength: number = 0;

  constructor(private inner: InnerService) { }

  ngOnInit(): void {
    this.inner.videoProgress.subscribe(
      (value) => {
        this.pauseTimer();
        this.currentValue = value;
        this.stringValue = this.calculateDuration(value.toFixed(0));
        this.startTimer();
      }
    );
    this.startTimer();
  }

  pauseTimer(): void {
    clearInterval(this.interval);
  }

  startTimer(): void {
    this.interval = setInterval(() => {
      this.inner.checkCurrentTime.next();
    }, 200);
  }

  calculateDuration(duration: string): string {
    let watchTime = '';
    const alreadyWatched: number = Number(duration);
    const watchedHours: number = Math.floor(alreadyWatched / 3600);
    const watchedMinutes: number = Math.floor((alreadyWatched - (watchedHours * 3600)) / 60);
    const watchedSeconds: number = Math.floor(alreadyWatched % 60);
    if (watchedHours > 9) {
      watchTime = watchTime + watchedHours + ':';
    } else if (watchedHours === 0) {
      watchTime = watchTime + '00' + ':';
    } else {
      watchTime = watchTime + '0' + watchedHours + ':';
    }

    if (watchedMinutes > 9) {
      watchTime = watchTime + watchedMinutes + ':';
    } else if (watchedMinutes === 0) {
      watchTime = watchTime + '00' + ':';
    } else {
      watchTime = watchTime + '0' + watchedMinutes + ':';
    }

    if (watchedSeconds > 9) {
      watchTime = watchTime + watchedSeconds;
    } else if (watchedSeconds === 0) {
      watchTime = watchTime + '00';
    } else {
      watchTime = watchTime + '0' + watchedSeconds;
    }
    return watchTime;
  }

}
