package pacmanGame;

public class GameThread implements Runnable {

	private GamePanel gamePanel;

	// 생성자
	public GameThread(GamePanel panel) {
		gamePanel = panel; // 게임 패널 초기화
	}

	// 스레드 실행 메소드
	public void run() {
		while (true) {
			try {
				Thread.sleep(100); // 100ms 대기
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
