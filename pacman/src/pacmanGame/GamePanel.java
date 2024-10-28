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
	private Ghost ghost; // 적 객체
	private GameMap gameMap; // 게임 맵
	private GamePanel gamePanel;
	private Image heart;
	private boolean gameOver = false; // 아직 게임 오버는 아니니까 false

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
		pacmanTimer = new Timer(100, e -> {
			pacman.move();
			update();
			repaint();
		});
		pacmanTimer.start();

		ghostTimer = new Timer(200, e -> {
			for (Ghost ghost : ghosts) {
				ghost.move();
			}
			repaint();
		});
		ghostTimer.start();

		// 키 입력 리스너
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) { // 키가 눌렸을 때
				pacman.handleKeyPress(e);
			}
		});

		// 게임 스레드 시작
		GameThread gameThread = new GameThread(this); // 게임 스레드 객체
		new Thread(gameThread).start(); // 새로운 스레드에서 게임 실행

		// 생명 이미지 가져오기
		loadHeartImage();
	}

	// 팩맨과 유령 충돌 체크
	private void checkCollision() {
		for (Ghost ghost : ghosts) {
			if (pacman.getX() == ghost.getX() && pacman.getY() == ghost.getY()) {
				lives--;
				pacman.resetPosition();

				if (lives <= 0)

				{
					gameOver = true;
				}
				break;
			}
		}
	}

	// 하트 사진 가져오기
	private void loadHeartImage() {
		heart = new ImageIcon(getClass().getResource("/images/heart.png")).getImage();
	}

	// 타이머 작동시
	public void actionPerformed(ActionEvent e) {

	}

	public void update() {
		if (!gameOver) {
			score++;
			checkCollision();
			repaint();
		}
	}



	// 팩맨과 적 그리기 + map 그리기 추가
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g); // 부모 클래스의 paintComponent 호출
		if (gameOver) {
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
			g.drawString("Time : " + time, 650, 50);
			g.drawString("Lives ", 650, 80);
			for (int i = 0; i < lives; i++) {
				g.drawImage(heart, 650 + (i * 25), +90, 20, 20, null);
			}
		}

	}

}
