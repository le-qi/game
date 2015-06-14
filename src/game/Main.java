package game;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.*;

/**
 * This program implements a simple top down scrolling shooter game. As
 * the player (the blue circle) the object of the game is to eliminate all 
 * of the werewolves (represented as red circles) by firing a gun at them. 
 * The arrow keys are used to move while the mouse key is used to fire. 
 * There are two separate rounds of the game. In the first round, they 
 * move randomly and bounce off the edges. In the second round, the 
 * werewolves follow you. If any of them come in contact with you, the 
 * game is over and you lose. A cheat code can be used at the beginning of 
 * the screen. If "cheat" is typed at the splash screen, the game will 
 * start automatically and the mouse click will cause three bullets to 
 * fire instead of one, making the game significantly easier.
 *
 * @author leqi
 */
public class Main extends Application {

	private static final int NUM_FRAMES_PER_SECOND = 60;

	private GameWorld myGame;

	public void start(Stage stage) {

		stage.setTitle("The Lone Survivor");
		stage.setResizable(false);
		myGame = new GameWorld();
		myGame.initialize(stage);
		stage.show();

		// game loop
		KeyFrame frame = myGame.start(NUM_FRAMES_PER_SECOND);
		Timeline animation = new Timeline();
		animation.setCycleCount(Animation.INDEFINITE);
		animation.getKeyFrames().add(frame);
		animation.play();

	}

	public static void main(String[] args) {
		launch(args);
	}
}
