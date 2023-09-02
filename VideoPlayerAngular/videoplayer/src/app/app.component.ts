import {Component} from '@angular/core';
import {HttpServiceService} from "./services/http-service.service";
import {InnerService} from "./services/inner.service";
import {LayoutService} from "./services/layout.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'videoplayer';
  showNavbar = true;
  playing: boolean = false;
  navbarStyle = {
    'height': '50px',
    "color": "white"
  }

  constructor(private http: HttpServiceService, private innerService: InnerService) {
    innerService.isPlaying.subscribe(
      (next) => {
        if (next) {
          this.navbarStyle = {
            "height": "0px",
            "color": "transparent"
          };
        } else {
          this.navbarStyle = {
            'height': '50px',
            "color": "white"
          }
        }
      }
    );
  }

  refreshDatabase(): void {
    this.http.refreshDatabase().subscribe(
      () => { }
    );
  }

  shutdownServer(): void {
    this.http.shutdownServer().subscribe();
    console.log('Server probably shutting down.');
  }
}
