import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing/app-routing.module';
import { AppComponent } from './app.component';
import {MatToolbarModule} from "@angular/material/toolbar";
import { PlaylistListComponent } from './components/playlist-list/playlist-list.component';
import { PlaylistPlayerComponent } from './components/playlist-player/playlist-player.component';
import { VideoPlayerLayoutComponent } from './components/video-player-layout/video-player-layout.component';
import {MatIconModule} from "@angular/material/icon";
import { ProgressSliderComponent } from './components/helping/progress-slider/progress-slider.component';
import { ProgressComponent } from './components/helping/progress/progress.component';
import {MatSliderModule} from "@angular/material/slider";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatTableModule} from "@angular/material/table";
import { HttpClientModule } from '@angular/common/http';
import {MatInputModule} from "@angular/material/input";
import {MatSortModule} from "@angular/material/sort";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MatButtonModule} from "@angular/material/button";
import {MatTooltipModule} from "@angular/material/tooltip";

@NgModule({
  declarations: [
    AppComponent,
    PlaylistListComponent,
    PlaylistPlayerComponent,
    VideoPlayerLayoutComponent,
    ProgressSliderComponent,
    ProgressComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatToolbarModule,
    MatIconModule,
    MatSliderModule,
    MatPaginatorModule,
    MatFormFieldModule,
    MatTableModule,
    HttpClientModule,
    MatInputModule,
    MatSortModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatTooltipModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
