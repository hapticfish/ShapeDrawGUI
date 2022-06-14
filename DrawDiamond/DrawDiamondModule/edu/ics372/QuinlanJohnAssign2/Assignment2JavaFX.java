/*The project is intended to create a drawing area where after 2 clicks are
 * made by the mouse a diamond is draw when the "draw" button is pressed.
 * 
 * You should be able to click multiple points and the last 2 clicks will be
 * the points that are used for calculation of the diamond when drawn.
 * 
 * 
 * @author John Quinlan
 * 
 * */

package edu.ics372.QuinlanJohnAssign2;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Class, declares variables and references and implements methods from
 * EventHandler and extends Application
 * 
 * @param none
 * @returns N/A
 *
 */
public class Assignment2JavaFX extends Application implements EventHandler<ActionEvent> {
	private Button drawButton = new Button("Draw");
	private Button exitButton = new Button("Exit");
	private Canvas canvas = new Canvas(400, 400);
	private Point2D point1;
	private Point2D point2;
	private Point2D point3;
	private Point2D point4;
	private double absoluteX;
	private double absoluteY;
	private int clickCount;

	/**
	 * Constructs Stage and formats pane with buttons and canvas.
	 * 
	 * @param Stage
	 * @returns void
	 *
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		GridPane pane = new GridPane();
		GridPane buttonPane = new GridPane();
		buttonPane.add(drawButton, 0, 0);
		buttonPane.add(exitButton, 1, 0);
		pane.add(buttonPane, 0, 1);
		pane.add(canvas, 0, 0);
		Scene scene = new Scene(pane);
		primaryStage.setTitle("PA2");
		primaryStage.setScene(scene);
		drawButton.setOnAction(this);
		exitButton.setOnAction(this);
		canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {

			/**
			 * Listens for MounseEvents and collects the 2 mouse inputs to be later
			 * calculated and with the other points 3 and 4.
			 * 
			 * @param MouseEvent
			 * @returns void
			 *
			 */
			@Override
			public void handle(MouseEvent event) {
				clickCount++;
				if ((clickCount % 2) == 1) {
					point1 = new Point2D(event.getSceneX(), event.getSceneY());
				} else {
					point2 = new Point2D(event.getSceneX(), event.getSceneY());
				}
			}
		});

		primaryStage.show();
	}// end start stage

	/**
	 * Draws the line on the Graphics Context canvas object with strokeLine method
	 * 
	 * @param Point2D, Point2D
	 * @returns void
	 */
	public void draw(Point2D point1, Point2D point2) {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		calculatePoints();

		gc.strokeLine(point1.getX(), point1.getY(), point2.getX(), point2.getY());
		gc.strokeLine(point2.getX(), point2.getY(), point3.getX(), point3.getY());
		gc.strokeLine(point3.getX(), point3.getY(), point4.getX(), point4.getY());
		gc.strokeLine(point4.getX(), point4.getY(), point1.getX(), point1.getY());

		clickCount = 0;
		point1 = null;
		point2 = null;
		point3 = null;
		point4 = null;
	}// end draw

	/**
	 * Calculates the remaining points for drawing the diamond and handles cases for
	 * different point clicks on graphics context in relation to each other.
	 *
	 * @param none
	 * @returns void
	 */
	public void calculatePoints() {

		// calculating variable for point 3 and 4 calculation
		absoluteX = Math.abs(point2.getX() - point1.getX());
		absoluteY = Math.abs(point2.getY() - point1.getY());
		// Calculate Points 3 and 4
		// case 1: Point 2 down and to the left
		if (point2.getX() < point1.getX() && point2.getY() > point1.getY()) {
			point3 = new Point2D(point1.getX(), point2.getY() + absoluteY);
			point4 = new Point2D(point1.getX() + absoluteX, point2.getY());
		}
		// case 2: Point 2 up and to the right
		if (point2.getX() > point1.getX() && point2.getY() < point1.getY()) {
			point3 = new Point2D(point2.getX() + absoluteX, point1.getY());
			point4 = new Point2D(point2.getX(), point1.getY() + absoluteY);
		}
		// case 3: Point 2 up and to the left
		if (point2.getX() < point1.getX() && point2.getY() < point1.getY()) {
			point3 = new Point2D(point2.getX() - absoluteX, point1.getY());
			point4 = new Point2D(point2.getX(), point1.getY() + absoluteY);
		}
		// case 4: Point 2 down and to the right
		if (point2.getX() > point1.getX() && point2.getY() > point1.getY()) {
			point3 = new Point2D(point1.getX(), point2.getY() + absoluteY);
			point4 = new Point2D(point1.getX() - absoluteX, point2.getY());
		}
	}// end calculate points

	/**
	 * Handles button clicks for "drawButton" and "Exit"
	 * 
	 * @param ActionEvent
	 * @returns void
	 *
	 */
	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() == drawButton) {
			draw(point1, point2);
		} else if (event.getSource() == exitButton) {
			System.exit(0);
		}
	}

	/**
	 * Driver for launching the JavaFX program.
	 * 
	 * @param String[]
	 * @returns void
	 *
	 */
	public static void main(String[] args) {
		Application.launch(null);
	}// end main

}
