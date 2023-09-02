import { Component, OnInit, OnDestroy, AfterViewInit, ViewChild } from '@angular/core';
import { SubtitleModel } from 'src/app/models/subtitleModel';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpServiceService } from 'src/app/services/http-service.service';
import { InnerService } from 'src/app/services/inner.service';
import { LayoutService } from 'src/app/services/layout.service';
import { PlaylistModel } from 'src/app/models/playlistmodel';
import { PlaylistService } from 'src/app/services/playlist.service';

@Component({
  selector: 'app-playlist-player',
  templateUrl: './playlist-player.component.html',
  styleUrls: ['./playlist-player.component.css']
})
export class PlaylistPlayerComponent implements OnInit, OnDestroy, AfterViewInit {
  videoIdFromRoute: number;
  previousVideoId: number;
  autoStartVideo: boolean;
  videoURL: string;
  subtitle: SubtitleModel;
  isVideoEnded = false;
  title: string;
  textTrackCounter = 1;
  videoLength = 0;
  playing = false;
  volume = 0;
  volumeTemp = 0;
  fullscreen = false;
  playlist: PlaylistModel;
  showCase: HTMLElement;
  currentlyActiveSubtitleElements: Array<string> = [];
  layout: HTMLElement;
  timer: any;
  showSubtitle: boolean = true;
  layoutStyle = {
    color: 'transparent',
    cursor: 'none'
  };
  showcaseStyle = {
    cursor: 'default'
  };

  currentTime: number = 0;

  videoContainerStyle = {
    height: window.innerHeight.toFixed(0) + 'px',
    width: window.innerWidth.toFixed(0) + 'px'
  };

  /*videoPlayerStyle = {
    'min-height': window.innerHeight.toFixed(0) + 'px',
    'min-width': window.innerWidth.toFixed(0) + 'px'
  };*/

  videoPlayerStyle = {
    'height': '95vh',
    'width': '100vw',
    'position': 'fixed',
    'object-fit': 'cover'
  };

  constructor(private layoutService: LayoutService,
              private innerService: InnerService,
              private route: ActivatedRoute,
              private http: HttpServiceService,
              private router: Router) { }

  ngOnInit(): void {
    if (PlaylistService.playlist === null || PlaylistService.playlist === undefined) {
      this.router.navigate(["/"]);
    } else {
      this.playlist = PlaylistService.playlist;
      this.videoIdFromRoute = this.playlist.lastViewedVideo;
      this.previousVideoId = this.playlist.lastViewedVideo;
      this.autoStartVideo = true;

      const video: HTMLVideoElement = document.getElementById('videoPlayer') as HTMLVideoElement;
      this.http.getWatchedTime(this.videoIdFromRoute).subscribe((value) => {
        /*video.currentTime = Number(localStorage.getItem(this.subtitle.title));*/
        if (value === -1) {
          video.currentTime = 0;
        } else {
          video.currentTime = value;
        }});

      this.videoURL = 'http://localhost:8080/video/stream/' + this.videoIdFromRoute;
      this.innerService.isPlaying.next(true);
    }
  }

  ngOnDestroy() {
    const video: HTMLVideoElement = document.getElementById('videoPlayer') as HTMLVideoElement;
    if (!this.isVideoEnded) {
      this.http.setWatchedTime(this.videoIdFromRoute, Math.floor(video.currentTime));
    } else {
      this.http.setWatchedTime(this.videoIdFromRoute, -1);
    }
    localStorage.setItem('volume', video.volume.toFixed(2));
    this.innerService.isPlaying.next(false);
    window.location.reload();
  }

