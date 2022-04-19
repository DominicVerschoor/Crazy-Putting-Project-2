
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;
import org.mariuszgromada.math.mxparser.Function;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;

public class gameField {
    //INITIALIZING OTHER CLASSES
    UI_Handler UI = new UI_Handler();
    fileReader read = new fileReader();

    //EULER RELATED
    private double[] newArrXt = new double[4];
    private double g = 9.81;          // gravity;
    private double x = read.x0;       // x-coordinate of the initial position
    private double y = read.y0;       // y-coordinate of the initial position
    private double uk = read.muk;     // kinetic friction coefficient of grass
    private double us = read.mus;     // static friction coefficient of grass
    private final double h = 0.01;   //step of Euler

    //BASIC SETTINGS
    int rBall = 5;
    int counter = 0;

    public boolean SpacekeyPressed;
    public boolean escapePress = false;

    //VIEW RELATED
    Text score = new Text();
    Text xCor = new Text();
    Text yCor = new Text();

    TextField velX = new TextField();
    TextField velY = new TextField();

    public AnchorPane fieldPane;
    public Stage fieldStage;
    public Scene fieldScene;
    public Stage menuStage;
    public Circle ball = new Circle();
    public Circle hole;

    //CONSTRUCTOR
    public gameField() {
        constructStage();
        drawMap();
        golfBall();
        keyListenersManager();
        text();
        holeCreator();
    }


    /**
     * creating the GUI golf ball representation
     */

    public void golfBall() {
        TranslateTransition transition = new TranslateTransition();
        ball.setCenterX(x);
        ball.setCenterY(y);
        ball.setRadius(rBall);
        ball.setFill(Color.GHOSTWHITE);
        transition.setNode(ball);
        transition.setDuration(Duration.seconds(2));
        fieldPane.getChildren().add(ball);
    }

    /**
     * creating the GUI hole representation
     */

    public void holeCreator() {
        Circle hole = new Circle();
        this.hole = hole;

        hole.setCenterX(read.xt);
        hole.setCenterY(read.yt);
        hole.setRadius(read.r);
        hole.setFill(Color.SADDLEBROWN);
        fieldPane.getChildren().add(hole);
    }

    /**
     * creating the screen
     */

