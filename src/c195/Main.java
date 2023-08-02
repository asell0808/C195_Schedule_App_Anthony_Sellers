package c195;

import c195.dao.SQLDBService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    @Override

/**
 *
 * Start
 *
 * @param primaryStage  the primary stage.
 * @throws   Exception
 */
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("view/login-view.fxml")));
        primaryStage.setTitle("Schedule App");
        primaryStage.setScene(new Scene(root, 300, 375));
        primaryStage.show();
    }

    public interface loginInterfaceOne{
        String getSuccessful(String s);
    }

    public interface loginInterfaceTwo{
        String getUnsuccessful(String u);
    }


    /**
     *
     * Main
     *
     * @param args  the args.
     */
    public static void main(String[] args) {

        SQLDBService.connect();
        launch(args);
    }

}
