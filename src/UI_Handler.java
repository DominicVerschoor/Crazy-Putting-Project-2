import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.awt.*;

import static javafx.scene.Cursor.HAND;

public class UI_Handler {

    //BUTTONS RELATED
    private Image newGameImage= new Image("Assets/newGame.png",150,60,false,true);
    private ImageView newgameView = new ImageView(newGameImage);
    private Image optionsImage = new Image("Assets/options.png",150,50,false,true);
    private ImageView optionsView = new ImageView(optionsImage);
    public Image mainExitImage = new Image("Assets/Exit_Button.png");
    private ImageView mainExitImageView = new ImageView(mainExitImage);

    public Button newgame = new Button();
    public Button optoins = new Button();
    public Button mainExit = new Button();

    //SCREEN RELATED
    public int heightScreen = 672; // max screen height
    public int widthScreen = 864; // max screen width

    //VIEW SETTINGS
    private AnchorPane UIpane;
    private Stage UIstage;
    private Scene UIscene;
    public Stage exitStage;

    //CONSTRUCTOR
    public UI_Handler(){
        UIpane = new AnchorPane();
        UIstage = new Stage();
        UIscene = new Scene(UIpane,widthScreen,heightScreen);
        UIstage.setScene(UIscene);
        newGameButton();
        menuBackground();
    }

    /**
        Constructing buttons
     */
    public void newGameButton(){
        newgame.setStyle("-fx-background-color: transparent");
        newgame.setGraphic(newgameView);
        newgame.setLayoutX(50);
        newgame.setLayoutY(200);
        newgame.setCursor(Cursor.CLOSED_HAND);
        newgame.setEffect(new DropShadow());
        newgame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                gameField green = new gameField();
                green.fieldStage(UIstage);
            }
        });

        optoins.setStyle("-fx-background-color: transparent");
        optoins.setGraphic(optionsView);
        optoins.setLayoutX(36);
        optoins.setLayoutY(260);
        optoins.setCursor(Cursor.CLOSED_HAND);
        optoins.setEffect(new DropShadow());
        optoins.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent arg0) {
                optionsMenu settings = new optionsMenu();
                settings.fieldStage(UIstage);   
            }
            
        });
        mainExit.setStyle("-fx-background-color: transparent");
        mainExit.setGraphic(mainExitImageView);
        mainExit.setLayoutX(widthScreen-100);
        mainExit.setLayoutY(heightScreen-70);
        mainExit.setCursor(Cursor.CLOSED_HAND);
        mainExit.setEffect(new DropShadow());
        mainExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                UIstage.close();
            }
        });
        
        UIpane.getChildren().add(mainExit);
        UIpane.getChildren().add(newgame);
        UIpane.getChildren().add(optoins);
    }

    /**
     * Method for exiting different class stages and opening UI class stage
     * @param e different class stage
     */
        public void exitGame(Stage e){
            this.exitStage = e;
            e.close();
            UIstage.show();
        }

    //GETTER METHOD FOR STAGE (IN ORDER TO USE AS MA SCENE IN APP CLASS)
    public Stage getUIstage(){
        return UIstage;
    }

    /**
     * setting the background for the main menu screen
     */
    public void menuBackground(){
        Image backgroundImage = new Image("Assets/menu.gif");
        BackgroundSize size = new BackgroundSize(widthScreen, heightScreen, false, false, true, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,size);
        UIpane.setBackground(new Background(background));
    }
}
