# 웹소켓 기반 채팅 서비스

### 개요

- 웹소켓이 무엇인가
- 채팅서비스는 어떻게 구현되는가
- 공부하고, 프로젝트를 만들며 적용하기.

### 기술스택

- JAVA21, SpringBoot3.4, MySQL, Redis, Vue

### 요구사항

- 채팅 사용자 식별을 위한 로그인
- 실시간 채팅
- 1:1 채팅, 그룹 채팅
- 내 채팅 목록 조회

### 구현

- JWT 기반 인증
- WebSocket, Stomp 기반 실시간 채팅 서비스
- Redis 기반 다중서버 확장성 설계

### 환경설정

1. **Docker Compose로 MySQL & Redis 실행**
```shell
cd ./db
docker-compose up -d
```

### 공부

[WebSocket 정리](./doc/WebSocket.md)
