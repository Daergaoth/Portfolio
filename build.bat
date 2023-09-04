ECHO My build script starts.
set start_directory=%cd%
CD .\VideoPlayerAngular\videoplayer
@RD /S /Q ".\dist\videoplayer"
call npm run build
CD %start_directory%
@RD /S /Q ".\VideoPlayerJava\VideoPlayer\src\main\resources\public\*"
XCOPY ".\VideoPlayerAngular\videoplayer\dist\videoplayer" ".\VideoPlayerJava\VideoPlayer\src\main\resources\public" /s /e /y /q
CD .\VideoPlayerJava\VideoPlayer
docker rmi -f videoplayer
docker build --no-cache --progress=plain -t videoplayer .
CD %start_directory%
docker-compose up -d
docker logs -f portfolio-videoplayer-1