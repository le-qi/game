package game;

import java.util.*;
import javafx.animation.KeyFrame;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * This class represents the game environment.
 * 
 * @author leqi
 *
 */
public class GameWorld {

	private static final int NUM_ENEMIES = 5;
	private static final int ARENA_SIZE = 500;
	private static final String CHEAT_CODE = "CHEAT";
	
	private static final Font TITLE_FONT = Font.font("Tahoma", FontWeight.NORMAL, 32);
	private static final Font SUBTITLE_FONT = Font.font("Helvetica", FontWeight.BOLD, 16);
	private static final Color TITLE_COLOR = Color.WHITE;
	private static final Color SUBTITLE_COLOR = Color.FIREBRICK;
	private static final Color BACKGROUND_COLOR = Color.BLACK;
	
	private static final String SPLASH_TITLE = "The Lone Survivor";
	private static final String SPLASH_SUBTITLE = "Click mouse to continue...";
	private static final String BREAK_TITLE = "YOU ESCAPED!";
	private static final String BREAK_SUBTITLE = "But it's not over...";
	private static final String WIN_TITLE = "YOU'VE WON!";
	private static final String WIN_SUBTITLE = "Click to play again...";
	private static final String LOSS_TITLE = "GAME OVER.";
	private static final String LOSS_SUBTITLE = "Humanity is lost...try again?";

	private String myInputCode;
	private Stage myStage;
	private Group myGameRoot;
	private Player myPlayer;
	private ArrayList<Bullet> myBullets;
	private ArrayList<Enemy> myEnemies;
	private boolean myCheatOn;
	private boolean myGameStarted;
	private boolean mySecondRound;

	public Scene initialize(Stage s) {

		myStage = s;
		myInputCode = "";
		myGameStarted = false;
		mySecondRound = false;
		myCheatOn = false;
		myGameRoot = new Group();
		myBullets = new ArrayList<Bullet>();
		myEnemies = new ArrayList<Enemy>();

		return setSplashScreen();
	}

	/**
	 * Updates sprites every frame throughout the game.
	 * 
	 * @param	frameRate	Specifies how fast frames move.
	 * @return	New KeyFrame for next frame.
	 */
	public KeyFrame start(int frameRate) {
		return new KeyFrame(Duration.millis(1000 / frameRate),
				e -> updateSprites());
	}

	/**
	 * Returns game arena size.
	 * 
	 * @return	Size of the arena.
	 */
	public static int getArenaSize() {
		return ARENA_SIZE;
	}
	
	/**
	 * The function takes in a string and font and color styling and
	 * converts them into a decorated Text object.
	 * 
	 * @param	str		Message to be decorated.
	 * 			font	Specific font to be used.
	 * 			color	Color of the text.
	 * @return	Text object containing desired message.
	 */
	private Text decorateText(String str, Font font, Color color) {
		Text text = new Text(str);
		text.setFont(font);
		text.setFill(color);
		text.setTextAlignment(TextAlignment.CENTER);
		return text;
	}

	/**
	 * This function is used to generate a large white title display for all of
	 * the scenes in the game involving text.
	 * 
	 * @param	str	The string to be displayed in the scene.
	 * 
	 * @return	The Text to be placed as a node in a scene.
	 */
	private Text generateTitle(String str) {
		return decorateText(str, TITLE_FONT, TITLE_COLOR);
	}

	/**
	 * This function generates the red subtitle text for the various text scenes
	 * in the game.
	 * 
	 * @param	str	The string to be displayed as a subtitle.
	 * @return	The Text to be placed as a node in a scene.
	 */
	private Text generateSubtitle(String str) {
		return decorateText(str, SUBTITLE_FONT, SUBTITLE_COLOR);
	}
	
	/**
	 * 
	 * Generates an entire scene of text and sets it to the game stage
	 * given a title String and a
	 * subtitle String.
	 * 
	 * @param titleName		Message to be displayed as title.
	 * @param subtitleName	Message to be displayed as subtitle.
	 * @return				Scene containing title and subtitle nodes.
	 */
	private Scene setSceneText(String titleName, String subtitleName) {

		GridPane gp = new GridPane();
		gp.setAlignment(Pos.CENTER);
		Scene scene = new Scene(gp, ARENA_SIZE, ARENA_SIZE, BACKGROUND_COLOR);
		Text title = generateTitle(titleName);
		Text subtitle = generateSubtitle(subtitleName);
		gp.add(title, 1, 1);
		gp.add(subtitle, 1, 2);
		myStage.setScene(scene);

		return scene;
	}
	
	/**
	 * This function sets up the initial splash screen when the game is first
	 * started.
	 * 
	 * @return	The opening splash screen scene.
	 */
	private Scene setSplashScreen() {
		Scene scene = setSceneText(SPLASH_TITLE, SPLASH_SUBTITLE);
		scene.setOnMousePressed(e -> beginGame(e));
		scene.setOnKeyPressed(e -> checkCheatCode(e));
		return scene;
	}
	
	/**
	 * Set up the game scene.
	 * 
	 */
	private void setGameScene() {
		Scene gameScene = new Scene(myGameRoot, ARENA_SIZE, ARENA_SIZE,
				BACKGROUND_COLOR);
		gameScene.setOnKeyPressed(e -> myPlayer.move(e.getCode()));
		gameScene.setOnMousePressed(e -> handleMouseInput(e));
		myStage.setScene(gameScene);
	}
	
