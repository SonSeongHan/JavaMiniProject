package pacmanGame;

import java.awt.*;
import java.awt.*;
import java.io.File; // 파일 받아오기
import java.util.Random;

import javax.swing.ImageIcon;

public class GameMap {

	// 맵 구조
	// 1 - 벽, 2 - 길, 2 - coin, 3 - special
	private final int[][] map;
	private ImageIcon wallImage;
	private ImageIcon roadImage;
	private ImageIcon coinImage;
	private ImageIcon specialImage;

	// 생성자 : 맵 초기화 및 이미지 로드
	public GameMap() {
		map = new int[][] { { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 0, 0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 3, 0, 1 }, { 1, 0, 1, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 1 }, { 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1 }, { 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 }, { 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1 },
				{ 1, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 1 }, { 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1 },
				{ 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1 }, { 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 1, 0, 0, 1 },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 } };

		loadImages();

		// 모든 0을 2로 바꾸기
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] == 0) {
					map[i][j] = 2; // 0을 2로 변경
				}
			}
		}

		// 랜덤으로 3개의 위치를 선택하여 3으로 바꾸기
		Random random = new Random();
		int count = 0;

		while (count < 3) {
			int randomX = random.nextInt(map.length);
			int randomY = random.nextInt(map[0].length);

			// 타일이 2인 경우에만 3으로 변경
			if (map[randomX][randomY] == 2) {
				map[randomX][randomY] = 3;
				count++;
			}
		}
	}

	// 이미지 로드
	private void loadImages() {
		wallImage = new ImageIcon("src/images/wall.png");
		roadImage = new ImageIcon("src/images/road.png");
		coinImage = new ImageIcon("src/images/coin.png");
		specialImage = new ImageIcon("src/images/special.png");
	}

	// 특정 좌표의 타일 값을 반환
	public int getTile(int x, int y) {
		if (x < 0 || x >= map[0].length || y < 0 || y >= map.length) {
			return 1;
		}
		return map[y][x];
	}

	// 특정 좌표의 타일 값을 설정
	public void setTile(int x, int y, int value) {
		map[y][x] = value;
	}

	// 이미지 번호 매겨주기
	public void draw(Graphics g) {
		for (int y = 0; y < map.length; y++) { // 맵 높이 만큼
			for (int x = 0; x < map[y].length; x++) // 맵 너비 만큼
				switch (map[y][x]) {
				case 1:
					wallImage.paintIcon(null, g, x * 40, y * 40);
					break;
				case 0:
					roadImage.paintIcon(null, g, x * 40, y * 40);
					break;
				case 2:
					coinImage.paintIcon(null, g, x * 40, y * 40);
					break;
				case 3:
					specialImage.paintIcon(null, g, x * 40, y * 40);
					break;
				}

		}
	}

	public int getWidth() {
		return map[0].length; // 열의 길이
	}

	public int getHeight() {
		return map.length; // 행의 길이
	}

	public boolean collectCoin(int x, int y) {
		int tileValue = getTile(x, y);
		if (tileValue == 2) { // 코인 먹음
			setTile(x, y, 0); // 타일을 0으로 변경 (코인 사라짐)
			return true; // 코인을 먹음
		}
		return false; // 코인을 먹지 않음
	}

	public boolean collectSpecial(int x, int y) {
		if (getTile(x, y) == 3) { // 스페셜 아이템 먹음
			setTile(x, y, 0);
			return true;
		}
		return false;
	}

	public boolean allCoinsCollected() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				if (map[i][j] == 2 || map[i][j] == 3) {
					return false;
				}
			}
		}
		return true;
	}
}