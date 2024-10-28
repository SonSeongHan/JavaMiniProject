package pacmanGame;

// 스윙 GUI를 이용하기 위한 패키지 import
import javax.swing.*;

// JFrame을 이용한 맵 구성
public class PacmanGameEx extends JFrame {

	// 게임 패널을 담기 위한 변수 생성
	private GamePanel gamePanel;

	// 생성자
	public PacmanGameEx() {
		setTitle("Pacman Game");
		setSize(800, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE); // 창 닫기 시 프로그램 종료
		gamePanel = new GamePanel(); // 게임 패널 초기화
		add(gamePanel); // JFrame에서 프레임에 컴포넌트 추가하는 방법
		setVisible(true); // 창 보이게 설정
	}

	public static void main(String[] args) {
		new PacmanGameEx(); // 객체 생성 -> 게임 시작
	}

}
