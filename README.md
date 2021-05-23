***
# URL Shortening

## 사전세팅   
* 해당 프로젝트는 Spring Boot Framework 로 개발하였으며, 리눅스 환경에서 Gradle 을 통해 빌드 및 실행해야 합니다   
* 리눅스 환경 내 Jave 8, git 설치 필요   
   
## 실행설명   
  1. 리눅스 접속 후 github 에 있는 프로젝트 배포 -> shortenUrl 디렉토리 생성   
    - git clone https://github.com/ukjae88/shortenUrl.git   
  2. shortenUrl 디렉토리 위치에서 프로젝트 빌드하기 -> build 디렉토리 생성   
    - 명령어 권한 설정 : chmod 777 ./gradlew   
	- 테스트수행 : ./gradlew test  
	- 빌드실행 : ./gradlew build   
  3. .build/libs 폴더 경로에 생성된 urlshorten-0.0.1-SNAPSHOT.jar 파일을 실행한다   
    - jar 파일 실행 : java -jar build/libs/urlshorten-0.0.1-SNAPSHOT.jar  
  4. 서비스 동작을 확인한다   
	- URL 입력폼 화면 접속 : http://{호스트IP}:8080/   
	- Shortening URL 입력형식 : http://{호스트IP}:8080/{Shortening key}   
	  (Shortening URL 의 호스트 IP 는 모르는 상황이므로 localhost 로 설정해 놓음)   
   
***