  ngAfterViewInit() {
    this.http.updatePlaylist(this.playlist.id, this.videoIdFromRoute).subscribe();
    this.videoContainerStyle = {
      height: screen.height * 0.9 + 'px',
      width: screen.width + 'px'
    };

    /*this.videoPlayerStyle = {
      'min-height': screen.height * 0.9 + 'px',
      'min-width': screen.width + 'px'
    };*/
    this.videoPlayerStyle = {
      'height': '100vh',
      'width': '100vw',
      'position': 'fixed',
      'object-fit': 'cover'
    };
    const video: HTMLVideoElement = document.getElementById('videoPlayer') as HTMLVideoElement;

    this.startVideoSetup(video);

    this.layoutService.subtitle.subscribe(
      (value) => {
        this.turnOnOffSubtitle(video, value);
      }
    );

    this.layoutService.volume.subscribe(
      () => {
        if (video.volume === 0) {
          video.volume = this.volumeTemp === 0 ? 1 : this.volumeTemp;
          this.volume = video.volume;
        } else {
          this.volumeTemp = video.volume;
          video.volume = 0;
          this.volume = video.volume;
        }
      }
    );

    this.innerService.checkCurrentTime.subscribe(
      () => {
        if (!isNaN(video.duration)) {
          this.checkcurrentSubtitle(video.currentTime);
          this.videoLength = video.duration;
          this.innerService.videoProgress.next(video.currentTime);
        }
      }
    );

    this.layoutService.isPlaying.subscribe(
      (playing) => {
        if (playing) {
          video.play();
        } else {
          video.pause();
        }
      }
    );

    this.layoutService.playOther.subscribe(
      (isItNext) => {
        this.loadOtherVideo(video, isItNext);
      }
    );

    this.layoutService.changeCurrentTime.subscribe(
      (value) => {
        this.changeCurrentTime(video, value);
      }
    );

    this.layoutService.fullScreen.subscribe(
      () => {
        this.changeFullscreenMode();
      }
    );

    this.layoutService.home.subscribe(
      () => {
        this.home(video);
      }
    );

    window.onkeyup = (gfg: { keyCode: number; }) => {
      if (gfg.keyCode === 32) {
        if (this.playing) {
          video.pause();
        } else {
          video.play();
        }
      } else if (gfg.keyCode === 37) {
        this.changeCurrentTime(video, -5);
      } else if (gfg.keyCode === 38) {
        this.changeVolume(video, 0.05);
      } else if (gfg.keyCode === 39) {
        this.changeCurrentTime(video, 5);
      } else if (gfg.keyCode === 40) {
        this.changeVolume(video, -0.05);
      }
    };

    document.onfullscreenchange = () => {
      if (!document.fullscreenElement) {
        this.fullscreen = false;
      }
    };

    this.showCase = document.getElementById('sessionOne') as HTMLElement;
    this.showCase.onmousemove = () => {
      this.showcaseStyle = {
        cursor: 'default'
      };
      clearTimeout(this.timer);
      this.timer = setTimeout(() => {
        this.showcaseStyle = {
          cursor: 'none'
        };
      }, 2000);
    };

    this.layout = (document.getElementById('sessionOne') as HTMLElement);
    this.layout.onmousemove = () => {
      this.layoutStyle = {
        color: 'red',
        cursor: 'default'
      };
      clearTimeout(this.timer);
      this.timer = setTimeout(() => {
        this.layoutStyle = {
          color: 'transparent',
          cursor: 'none'
        };
      }, 2000);
    };
  }


