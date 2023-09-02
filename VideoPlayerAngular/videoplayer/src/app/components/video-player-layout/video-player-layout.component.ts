import { Component, OnInit, Input, ElementRef, ViewChild } from '@angular/core';
import { LayoutService } from 'src/app/services/layout.service';

@Component({
  selector: 'app-video-player-layout',
  templateUrl: './video-player-layout.component.html',
  styleUrls: ['./video-player-layout.component.css']
})
export class VideoPlayerLayoutComponent implements OnInit {
  @Input() title = 'No Title';
  @Input() videoLength = 0;
  /*buttonText = 'pause';*/
  fullScrrenButtonText = 'fullscreen';
  @Input() fullscreenState = false;
  isPlaying = true;
  isSubtitle = false;
  @Input() volume = 0;
  isMuted = false;
  showSliderVariable = false;
  timer: any;
  layoutContainer: any;
  layoutContainerStyle = {
    color: 'transparent',
    cursor: 'none'
  };
  /*subtitleText = 'subtitles';*/


  constructor(private layoutService: LayoutService) { }

  ngOnInit(): void {
    this.layoutService.isPlayingBack.subscribe(
      (value) => {
        this.isPlaying = value;
      }
    );

    this.layoutService.fullScreenBack.subscribe(
      () => {
        this.fullscreenState = false;
      }
    );

    this.layoutService.subtitleBack.subscribe(
      (value) => {
        this.isSubtitle = value;
      }
    );

    this.layoutContainer = document.getElementById('layoutContainer');
    this.layoutContainer.onmousemove = () => {
      this.layoutContainerStyle = {
        color: 'red',
        cursor: 'default'
      };
      clearTimeout(this.timer);
      this.timer = setTimeout(() => {
        this.layoutContainerStyle = {
          color: 'transparent',
          cursor: 'none'
        };
      }, 2000);
    };
  }

  subtitle(): void {
    this.isSubtitle = !this.isSubtitle;
    this.layoutService.subtitle.next(this.isSubtitle);
  }

  changeVolume(): void {
    this.layoutService.volume.next(false);
  }

  playPrevious(): void {
    this.layoutService.playOther.next(false);
  }

  oneAndHalfEarlier(): void {
    this.layoutService.changeCurrentTime.next(-90);
  }

  playButton(): void {
    this.isPlaying = !this.isPlaying;
    this.layoutService.isPlaying.next(this.isPlaying);
  }

  oneAndHalfLater(): void {
    this.layoutService.changeCurrentTime.next(90);
  }

  playNext(): void {
    this.layoutService.playOther.next(true);
  }

  home(): void {
    this.layoutService.home.next();
  }

  fullscrren(): void {
    this.layoutService.fullScreen.next();
  }

  fiveSecEarlier(): void {
    this.layoutService.changeCurrentTime.next(-5);
  }

  fiveSecLater(): void {
    this.layoutService.changeCurrentTime.next(5);
  }

  showSlider(): void {
    this.showSliderVariable = true;
    this.layoutService.eventHappend.next();
    this.timer = setTimeout(
      () => {
        this.showSliderVariable = false;
        clearInterval(this.timer); }, 5000);
  }
}
