import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import { PlaylistListComponent } from '../components/playlist-list/playlist-list.component';
import { PlaylistPlayerComponent } from '../components/playlist-player/playlist-player.component';



const routes: Routes = [
  {path: 'playlist', component: PlaylistPlayerComponent},
  {path: 'home', component: PlaylistListComponent},
  {path: '', component: PlaylistListComponent},
  {path: '**', component: PlaylistListComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