	/**
	 * Generates the scene and other conditions in between the two rounds
	 * of games.
	 */
	private void setBreakScene() {
		myGameStarted = false;
		mySecondRound = true;
		myBullets = new ArrayList<Bullet>();
		myEnemies = new ArrayList<Enemy>();
		Scene scene = setSceneText(BREAK_TITLE, BREAK_SUBTITLE);
		scene.setOnMousePressed(e -> beginGame(e));
	}

	/**
	 * Generates final end scene indicating end of the game.
	 */
	private void setEndScene(String title, String subtitle) {
		Scene scene = setSceneText(title, subtitle);
		scene.setOnMousePressed(e -> restartGame(e));
	}
	
	/**
	 * The function monitors the cheat code input at the beginning of the
	 * screen. If the cheat code is correct, then the game is started with
	 * the cheat in place. If not, it continues to wait until the correct
	 * message is input or if the game starts automatically
	 * 
	 * @param	e	KeyEvent input during splash screen. 
	 */
	private void checkCheatCode(KeyEvent e) {
		myInputCode = myInputCode.concat(e.getCode().toString());
		if (myInputCode.equals(CHEAT_CODE))
			beginGame(e);
		if (!CHEAT_CODE.startsWith(myInputCode))
			myInputCode = "";
	}
	
	/**
	 * Function sets up the initial game parameters after the player has
	 * indicated that he/she wants to play.
	 * 
	 * @param	e	InputEvent used to trigger the beginning of the game.
	 */
	private void beginGame(InputEvent e) {
		myGameStarted = true;
		myGameRoot = new Group();
		myCheatOn = e instanceof KeyEvent || myCheatOn;
		generateSprites(myCheatOn);
		setGameScene();
	}

	/**
	 * Creates Player and Enemy objects and places them onto the scene to
	 * display them.
	 * 
	 * @param	cheatOn	Indicates whether a cheat is being used.
	 */
	private void generateSprites(boolean cheatOn) {
		myPlayer = cheatOn ? new CheatPlayer() : new BasicPlayer();
		myGameRoot.getChildren().add(myPlayer.getImage());
		for (int i = 0; i < NUM_ENEMIES; i++) {
			Enemy enemy = mySecondRound ? new SmartEnemy() : new BasicEnemy();
			myGameRoot.getChildren().add(enemy.getImage());
			myEnemies.add(enemy);
		}
	}

	/**
	 * Generates Bullet objects and displays them on the scene (user fires
	 * bullets).
	 * 
	 * @param	e	MouseEvent triggering bullet firing.
	 */
	private void handleMouseInput(MouseEvent e) {
		ArrayList<Bullet> firedBullets = myPlayer.fire();
		for (Bullet b : firedBullets) {
			myGameRoot.getChildren().add(b.getImage());
			myBullets.add(b);
		}
	}

	/**
	 * Collectively updating the state of the game, moving the sprites
	 * and doing collision checking.
	 */
	private void updateSprites() {
		updateBullets();
		updateEnemies();
		checkCollisions();
	}


	/**
	 * Moves bullets for the next frame.
	 */
	private void updateBullets() {
		for (Bullet b : myBullets)
			b.move();
	}
	
	/**
	 * Moves enemies for the next frame. The way in which the enemy moves
	 * depends on which round the current game is in.
	 */
	private void updateEnemies() {
		for (Enemy e : myEnemies) {
			e.move();
			e.updateSpeed(myPlayer);
		}
	}

	/**
	 * Checks for all object-object collisions.
	 */
	private void checkCollisions() {
		checkBulletCollisions();
		checkPlayerCollisions();
		checkIfWon();
	}

	/**
	 * Checks to see if any bullets hit any enemies. If so, remove them
	 * both from the game and the screen.
	 */
	private void checkBulletCollisions() {
		for (int i = 0; i < myBullets.size(); i++) {
			for (int j = 0; j < myEnemies.size(); j++) {
				if (myEnemies.size() > 0 && myBullets.size() > 0
						&& myBullets.get(i).getImage().getBoundsInParent()
						.intersects(myEnemies.get(j).getImage().getBoundsInParent())) {
					myGameRoot.getChildren().remove(myBullets.get(i).getImage());
					myGameRoot.getChildren().remove(myEnemies.get(j).getImage());
					myBullets.remove(i);
					myEnemies.remove(j);
					i--;
					j--;
				}
			}
		}
	}
	
	/**
	 * Checks if any enemies have hit the player. If so, game over. Also
	 * checks to see if any enemies hit an edge in the arena. If so, its
	 * movement is deflected.
	 */
	private void checkPlayerCollisions() {
		for (Enemy enemy : myEnemies) {
			if (myPlayer.getImage().getBoundsInParent()
					.intersects(enemy.getImage().getBoundsInParent()))
				setEndScene(LOSS_TITLE, LOSS_SUBTITLE);
		}
	}

	/**
	 * Checks to see if the round has been won.
	 */
	private void checkIfWon() {
		if (myGameStarted && myEnemies.size() == 0) {
			if (mySecondRound)
				setEndScene(WIN_TITLE, WIN_SUBTITLE);
			else
				setBreakScene();
		}
	}

	/**
	 * Restarts parameters for another game.
	 * 
	 * @param e	Mouse click indicating another game to be played.
	 */
	private void restartGame(MouseEvent e) {
		myStage.setScene(initialize(myStage));
	}

}
