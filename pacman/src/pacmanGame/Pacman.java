package pacmanGame;

import java.awt.*;
import java.awt.event.*;
import javax.swing.ImageIcon; // 이미지 가져오기
import javax.swing.SwingUtilities;

import java.io.File; // 파일 입출력을 위한 클래스
import java.io.IOException; // 입출력 예외 처리를 위한 클래스

public class Pacman {

	private int x;
	private int y;
	private Image image; // 현재 방향 팩맨 이미지 변수
	private String direction; // 현재 방향 저장할 변수
	private GameMap gameMap; // 맵 흐르에 따른 변화 감지
	private GamePanel gamePanel;

	// 생성자
	public Pacman(int startX, int startY, GameMap gameMap, GamePanel gamePanel) {
		x = startX;
		y = startY;
		direction = "none"; // 초기 방향을 "none"으로 설정
		this.gameMap = gameMap;
		this.gamePanel = gamePanel;
		loadImages();
	}

	// 이미지 로드
	private void loadImages() {
		image = new ImageIcon(getClass().getResource("/images/pacman.png")).getImage();
	}

	// 키 입력 처리 메소드
	public void handleKeyPress(KeyEvent e) {
		int key = e.getKeyCode(); // 눌린 키 가져옴
		switch (key) {
		case KeyEvent.VK_UP: // 위쪽 화살표
			direction = "up";
			break;
		case KeyEvent.VK_DOWN:
			direction = "down";
			break;
		case KeyEvent.VK_LEFT:
			direction = "left";
			break;
		case KeyEvent.VK_RIGHT:
			direction = "right";
			break;
		}
		updateImage(); // 방향에 따른 이미지 업데이트
	}

	// 방향에 따른 이미지 업데이트
	private void updateImage() {
		switch (direction) {
		case "up":
			image = new ImageIcon(getClass().getResource("/images/up.gif")).getImage();
			break;
		case "down":
			image = new ImageIcon(getClass().getResource("/images/down.gif")).getImage();
			break;
		case "left":
			image = new ImageIcon(getClass().getResource("/images/left.gif")).getImage();
			break;
		case "right":
			image = new ImageIcon(getClass().getResource("/images/right.gif")).getImage();
			break;
		}
	}

	// 팩맨 그리기
	public void draw(Graphics g) {
		g.drawImage(image, x * 40, y * 40, 40, 40, null);
	}

	// 팩맨의 현재 위치를 반환
	public Point getPosition() {
		return new Point(x, y);
	}

	public void move() {
		// 벽과 충돌 하지 않도록 이동

		int newX = x, newY = y;

		switch (direction) {
		case "up":
			newY--;
			break;
		case "down":
			newY++;
			break;
		case "left":
			newX--;
			break;
		case "right":
			newX++;
			break;
		}

		// 이동 가능 여부 확인
		if (canMove(newX, newY)) {
			x = newX;
			y = newY;

			// GameMap에서 코인 수집 여부 확인
			if (gameMap.collectCoin(x, y))
				gamePanel.update(); // update 메서드 호출
		}
	}

	public boolean canMove(int newX, int newY) {
		// 배열의 범위를 벗어나지 않도록 체크
		if (newX < 0 || newX >= gameMap.getWidth() || newY < 0 || newY >= gameMap.getHeight()) {
			return false; // 범위를 벗어나면 이동 불가
		}

		return gameMap.getTile(newX, newY) != 1; // 벽이 아닌 경우에만 이동 가능
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void resetPosition() {
		x = 8;
		y = 7;
	}

}