  checkcurrentSubtitle(currentTime: number) {
    if(this.showSubtitle){
      let counter = 0;
      for (let i = 0; i < this.subtitle.elements.length; i++) {
        if (this.subtitle !== undefined && this.subtitle.elements[i] !== undefined &&
          this.subtitle.elements[i].start < currentTime && this.subtitle.elements[i].end > currentTime) {
          if (counter === 0) {
            this.currentlyActiveSubtitleElements = [];
            counter = counter + 1;
          }

          let tempString = '';
          for (let index = 0; index < this.subtitle.elements[i].content.length - 1; index++) {
            if(this.subtitle.elements[i].content[index] === '?' && this.subtitle.elements[i].content[index + 1] === '�'){
              tempString = tempString + '�';
            }else {
              console.log(this.subtitle.elements[i].content[index] === '�');
              tempString = tempString + this.subtitle.elements[i].content[index];
            }
          }


          this.currentlyActiveSubtitleElements.push(this.subtitle.elements[i].content
            .replace('<i>', '')
            .replace('<b>', '')
            .replace('</b>', '')
            .replace('</i>', ''));

        }
      }
      if (counter === 0) {
        this.currentlyActiveSubtitleElements = [];
      }
      if(this.currentTime === 0){
        this.currentTime = currentTime;
      }
      if(this.currentTime + 1 < currentTime){
        this.currentTime = this.currentTime + 2;
      }
    }else {
      this.currentlyActiveSubtitleElements = [];
    }
  }

  turnOnOffSubtitle(video: HTMLVideoElement, value: boolean) {
    this.showSubtitle = !this.showSubtitle;
  }

  showSubtitleMethod(value: string): string {
    return this.showSubtitle ? value : '';
  }

  changeVolume(video: HTMLVideoElement, value: number) {
    if (value > 0) {
      if ((1 - video.volume) <= value) {
        video.volume = 1;
      } else {
        video.volume = video.volume + value;
      }
    } else {
      if (video.volume < Math.abs(value)) {
        video.volume = 0;
      } else {
        video.volume = video.volume + value;
      }
    }
    this.volume = video.volume;
  }

  changeFullscreenMode() {
    const page = document.documentElement;
    this.fullscreen = !this.fullscreen;
    if (this.fullscreen) {
      if (page.requestFullscreen) {
        page.requestFullscreen();
      }
      this.videoContainerStyle = {
        height: '100%',
        width: '100%'
      };

      /*this.videoPlayerStyle = {
        'min-height': '100%',
        'min-width': '100%'
      };*/

      this.videoPlayerStyle = {
        'height': '100vh',
        'width': '100vw',
        'position': 'fixed',
        'object-fit': 'cover'
      };

      /*
      * width: 100vw;
    height: 100vh;
    position: fixed;
    object-fit: cover;
      *
      * */
    } else {
      if (document.exitFullscreen) {
        document.exitFullscreen();
      }

      this.videoContainerStyle = {
        /*height: window.innerHeight.toFixed(0) + 'px',
        width: window.innerWidth.toFixed(0) + 'px'*/
        height: screen.height * 0.9 + 'px',
        width: screen.width + 'px'
      };

      /*this.videoPlayerStyle = {*/
      /*'min-height': window.innerHeight.toFixed(0) + 'px',
      'min-width': window.innerWidth.toFixed(0) + 'px'*/
      /*'min-height': screen.height * 0.9 + 'px',
      'min-width': screen.width + 'px'
    };*/

      this.videoPlayerStyle = {
        'height': '100vh',
        'width': '100vw',
        'position': 'fixed',
        'object-fit': 'cover'
      };
    }
  }
  changeCurrentTime(video: HTMLVideoElement, value: number) {
    if (value > 0) {
      if ((video.duration - video.currentTime) <= value) {
        video.currentTime = video.duration - 1;
      } else {
        video.currentTime = video.currentTime + value;
      }
    } else {
      if (video.currentTime < Math.abs(value)) {
        video.currentTime = 0;
      } else {
        video.currentTime = video.currentTime + value;
      }
    }
  }

  startVideoSetup(video: HTMLVideoElement): void {
    this.http.getSubtitle(this.videoIdFromRoute).subscribe(
      (response) => {
        if (response !== null) {
          if (response.elements.length > 0) {
            this.subtitle = response;
            this.title = this.subtitle.title;
            video.onloadeddata = this.autoStart(video);
            this.layoutService.subtitleBack.next(true);
          } else {
            this.subtitle = response;
            this.title = this.subtitle.title;
            video.onloadeddata = this.autoStart(video);
          }
        } else {
          this.title = 'No video found.';
        }
      }
    );
  }

