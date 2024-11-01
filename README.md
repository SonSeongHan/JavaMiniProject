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
- GameMap 클래스

  ```
     public GameMap() {
    map = new int[][] {
        { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
        { 1, 0, 0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 3, 0, 1 },
        // ... (생략)
        { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }
    };
    loadImages();
    initializeMap();
  }

  private void initializeMap() {
    for (int i = 0; i < map.length; i++) {
        for (int j = 0; j < map[i].length; j++) {
            if (map[i][j] == 0) {
                map[i][j] = 2; // 0을 2로 변경
            }
        }
    }
  }

#### 팩맨
- 위치와 이미지 : x, y 좌표로 위치 저장, 방향에 따라 이미지 업데이트
- 이동 로직 : 방향키 입력에 따라 이동, 이동 가능 여부를 맵의 타일 정보로 판단
- 충돌 처리 : 팩맨이 유령과 충돌 시 생명 감소 또는 게임 오버
   ```public void handleKeyPress(KeyEvent e) {
    int key = e.getKeyCode();
    switch (key) {
        case KeyEvent.VK_UP: direction = "up"; break;
        case KeyEvent.VK_DOWN: direction = "down"; break;
        case KeyEvent.VK_LEFT: direction = "left"; break;
        case KeyEvent.VK_RIGHT: direction = "right"; break;
    }
    updateImage();
  }
  ---------------------------------------------
  public void move() {
    int newX = x, newY = y;
    switch (direction) {
        case "up": newY--; break;
        case "down": newY++; break;
        case "left": newX--; break;
        case "right": newX++; break;
    }
    if (canMove(newX, newY)) {
        x = newX;
        y = newY;
        gameMap.collectCoin(x, y); // 코인 수집 체크
    }
  }
  ------------------------------------------------- 
  public boolean canMove(int newX, int newY) {
    return (newX >= 0 && newX < gameMap.getWidth() && newY >= 0 && newY < gameMap.getHeight() &&
            gameMap.getTile(newX, newY) != 1);
  }  


#### 유령
- 위치와 이동 : ArrayList를 사용한 배열로 3마리 컨트롤, Random함수로 인한 무작위 이동, 마지막 이동 방향과 반대 방향으로 이동 금지
- 충돌 처리 : 스페셜 아이템이 활성화 되어있을 시 유령 제거, 그렇지 않을 시 생명 감소 또는 게임 오버
  ```
  public void move() {
    List<String> possibleDirections = new ArrayList<>();
    // 각 방향 체크
    if (canMove(x, y - 1)) possibleDirections.add("up");
    if (canMove(x, y + 1)) possibleDirections.add("down");
    if (canMove(x - 1, y)) possibleDirections.add("left");
    if (canMove(x + 1, y)) possibleDirections.add("right");
  ----------------------랜덤 이동 -----------------------------
  public void moveTowardsPacman(Pacman pacman) {
    int pacmanX = pacman.getX();
    int pacmanY = pacman.getY();
    int dx = Integer.compare(pacmanX, x);
    int dy = Integer.compare(pacmanY, y);

    if (canMove(x + dx, y)) {
        x += dx; // X축으로 이동
    } else if (canMove(x, y + dy)) {
        y += dy; // Y축으로 이동
    }
  }

#### 코인 및 아이템 수집
- 코인 획득 시 수집하고 특수 효과 발동
  ```
  public boolean collectCoin(int x, int y) {
    if (getTile(x, y) == 2) {
        setTile(x, y, 0); // 코인 사라짐
        return true; // 코인 수집 성공
    }
    return false; // 코인 수집 실패
  }
  --------------------스페셜 코인-------------------
  public boolean collectSpecial(int x, int y) {
    if (getTile(x, y) == 3) {
        setTile(x, y, 0); // 스페셜 아이템 사라짐
        return true; // 스페셜 아이템 수집 성공
    }
    return false; // 수집 실패
  }
  public void useSpecialAbility() {
    isInvincible = true;
    // 5초 후 다시 정상 상태로 복귀
    new Timer(5000, e -> {
        isInvincible = false;
    }).start();
  }  
  --------------------------------------------------
  public boolean allCoinsCollected() {
    for (int i = 0; i < map.length; i++) {
        for (int j = 0; j < map[i].length; j++) {
            if (map[i][j] == 2 || map[i][j] == 3) {
                return false; // 아직 남은 코인 또는 스페셜 아이템
            }
        }
    }
    return true; // 모든 코인 수집 완료
  }

#### 게임 UI
- 점수 및 생명 표시 : 게임 화면 상단에 현재 점수와 남은 생명 표시
- 게임 시작 및 종료 : 스페이스 바를 눌러 게임 시작, 게임 오버 또는 클리어 상태 시 해당 메시지 표시
  ```
  // 게임 정보 UI 표시
	g.setColor(Color.BLACK);
	g.drawString("Score : " + score, 650, 20);
	g.drawString("Time : " + (specialActive ? time : 0), 650, 50);
	g.drawString("Lives ", 650, 80);
	for (int i = 0; i < lives; i++) {
		g.drawImage(heart, 650 + (i * 25), +90, 20, 20, null);
	}
  ------------ 점수 증가 ---------------
  public void incrementScore(int amount) {
    score += amount;
    scoreLabel.setText("Score: " + score); // UI 업데이트
  }
  
---
## 클래스 UML
![Pacman_UML(Class)](https://github.com/user-attachments/assets/0b2be6a4-f7a6-4978-86f0-a1db4a22115b)

---
## 게임 화면

#### 게임 시작 전
<img width="583" alt="게임시작전" src="https://github.com/user-attachments/assets/f34b18bd-68f5-4407-ab01-6d0f82cfaead">

#### 게임 플레이 중
<img width="577" alt="게임플레이장면" src="https://github.com/user-attachments/assets/578255ec-62cd-4caa-84c6-3bf4733da1c3">

#### 게임 종료 및 재시작
<img width="581" alt="게임종료_재시작" src="https://github.com/user-attachments/assets/69929bad-46c0-49d2-b221-d0173147a6c2">

#### 시연 영상
https://github.com/user-attachments/assets/bcb06972-3eab-42fc-9c3a-c0f96ced179d



