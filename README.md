# dongdaemunfriends
Social Experiment Poject dongdaemunFriends-portfolio-public-repository

https://www.notion.so/Friends-244950f247e380aba3cbeaf13c538802?source=copy_link

동대문Friends
친구들과 함께 여러가지 컨셉의 글을 쓰고 놀 수 있는 현재 운영 중인 웹사이트를 구축 했습니다. 실제 운영하며 마주한 이슈들과 사용자들의 피드백을 받아 현재도 개선하고 확장해 나가고 있습니다.

개발 기간
2025-04 ~ 현재 운영중

팀 구성
1인

사용 기술스택
Docker, Nginx, SpringBoot, Oauth2, Redis, My-SQL, MongoDB, JPA, MINIO, React, TypeScript

주요 기술 구현 및 경험

● 운영환경 인프라 구축
1. Linux환경에서 DOCKER를 이용한 컨테이너 기반 운영환경 인프라 구성
2. Nginx를 통한 로드밸런싱, 리버스 프록시 처리
3. Was의 이중화로 Blue Green 전략의 무중단 배포 구성
4. GitActions를 이용한 CI/CD 파이프라인 구축
5. 
● 파일 저장 및 리소스 최적화
1. MinIO를 활용한 객체 스토리지 구성
2. 이미지 WebP 서버 자원절약 방안 구성
3. 프론트에서 Tone.js를 이용해 음성변조 기능 구현

● 토큰/캐시/조회수 처리 고도화
1. Redis를 통한 Refresh토큰 캐싱 및 Access 토큰 재발급 구조 설계
2. 조회수등의 캐싱 및 배치처리

● 보안 및 시스템 설정
1. SSL 인증 및 Cors 정책등 설정 등 실서비스 환경 보안 고려
