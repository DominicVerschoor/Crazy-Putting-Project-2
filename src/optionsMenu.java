

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.event.EventHandler;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;


public class optionsMenu
{
    //MUSIC RELATED
    public static Clip clip;
    boolean musicPlaying=false;

    //VIEW RELATED
    Stage menuStage;
    boolean exitPressed=false;

    //OTHER CLASSES
    UI_Handler UI=  new UI_Handler();

    //BUTTONS RELATED
    private ImageView view = new ImageView(UI.mainExitImage);
    Image onImg = new Image("Assets/on.png");
    ImageView onView = new ImageView(onImg);
    Image offImg = new Image("Assets/off.png");
    ImageView offView = new ImageView(offImg);

    Button exit = new Button();
    Button on = new Button();
    Button off = new Button();
    AnchorPane pane;
    Stage stage;
    Scene scene;

    public optionsMenu()
    {
       stageCreater();
       optionsBackground();
       buttonCreater();
    }

    /**
     * Creating the option stage
     */
    public void stageCreater()
    {
        pane = new AnchorPane();
        stage = new Stage();
        scene = new Scene(pane, UI.widthScreen, UI.heightScreen);

        stage.setScene(scene);
    }

    /**
     * Creating buttons
     */
    public void buttonCreater()
    {
        Image musicImg = new Image("Assets/Music.png");
        ImageView musicView = new ImageView(musicImg);
        musicView.setEffect(new DropShadow());
        musicView.setLayoutX(UI.heightScreen-445);
        musicView.setLayoutY(UI.widthScreen-590);
        pane.getChildren().add(musicView);

        exit.setStyle("-fx-background-color: transparent");
        exit.setGraphic(view);
        exit.setLayoutX(UI.widthScreen-100);
        exit.setLayoutY(UI.heightScreen-70);
        exit.setCursor(Cursor.CLOSED_HAND);
        exit.setEffect(new DropShadow());
        exit.setOnAction(new javafx.event.EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                    exitPressed = true;
                    UI.exitGame(stage);
            }
            
        });

        on.setStyle("-fx-background-color: transparent");
        on.setGraphic(onView);
        on.setLayoutX(UI.widthScreen-650);
        on.setLayoutY(UI.heightScreen-350);
        on.setCursor(Cursor.CLOSED_HAND);
        on.setEffect(new DropShadow());
        on.setOnAction(new javafx.event.EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (musicPlaying==false) {
                    System.out.println("in if stat");
                    playMelody();
                } else
                    System.out.println("Notthing");
                    return;
            }
        });

        off.setStyle("-fx-background-color: transparent");
        off.setGraphic(offView);
        off.setLayoutX(UI.widthScreen-600);
        off.setLayoutY(UI.heightScreen-350);
        off.setCursor(Cursor.CLOSED_HAND);
        off.setEffect(new DropShadow());
        off.setOnAction(new javafx.event.EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                pauseMelody();
                musicPlaying=false;
            }
        });


        pane.getChildren().add(exit);
        pane.getChildren().add(on);
        pane.getChildren().add(off);
    }

    /**
     * Method for exiting different class stages and opening UI class stage
     * @param mainMenuStage different class stage
     */
    public void fieldStage(Stage mainMenuStage){
        this.menuStage=mainMenuStage;
        mainMenuStage.close();
        stage.show();
    }

    /**
     * setting up the background for the option screen
     */
    public void optionsBackground(){
        Image backgroundImage = new Image("Assets/menu.gif");
        BackgroundSize size = new BackgroundSize(1000, 800, false, false, true, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,size);
        pane.setBackground(new Background(background));
    }

    /**
     * setting actions for pressed keys
     */
    public void keyListenersOptions(){

        scene.setOnKeyPressed((new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode()== KeyCode.ESCAPE){
                    exitPressed = true;
                    //System.out.println("kezcode");
                    UI.exitGame(stage);
                }
            }
        }));
    }

    /**
     * Pausing the music
     */
    public  void pauseMelody() {
        if ( musicPlaying = true) {
            clip.stop();
        } else
            return;
    }

    /**
     * Playing the music
     */
    public static void playMelody() {
        try {
            System.out.println("im here");
            File melody = new File("/Users/nataliaczaban/Desktop/Golf2D/src/Assets/melody.wav");
            if (melody.exists()) {
                AudioInputStream melodyInput = AudioSystem.getAudioInputStream(melody);
                clip = AudioSystem.getClip();
                clip.open(melodyInput);
                clip.start();
                clip.loop(clip.LOOP_CONTINUOUSLY);
            } else {
                System.out.println("smth went wrong");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
