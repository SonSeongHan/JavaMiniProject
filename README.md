  # Java_Mini_Project_PACMAN

  Java Swing, AWT를 이용한 클래식 게임

  ## 프로젝트 소개

  옛날 클래식 게임 PACMAN을 구현한 프로젝트입니다

  ## 개발 기간

  - 24.10.28(월) ~ 24.10.30(수)
---
### 맴버

개인프로젝트였습니다

### 개발환경
- 언어 : Java 17
- IDE : Eclipse
- 운영 체제 : Windows
- Java SDK : Oracle JDK 17

---

## 주요 기능

#### 맵
- 구조 : 2차원 배열로 타일 값 0-길, 1-벽, 2-길, 3-스페셜 아이템
- 코인 및 스페셜 아이템 수집 : 게임 시작 시 스페셜 아이템 및 코인 생성, 팩맨이 해당 타일에 도착하면 아이템 수집

#### 팩맨
- 위치와 이미지 : x, y 좌표로 위치 저장, 방향에 따라 이미지 업데이트
- 이동 로직 : 방향키 입력에 따라 이동, 이동 가능 여부를 맵의 타일 정보로 판단
- 충돌 처리 : 팩맨이 유령과 충돌 시 생명 감소 또는 게임 오버

#### 유령
- 위치와 이동 : ArrayList를 사용한 배열로 3마리 컨트롤, Random함수로 인한 무작위 이동, 마지막 이동 방향과 반대 방향으로 이동 금지
- 충돌 처리 : 스페셜 아이템이 활성화 되어있을 시 유령 제거, 그렇지 않을 시 생명 감소 또는 게임 오버

#### 게임 UI
- 점수 및 생명 표시 : 게임 화면 상단에 현재 점수와 남은 생명 표시
- 게임 시작 및 종료 : 스페이스 바를 눌러 게임 시작, 게임 오버 또는 클리어 상태 시 해당 메시지 표시

---

## 클래스 UML
![Pacman_UML(Class)](https://github.com/user-attachments/assets/0b2be6a4-f7a6-4978-86f0-a1db4a22115b)

---

## 게임 화면

#### 게임 시작 전
<img width="583" alt="게임시작전" src="https://github.com/user-attachments/assets/f34b18bd-68f5-4407-ab01-6d0f82cfaead">

#### 게임 플레이 중
<img width="577" alt="게임플레이장면" src="https://github.com/user-attachments/assets/578255ec-62cd-4caa-84c6-3bf4733da1c3">

#### 게임 클리어
<img width="584" alt="게임클리어" src="https://github.com/user-attachments/assets/91cdba17-b28d-4007-a277-c38d0130164c">

#### 게임 오버
<img width="582" alt="게임오버" src="https://github.com/user-attachments/assets/9d05e150-312e-4910-b562-307113009d22">