    public void constructStage() {
        fieldPane = new AnchorPane();
        fieldScene = new Scene(fieldPane, UI.widthScreen, UI.heightScreen);
        fieldStage = new Stage();
        fieldStage.setScene(fieldScene);

        //fieldPane.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    /**
     * Method for hiding the main menu stage and opening the game stage
     *
     * @param mainMenuStage the stage from UI class
     */

    public void fieldStage(Stage mainMenuStage) {
        this.menuStage = mainMenuStage;
        mainMenuStage.hide();
        fieldStage.show();
    }


    /**
     * setting actions for pressed keys
     */
    public void keyListenersManager() {
        fieldScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ESCAPE) {
                    escapePress = true;
                    UI.exitGame(fieldStage);
                }
            }
        });

    }

    /**
     * receiving color for a specific place on the game field
     *
     * @param height - map slopes
     * @return Color of the field
     */

    public Color mapColor(Double height) {
        if (height >= -10 && height <= 10) {
            for (double g = 255, i = -10, j = i + 0.1; i < 9.9 && g > 30; g -= 2, i += 0.1, j += 0.1) {
                if (height > i && height < j) {
                    return Color.rgb(0, (int) g, 0);
                }
            }
        }
        return Color.rgb(0, 25, 0);
    }

    /**
     * drawing the game field map
     */
    public void drawMap() {

        Function f = new Function(read.function);

        for (int X = 0; X < 864; X += 864 / 80) {
            for (int Y = 0; Y < 672; Y += 672 / 80) {

                String xs = "x = " + X;
                String ys = "y = " + Y;
                Argument x = new Argument(xs);
                Argument y = new Argument(ys);

                Expression e = new Expression(read.function, x, y);
                Rectangle rectangle = new Rectangle();
                rectangle.setX(X);
                rectangle.setY(Y);
                rectangle.setWidth(864 / 80);
                rectangle.setHeight(672 / 80);
                rectangle.setFill(mapColor(e.calculate()));
                fieldPane.getChildren().add(rectangle);
//                    read.function.
            }
        }

    }

    /**
     * Setting up the playing game screen layout
     */
    public void text() {

        score.setText("Score : " + counter);
        score.setFont(Font.font("arial", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 35));
        score.setFill(Color.WHITE);
        score.setStroke(Color.BLACK);
        score.setLayoutX(810 - 120);
        score.setLayoutY(35);
        fieldPane.getChildren().addAll(score);

        xCor.setText("X : " + (int) ball.getCenterX());
        xCor.setFont(Font.font("arial", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 35));
        xCor.setFill(Color.WHITE);
        xCor.setStroke(Color.BLACK);
        xCor.setLayoutX(810 - 65);
        xCor.setLayoutY(35 + 35);
        fieldPane.getChildren().addAll(xCor);

        yCor.setText("Y : " + (int) ball.getCenterY());
        yCor.setFont(Font.font("arial", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 35));
        yCor.setFill(Color.WHITE);
        yCor.setStroke(Color.BLACK);
        yCor.setLayoutX(810 - 65);
        yCor.setLayoutY(35 + 70);
        fieldPane.getChildren().addAll(yCor);

        velX.setTranslateX(40);
        velX.setTranslateY(UI.heightScreen - 150);
        velX.setText("0");

        velY.setTranslateX(40);
        velY.setTranslateY(UI.heightScreen - 110);
        velY.setText("0");


        fieldPane.getChildren().add(velX);
        fieldPane.getChildren().add(velY);

        Text x = new Text();
        Text y = new Text();

        x.setText("x : ");
        x.setFont(Font.font("arial", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 20));
        x.setFill(Color.WHITE);
        x.setStroke(Color.BLACK);
        x.setLayoutX(15);
        x.setLayoutY(UI.heightScreen - 133);

        y.setText("y : ");
        y.setFont(Font.font("arial", FontWeight.EXTRA_BOLD, FontPosture.REGULAR, 20));
        y.setFill(Color.WHITE);
        y.setStroke(Color.BLACK);
        y.setLayoutX(15);
        y.setLayoutY(UI.heightScreen - 93);

        fieldPane.getChildren().add(x);
        fieldPane.getChildren().add(y);


        Button go = new Button();

        Image goImg = new Image("Assets/go.png", 80, 30, false, true);
        ImageView goView = new ImageView(goImg);

        go.setStyle("-fx-background-color: transparent");
        go.setGraphic(goView);
        go.setLayoutX(184);
        go.setLayoutY(UI.heightScreen - 137);
        go.setCursor(Cursor.CLOSED_HAND);
        go.setEffect(new DropShadow());

        go.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                counter++;
                newArrXt[0] = ball.getCenterX();
                newArrXt[1] = ball.getCenterY();

                newArrXt[2] = Double.parseDouble(velX.getText());
                newArrXt[3] = Double.parseDouble(velY.getText());
                changeScore();
                Euler(newArrXt);
                xCor.setText("X : " + (int) ball.getCenterX());
                yCor.setText("Y : " + (int) ball.getCenterY());
            }
        });

        fieldPane.getChildren().add(go);
    }

    /**
     * Changing the score counter during the game
     */
    public void changeScore() {
        score.setText("Score : " + counter);
    }

    PartialDerivate derivate = new PartialDerivate();

    public void Euler(double[] arrXt) {


        //when the velocity of x, y are not 0, cuz its quite impossible to get them to 0,
        // so we take the little range 0.1 instead
        while (Math.abs(arrXt[2]) > 0.1 || Math.abs(arrXt[3]) > 0.1) {
            newArrXt[0] = arrXt[0] + h * arrXt[2];     //position + step * velocity --> get the new position X
            newArrXt[1] = arrXt[1] + h * arrXt[3];     //position + step * velocity --> get the new position Y

            double partialX = derivate.derivateX(arrXt[0], arrXt[1]);    //the partial derivative of X
            double partialY = derivate.derivateY(arrXt[0], arrXt[1]);    //the partial derivative of Y

            //the second-order derivative equation, which is the acceleration of X, Y
            double accX = -g * partialX - uk * g * arrXt[2] / Math.sqrt(arrXt[2] * arrXt[2] + arrXt[3] * arrXt[3]);
            double accY = -g * partialY - uk * g * arrXt[3] / Math.sqrt(arrXt[2] * arrXt[2] + arrXt[3] * arrXt[3]);

            newArrXt[2] = arrXt[2] + h * accX;        //X velocity + step * acceleration --> new velocity
            newArrXt[3] = arrXt[3] + h * accY;        //Y velocity + step * acceleration --> new velocity


            for (int i = 0; i < arrXt.length; i++) {
                arrXt[i] = newArrXt[i];              //clone newArrXt back to arrXt
            }

            //when the ball velocity equals to 0 (smaller than range 0.1 in this case)
            //and the partial derivative of X and Y is not equal to 0
            //it means the ball stop on a slope,

            // then if the static friction bigger than downhill force,
            // means the ball really stop, then break the loop;
            // if not, means the the ball will keep moving then change the friction part of the original equation
            // and go back the loop
            if ((Math.abs(arrXt[2]) <= 0.1 && Math.abs(arrXt[3]) <= 0.1) && (Math.abs(partialX) > 0.1 || Math.abs(partialY) > 0.1)) {
                if (us > Math.sqrt(partialX * partialX + partialY * partialY)) {
                    break;
                } else {
                    arrXt[2] = h * -g * partialX + uk * g * partialX / Math.sqrt(partialX * partialX + partialY * partialY);
                    arrXt[3] = h * -g * partialY + uk * g * partialY / Math.sqrt(partialX * partialX + partialY * partialY);

                }
            }

        }
        double distance = Math.sqrt(Math.pow(arrXt[0] - hole.getCenterX(), 2) + Math.pow(arrXt[1] - hole.getCenterY(), 2));
        if ((hole.getRadius() > ball.getRadius() + distance)) {

            System.out.println("win");

            Alert popWindow = new Alert(Alert.AlertType.INFORMATION);
            popWindow.setTitle("WIN");
            popWindow.setHeaderText("YOU FINISHED WITH SCORE " + counter);
            UI.exitGame(fieldStage);
            popWindow.show();

        }

        ball.setCenterX(arrXt[0]);
        ball.setCenterY(arrXt[1]);
        System.out.println("X: " + arrXt[0]);
        System.out.println("Y: " + arrXt[1]);
    }

