docker-compose down
docker system prune -f
docker rm -f portfolio-videoplayer-1 portfolio-db-1
docker rmi -f videoplayer:latest mysql:8.0.27
docker volume rm portfolio_db_data portfolio_videoplayer_data
::docker volume rm $(docker volume ls -q)
::docker rm -f $(docker container ls -a -q)
::docker rmi -f $(docker image ls -a -q)
