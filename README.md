# Portfolio (Nándor Segyevy)
A collection of my project I intend to list in my portfolio.

Table of content

<ol>
<li>

[Video Player Angular Frontend](#1_0)</li>
</ol>

## <a name="1_0"></a>Video Player Angular Frontend

Angular project was created with Angular CLI

Angular CLI: 16.2.1

Node: 18.15.0

Package Manager: npm 9.5.0

#### Routes

'home' --> PlaylistListComponent

'playlist' --> PlaylistPlayerComponent

'**' -->PlaylistListComponent

### Components

<ol>
<li>

[Playlist List Component](#playlist-list)</li>
<li>

[Playlist Player Component](#playlist-player)</li>
<li>

[Video Player Layout Component](#video-player-layout)</li>
<li>

[Progress Component](#progress)</li>
<li>

[Progress Slider Component](#progress-slider)</li>
</ol>

#### app.component

App-root, contains the navbar and the router component.
Navbar buttons:
<ol>
<li>Video Player -> '/home'</li>
<li>Home -> '/home'</li>
<li>Update -> This will trigger a database refresh on the server side.</li>
<li>Shutdown -> This button will request the server to stop.</li>
</ol>

#### playlist-list

This page works as the **Home Page**. This component will provide a list of all Playlists.

<table>
<tr>
<th>Name</th>
<th>Video Count</th>
<th>Filler Percent</th>
<th>Play Episode</th>
<th>Reset all Playlist</th>
</tr>
<tr>
<td>Name of the Playlist.

Default lists are marked with "- default".</td>
<td>Amount of videos included in the Playlist.</td>
<td>Percentage of episode filtered out on Playlist. Default playlist has no such filtering.</td>
<td>Start playing the Playlist from the episode (number) provided in the input field.</td>
<td>Reset the given Playlist.</td>
</tr>
</table>

#### playlist-player

Video player HTML tag is added here. This Component responsible for loading/starting/playing the videos. Currently Component supports three video extension (webm, mp4 and ogg).
The component also will use subtitles in case there was a (.srt) subtitle file provided with the video file.
This component also contains the [Video Player Layout Component](#video-player-layout) and the [Progress Slider Component](#progress-slider).

#### video-player-layout

With this component the user will be able to command the video player.

Component shows the title of current video and volume (percentage). Component contains the [Progress Component](#progress).

Buttons / keyboard commands:


<table>
<tr>
<th>Button / Key pressed</th>
<th>Command</th>
</tr>
<tr>
<td>skip_previous</td>
<td>Play the previous video on the Playlist if exists.</td>
</tr>
<tr>
<td>skip_next</td>
<td>Play the next video on the Playlist if exists.</td>
</tr>
<tr>
<td>exit_to_app</td>
<td>Redirect user to the **Home Page**. Using the Button will send a request to the server to save progress.</td>
</tr>
<tr>
<td>volume_off / volume_up</td>
<td>Mute / Unmute video player.</td>
</tr>
<tr>
<td>fast_rewind</td>
<td>Set the video player progress back with 1 minutes 30 seconds or at 00:00 if current progress is less than 01:30.</td>
</tr>
<tr>
<td>replay_5 / left key</td>
<td>Set the video player progress back with 5 seconds or at 00:00 if current progress is less than 00:05.</td>
</tr>
<tr>
<td>pause / play_arrow / space key</td>
<td>Pause or start playing the video.</td>
</tr>
<tr>
<td>forward_5 / right key</td>
<td>Set the video player progress forward with 5 seconds or start next video if video has less than 5 seconds left.</td>
</tr>
<tr>
<td>fast_forward</td>
<td>Set the video player progress forward with 1 minutes 30 seconds or start next video if video has less than 1 minutes 30 seconds left.</td>
</tr>
<tr>
<td>subtitles / video_label</td>
<td>Turn on / off subtitles.</td>
</tr>
<tr>
<td>visibility / visibility_off</td>
<td>Show / Hide the video progress bar.</td>
</tr>
<tr>
<td>fullscreen_exit / fullscreen</td>
<td>Request the video player to go / exit full screen mode.</td>
</tr>
<tr>
<td>up / down key</td>
<td>Change volume by 5% up or down.</td>
</tr>
</table>

#### progress

This Component show the current progress of the video on the video layout in the format of 00:00:00 / 00:00:00 (Current/Full).

#### progress-slider

This component currently not working after upgrade from Angular 15 to Angular 16.

[comment]: <> (```mermaid)
[comment]: <> (%%{init: {"flowchart": {"htmlLabels": false}} }%%)
[comment]: <> (flowchart LR)
[comment]: <> (    markdown["Home Page"])
[comment]: <> (    newLines["`Line1)
[comment]: <> (    Line 2)
[comment]: <> (    Line 3`"])
[comment]: <> (    markdown --> newLines)
[comment]: <> (```)

## Video Player Java Spring Boot Backend

The Spring Boot server intended to serve all request coming from the Angular frontend and some other requests.
Server is a classic REST API with Controller - Service - Repository levels.

### Domain

<ol>
<li>FillerRecord</li>
<li>Folder</li>
<li>Playlist</li>
<li>Subtitle</li>
<li>Video</li>
</ol>

## Rest API Entry Points

### 1. Folder Controller

#### 1.1 FolderController GET -/api/folders/all

Return the list all non-empty Folders.

#### 1.2 FolderController GET - /api/folders/{id}

Return the folder with the **given id**.

#### 1.3 FolderController POST - /api/folders/playlist

Create a new Playlist.

#### 1.4 FolderController GET - /api/folders/playlist/narutoFillerFree/

Duplicate "Naruto Shippuuden . default" Playlist and add filler episode filtering.

#### 1.5 FolderController GET - /api/folders/playlist

Return all the Playlists.

#### 1.6 FolderController GET - /api/folders/playlistReset/{id}

Reset the Playlist with **given id**. Set last watched episode to the firts episode and watched time to zero seconds.

#### 1.7 FolderController GET - /api/folders/playlistUpdateState/{id}/{videoID}

Update the last watched episode of the Playlist with **given id** to the video with **given videoID**. 

Updated on 03. 09. 2023