/*
%Second order Runge-Kutta: Ralston's Method
f = @(t,y)(sin(t) + y - y^3); %ODE equation
hs = 0.1; %step size
wi = 2; %initial y

step = 0;
for t = 0.1:hs:0.2 %only do 2 steps
    counter = counter + 1;
    hki1 = hs * f(t,wi);
    hki2 = hs * f(t + (3/4 * hs), wi + (3/4 * hki1 * hs));
    wi = wi + (1/4) * hs * ((1/3) hki1 + (2/3 * hki2)); %change wi for next iteration
    solutions(counter) = wi; %store solutions
end
 */


    public void RungeKuttaSecondOrder(double[] arrXt) {


        //when the velocity of x, y are not 0, cuz its quite impossible to get them to 0,
        // so we take the little range 0.1 instead
        while (Math.abs(arrXt[2]) > 0.1 || Math.abs(arrXt[3]) > 0.1) {
            newArrXt[0] = arrXt[0] + h * arrXt[2];     //position + step * velocity --> get the new position X
            newArrXt[1] = arrXt[1] + h * arrXt[3];     //position + step * velocity --> get the new position Y

            double partialX = derivate.derivateX(arrXt[0], arrXt[1]);    //the partial derivative of X
            double partialY = derivate.derivateY(arrXt[0], arrXt[1]);    //the partial derivative of Y

            //the second-order derivative equation, which is the acceleration of X, Y
            double accX = -g * partialX - uk * g * arrXt[2] / Math.sqrt(arrXt[2] * arrXt[2] + arrXt[3] * arrXt[3]);
            double accY = -g * partialY - uk * g * arrXt[3] / Math.sqrt(arrXt[2] * arrXt[2] + arrXt[3] * arrXt[3]);

            double posK1X = h * arrXt[2];
            double posK1Y = h * arrXt[3];

            double posK2X = h * arrXt[2];
            double posK2Y = h * arrXt[3];

            newArrXt[2] = arrXt[2] + h * accX;        //X velocity + step * acceleration --> new velocity
            newArrXt[3] = arrXt[3] + h * accY;        //Y velocity + step * acceleration --> new velocity

            for (int i = 0; i < arrXt.length; i++) {
                arrXt[i] = newArrXt[i];              //clone newArrXt back to arrXt
            }
        }

        double distance = Math.sqrt(Math.pow(arrXt[0] - hole.getCenterX(), 2) + Math.pow(arrXt[1] - hole.getCenterY(), 2));
        if ((hole.getRadius() > ball.getRadius() + distance)) {

            System.out.println("win");

            Alert popWindow = new Alert(Alert.AlertType.INFORMATION);
            popWindow.setTitle("WIN");
            popWindow.setHeaderText("YOU FINISHED WITH SCORE " + counter);
            UI.exitGame(fieldStage);
            popWindow.show();

        }

        ball.setCenterX(arrXt[0]);
        ball.setCenterY(arrXt[1]);
        System.out.println("X: " + arrXt[0]);
        System.out.println("Y: " + arrXt[1]);
    }
}
