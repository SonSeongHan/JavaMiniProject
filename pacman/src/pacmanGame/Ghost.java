package pacmanGame;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

class Ghost {

	private int x;
	private int y;
	private Image image; // 적 이미지
	private GameMap gameMap; // 맵 흐르에 따른 변화 감지
	private String lastPosition = ""; // 마지막 위치 -> 백스텝 방지용
	private int moveCount = 0; // 주기적 방향 전환

	// 생성자
	public Ghost(int startX, int startY, GameMap gameMap) {
		x = startX;
		y = startY;
		this.gameMap = gameMap;
		loadImages();
	}

	private void loadImages() {
		image = new ImageIcon("src/images/ghost.gif").getImage();
	}

	// 적 그리기 메소드
	public void draw(Graphics g) {
		g.drawImage(image, x * 40, y * 40, 40, 40, null); // x, y 좌표에 이미지 그리기
	}

	// 유령 위치 반환
	public Point getPosition() {
		return new Point(x, y);
	}

	// 유령 이동 메소드
	public void move() {
		// 무작위 방향을 선택하기 위한 리스트
		List<String> possibleDirections = new ArrayList<>();

		// 각 방향에 대해 이동 가능한지 체크하고 이전 방향과 다르면 추가
		if (canMove(x, y - 1) && !lastPosition.equals("down")) {
			possibleDirections.add("up");
		}
		if (canMove(x, y + 1) && !lastPosition.equals("up")) {
			possibleDirections.add("down");
		}
		if (canMove(x - 1, y) && !lastPosition.equals("right")) {
			possibleDirections.add("left");
		}
		if (canMove(x + 1, y) && !lastPosition.equals("left")) {
			possibleDirections.add("right");
		}

		// 이동할 수 있는 방향이 있다면
		if (!possibleDirections.isEmpty()) {
			// 랜덤으로 방향 선택
			String randomDirection = possibleDirections.get((int) (Math.random() * possibleDirections.size()));

			// 선택한 방향에 따라 위치 업데이트
			switch (randomDirection) {
			case "up":
				y--; // y 좌표 감소 (위로 이동)
				break;
			case "down":
				y++; // y 좌표 증가 (아래로 이동)
				break;
			case "left":
				x--; // x 좌표 감소 (왼쪽으로 이동)
				break;
			case "right":
				x++; // x 좌표 증가 (오른쪽으로 이동)
				break;
			}

			lastPosition = randomDirection; // 현재 방향 저장
			moveCount = 0; // 이동 카운트 초기화
		} else {
			// 가능한 방향이 없으면 카운트 증가
			moveCount++;
			if (moveCount > 5) { // 특정 카운트 이상일 경우
				// 무작위 방향으로 변경
				String[] directions = { "up", "down", "left", "right" };
				String randomDirection = directions[(int) (Math.random() * directions.length)];

				// 방향에 따라 위치 업데이트
				switch (randomDirection) {
				case "up":
					if (canMove(x, y - 1))
						y--;
					break;
				case "down":
					if (canMove(x, y + 1))
						y++;
					break;
				case "left":
					if (canMove(x - 1, y))
						x--;
					break;
				case "right":
					if (canMove(x + 1, y))
						x++;
					break;
				}
				moveCount = 0; // 이동 카운트 초기화
			}
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
		return x; // 현재 x 좌표 반환
	}

	public int getY() {
		return y; // 현재 y 좌표 반환
	}
}