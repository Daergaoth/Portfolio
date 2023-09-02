import { Component, OnInit, Input } from '@angular/core';
import { LayoutService } from 'src/app/services/layout.service';
import { InnerService } from 'src/app/services/inner.service';

@Component({
  selector: 'app-progress-slider',
  templateUrl: './progress-slider.component.html',
  styleUrls: ['./progress-slider.component.css']
})
export class ProgressSliderComponent implements OnInit {

  showProgress = false;
  timeout: any;
  progressTimer: any;
  currentProgress = 0;
  savedProgress = 0;
  @Input()videoLength = 0;

  constructor(private inner: InnerService,private layoutService: LayoutService) { }

  ngOnInit(): void {
    this.layoutService.eventHappend.subscribe(
      () => {
        this.pauseShowingTimer();
        this.showProgress = true;
        this.startShowingTimer();
      }
    );

    this.inner.videoProgress.subscribe(
      (value) => {
        this.pauseTimer();
        this.currentProgress = value / this.videoLength * 100;
        this.savedProgress = value / this.videoLength * 100;
        this.startTimer();
      }
    );
  }

  pauseTimer(): void {
    clearInterval(this.progressTimer);
  }

  startTimer(): void {
    this.progressTimer = setInterval(() => {
      this.inner.checkCurrentTime.next();
    }, 200);
  }

  pauseShowingTimer(): void {
    clearInterval(this.timeout);
  }

  startShowingTimer(): void {
    this.timeout = setInterval(() => {
      this.showProgress = false;
    }, 5000);
  }

  changeSliderValue(event: any): void {
    const changeValue = ((event.value - this.currentProgress) / 100) * this.videoLength;
    this.layoutService.changeCurrentTime.next(changeValue);
  }

}
