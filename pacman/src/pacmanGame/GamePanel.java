package pacmanGame;

import javax.swing.*;
import java.awt.*; // awt 컴포넌트, 레이아수 클래스 사용하기 윈한 패키지
import java.awt.event.*; // awt 이벤트 리스너 사용하기 위한 패키지
// 유령 목록 만들려고
import java.util.ArrayList;
import java.util.List;

// 게임 패널 클래스, JPanel을 상속
// ActionListener : 특정 행동 발생 시 실행되는 인터페이스
class GamePanel extends JPanel implements ActionListener {

	private List<Ghost> ghosts; // 유령 리스트
	private Timer ghostTimer;
	private Timer pacmanTimer;
	private Pacman pacman; // 팩맨 객체
	private GameMap gameMap; // 게임 맵
	private GamePanel gamePanel;
	private Image heart;
	private boolean pacmanMoved = false; // 팩맨 이동 여부

	// 게임 문구
	private boolean gameOver = false; // 아직 게임 오버는 아니니까 false
	private boolean gameClear = false; // 게임 클리어 상태 추가 -> 아직은 false
	private boolean gameStarted = false; // 게임 시작 여부 추가

	// special 아이템 먹었을 시 특수 효과
	private boolean specialActive = false;
	private Timer specialTimer;

	// 게임 정보 UI
	private int score = 0;
	private int lives = 3;
	private int time = 0;

	// 생성자
	public GamePanel() {
		setFocusable(true); // 먼저 키 입력을 받아주는 함수
		gameMap = new GameMap();
		pacman = new Pacman(8, 7, gameMap, this);

		ghosts = new ArrayList<>();
		ghosts.add(new Ghost(14, 12, gameMap));
		ghosts.add(new Ghost(1, 1, gameMap));
		ghosts.add(new Ghost(1, 12, gameMap));

		// 팩맨, 유령 속도 따로 분리
		pacmanTimer = new Timer(150, e -> {
			pacman.move();
			pacmanMoved = true;
			update();
			repaint();
		});
		pacmanTimer.start();

		ghostTimer = new Timer(180, e -> {
			for (Ghost ghost : ghosts) {
				ghost.move();
			}
			repaint();
		});
		ghostTimer.start();

		// 키 입력 리스너
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) { // 키가 눌렸을 때

				// 스페이스바로 게임 시작
				if (!gameStarted && e.getKeyCode() == KeyEvent.VK_SPACE) {
					gameStarted = true;
					pacmanTimer.start();
					ghostTimer.start();
				}
				if (gameStarted) {
					pacman.handleKeyPress(e);

					pacman.handleKeyPress(e);
					if (gameMap.collectSpecial(pacman.getX(), pacman.getY())) {
						startSpecialEffect();
					}
				}
			}
		});

//		// 게임 스레드 시작
		// 더 프레임이 끊겨서 타이머로 함
//		GameThread gameThread = new GameThread(this); // 게임 스레드 객체
//		new Thread(gameThread).start(); // 새로운 스레드에서 게임 실행

		// 생명 이미지 가져오기
		loadHeartImage();
	}

//	// 스페셜 아이템 근처에 있어도 먹는 메소드
//	private boolean isNearSpecial(int x, int y) {
//		for (int i = 0; i < gameMap.getHeight(); i++) {
//			for (int j = 0; j < gameMap.getWidth(); j++) {
//				if (gameMap.getTile(j, i) == 3) {
//					if (Math.abs(x - j) < i && Math.abs(y - i) < 1) {
//						gameMap.collectSpecial(j, i);
//						return true;
//					}
//				}
//			}
//		}
//		return false;
//	}

	// 팩맨의 충돌 (유령, 코인)
	private void checkCollision() {
//		// 팩맨 위치 확인
//		System.out.println("Pacman Position: " + pacman.getX() + ", " + pacman.getY());
		for (int i = 0; i < ghosts.size(); i++) {
			Ghost ghost = ghosts.get(i);
			if (pacman.getX() == ghost.getX() && pacman.getY() == ghost.getY()) {
				// 스페셜 효과 on -> 유령 제거
				if (specialActive) {
					ghosts.remove(i);
					i--;
				} else {
					lives--;
					pacman.resetPosition();
					if (lives <= 0) {
						gameOver = true;
					}
				}
				break;
			}
		}

//		if (gameMap.collectCoin(pacman.getX(), pacman.getY())) {
//			score++;
//		}

		if (gameMap.allCoinsCollected()) {
			gameClear = true;
		}

	}

	// 24.10.30.14:40 손성한
	// 점수 증가 메소드
	public void incrementScore() {
		score++;
		repaint();
	}

	public void update() {
		if (!gameOver && !gameClear) {
			if (pacmanMoved) {
				checkCollision();
				pacmanMoved = false;
			}
			repaint();
		}
	}

	// 스페셜 아이템 먹었을 시 시작
	public void startSpecialEffect() {
		specialActive = true; // 스페셜 효과 활성화
		time = 5;

		// 24.10.30.14:45
		// 코인 획득 시 유령 속도 감소 추가
		ghostTimer.setDelay(500);

		specialTimer = new Timer(1000, e -> {
			time--;
			if (time <= 0) {
				specialActive = false; // 효과 종료
				ghostTimer.setDelay(180);
				specialTimer.stop(); // 타이머 정지
			}
			repaint();
		});
		specialTimer.start();
	}

	// 하트 사진 가져오기
	private void loadHeartImage() {
		heart = new ImageIcon(getClass().getResource("/images/heart.png")).getImage();
	}

	// 타이머 작동시
	public void actionPerformed(ActionEvent e) {

	}

	// 팩맨과 적 그리기 + map 그리기 추가
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g); // 부모 클래스의 paintComponent 호출
		if (!gameStarted) { // 게임이 시작되지 않았을 때

			gameMap.draw(g);
			g.setColor(new Color(0, 0, 0, 128));
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.PLAIN, 30));
			String startText = "Press Spacebar Key";
			FontMetrics metrics = g.getFontMetrics();
			int x = (getWidth() - metrics.stringWidth(startText)) / 2;
			int y = getHeight() / 3;
			g.drawString(startText, x, y);
		} else if (gameClear) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(Color.WHITE);
			g.setFont(new Font("Arial", Font.BOLD, 50));
			String gameClearText = "GAME CLEAR";
			FontMetrics metrics = g.getFontMetrics();
			int x = (getWidth() - metrics.stringWidth(gameClearText)) / 2; // 가운데 정렬
			int y = getHeight() / 2;
			g.drawString(gameClearText, x, y);
		} else if (gameOver) {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, getWidth(), getHeight()); // 검은 배경
			g.setColor(Color.RED);
			g.setFont(new Font("Arial", Font.BOLD, 50));
			String gameOverText = "GAME OVER";
			FontMetrics metrics = g.getFontMetrics();
			int x = (getWidth() - metrics.stringWidth(gameOverText)) / 2; // 가운데 정렬
			int y = getHeight() / 2;
			g.drawString(gameOverText, x, y);
		} else {
			gameMap.draw(g);
			pacman.draw(g);
			for (Ghost ghost : ghosts) {
				ghost.draw(g);
			}

			// 게임 정보 UI 표시
			g.setColor(Color.BLACK);
			g.drawString("Score : " + score, 650, 20);
			g.drawString("Time : " + (specialActive ? time : 0), 650, 50);
			g.drawString("Lives ", 650, 80);
			for (int i = 0; i < lives; i++) {
				g.drawImage(heart, 650 + (i * 25), +90, 20, 20, null);
			}
		}

	}

}