  autoStart(video: HTMLVideoElement): any {
    video.onplay = () => {
      this.playing = true;
      this.layoutService.isPlayingBack.next(this.playing);
      this.layoutService.isPlaying.next(true);
    };

    video.onpause = () => {
      this.playing = false;
      this.layoutService.isPlayingBack.next(this.playing);
      this.layoutService.isPlaying.next(false);
    };

    video.onended = () => {
      if (this.autoStartVideo) {
        this.loadOtherVideo(video, true);
      }
      this.isVideoEnded = true;
      this.http.setWatchedTime(this.videoIdFromRoute, -1);
      localStorage.setItem('volume', (video.volume as unknown) as string);
    };
    const volumeString = localStorage.getItem('volume');
    if (volumeString !== null && volumeString !== undefined) {
      this.volume = Number(volumeString);
      video.volume = Number(volumeString);
    }

    if (this.autoStartVideo) {
      this.playing = true;
      video.play();
    }
  }

  loadOtherVideo(video: HTMLVideoElement, isItNext: boolean): void {
    if (!this.isVideoEnded) {
      this.http.setWatchedTime(this.videoIdFromRoute, Math.floor(video.currentTime)).subscribe(() => { });
    } else {
      this.http.setWatchedTime(this.videoIdFromRoute, -1).subscribe(() => { });
    }
    localStorage.setItem('volume', video.volume.toFixed(2));
    this.textTrackCounter = this.textTrackCounter + 1;

    this.previousVideoId = this.videoIdFromRoute;
    this.videoIdFromRoute = this.getNewVideoID(isItNext);
    this.http.updatePlaylist(this.playlist.id, this.videoIdFromRoute).subscribe();
    this.videoURL = 'http://localhost:8080/video/stream/' + this.videoIdFromRoute;
    video.load();

    this.http.getWatchedTime(this.videoIdFromRoute).subscribe((value) => {
      if (value === -1) {
        video.currentTime = 0;
      } else {
        video.currentTime = value;
      }
      this.playing = true;
      video.play();
    });

    this.http.getSubtitle(this.videoIdFromRoute).subscribe(
      (response) => {
        if (response !== null) {
          this.subtitle = response;
          this.title = this.subtitle.title;
        }}
    );
  }

  home(video: HTMLVideoElement): void {
    this.http.setWatchedTime(this.videoIdFromRoute, Math.floor(video.currentTime)).subscribe(() => {
      this.router.navigate(['']);
    });
  }

  getNewVideoID(next: boolean): number {
    let didIFindVideo = false;
    for (let i = 0; i < this.playlist.listOfAllVideo.length; i++) {
      if (this.playlist.listOfAllVideo[i] === this.videoIdFromRoute) {
        if (next) {
          for (let j = i + 1; j < this.playlist.listOfAllVideo.length; j++) {
            if (!this.doesFillerListContainsVideo(this.playlist.listOfAllVideo[j], this.playlist.listOfFillerVideo)) {
              didIFindVideo = true;
              return this.playlist.listOfAllVideo[j];
            }
          }
        } else {
          for (let j = i - 1; j >= 0; j--) {
            if (!this.doesFillerListContainsVideo(this.playlist.listOfAllVideo[j], this.playlist.listOfFillerVideo)) {
              didIFindVideo = true;
              return this.playlist.listOfAllVideo[j];
            }
          }
        }
      }
    }
    if (!didIFindVideo) {
      this.home(document.getElementById('videoPlayer') as HTMLVideoElement);
    }

    return 0;
  }

  doesFillerListContainsVideo(id: number, fillerList: Array<number>): boolean {
    for (let i = 0; i < fillerList.length; i++) {
      if (fillerList[i] === id) {
        return true;
      }
    }
    return false;
  }
}
