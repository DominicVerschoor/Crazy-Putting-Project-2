
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) throws Exception {
            launch(args);
    }

    @Override
    public void start(Stage mainStage) throws Exception {

        UI_Handler UI = new UI_Handler();
        mainStage = UI.getUIstage();
        mainStage.show();

    }
}
