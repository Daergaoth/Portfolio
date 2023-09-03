import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import { HttpServiceService } from 'src/app/services/http-service.service';
import { Router } from '@angular/router';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { PlaylistModel } from 'src/app/models/playlistmodel';
import { PlaylistService } from 'src/app/services/playlist.service';
import {MainListElementModel} from "../../models/mainListElementModel";
import { MatTable } from '@angular/material/table';

@Component({
  selector: 'app-playlist-list',
  templateUrl: './playlist-list.component.html',
  styleUrls: ['./playlist-list.component.css']
})
export class PlaylistListComponent implements OnInit, AfterViewInit {
  // displayedColumns: string[] = ['title', 'videoCount', 'percentOfFillerEpisodes' ];
  displayedColumns: string[] = ['title', 'videoCount', 'percentOfFillerEpisodes', 'specificEpisode', 'resetPlaylist' ];
  playlistList: Array<MainListElementModel>;
  initDefaultList: Array<MainListElementModel>;
  initCustomList: Array<MainListElementModel>;
  dataSource: any;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  @ViewChild(MatTable) table!: MatTable<any>;
  resetAllButtonStyle = {
    color: 'blue'
  };

  constructor(private router: Router, private http: HttpServiceService) { }

  ngAfterViewInit(): void {
    /*const routeParams = this.route.snapshot.paramMap;*/
    this.http.getAllPlaylist().subscribe(
      (response) => {
        this.playlistList = [];
        this.initDefaultList = [];
        this.initCustomList = [];
        if (response !== null) {
          for (let i = 0; i < response.length; i++) {
            let element: MainListElementModel = new class implements MainListElementModel {
              playlistModel: PlaylistModel;
              resetButtonStyle: { color: string };
            };
            element.playlistModel = response[i];
            element.resetButtonStyle = {
              color: 'blue'
            }
            if(element.playlistModel.name.includes(" - default")){
              this.initDefaultList.push(element);
            }else{
              this.initCustomList.push(element);
            }
          }
          this.playlistList.push.apply(this.playlistList,this.initCustomList);
          this.playlistList.push.apply(this.playlistList,this.initDefaultList);
          //this.playlistList = response;
          this.dataSource = this.playlistList;
          this.dataSource = new MatTableDataSource(this.playlistList);
          this.dataSource.paginator = this.paginator;
          this.dataSource.sort = this.sort;

        }
      }
    );
  }

  ngOnInit(): void {

  }

  playPlaylist(element: any): void {
    PlaylistService.playlist = element;
    this.router.navigate(["/playlist"]);
  }

  resetPlayList(element: any): void {
    this.http.resetPlaylist(element.playlistModel.id).subscribe(
      res => {
        element.playlistModel.lastViewedVideo = element.playlistModel.listOfAllVideo[0];
        element.resetButtonStyle = {
          color: 'green'
        }
      },
      err=>{
        console.error("Error during playlist reset");
        element.resetButtonStyle = {
          color: 'red'
        }
      });
  }

  resetAllPlayList(): void {
    for (let i = 0; i < this.playlistList.length; i++) {
      this.resetPlayList(this.playlistList[i]);
    }
    this.resetAllButtonStyle = {
      color: 'green'
    };

  }

  attemptToPlay(element: PlaylistModel) {
    const inputElement: HTMLInputElement = document.getElementById('input_' + element.id) as HTMLInputElement;

    if(inputElement !== null && inputElement !== undefined){
      inputElement.style["color"] = "black";
      if(inputElement.value.length > 0){
        let episode: number = Number(inputElement.value);
        episode--;
        let inputContentValid: boolean = true;
        if(!isNaN(episode)){
          if(episode <= element.listOfAllVideo.length && episode >= 0){
            let shouldIPlayThisEpisode: boolean = false;
            for (let i = 0; i < element.listOfAllVideo.length; i++) {
              if(element.listOfFillerVideo.length > 0){
                if(i==episode && !element.listOfFillerVideo.includes(element.listOfAllVideo[i])){
                  element.lastViewedVideo = element.listOfAllVideo[i];
                  shouldIPlayThisEpisode = true;
                }
              }else{
                if(i==episode){
                  element.lastViewedVideo = element.listOfAllVideo[i];
                  shouldIPlayThisEpisode = true;
                }
              }

            }
            if(shouldIPlayThisEpisode){
              this.playPlaylist(element);
            }else{
              inputElement.style["color"] = "red";
              inputElement.value = "This is a filler episode.";
            }
          }else{
            inputElement.style["color"] = "red";
            inputElement.value = "Out of range of this playlist.";
          }

        }else{
          inputElement.style["color"] = "red";
          inputElement.value = "Please use numbers only.";
        }
      }

    }
  }
}
