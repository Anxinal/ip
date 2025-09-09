
import shiroha.ChatBot;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Main extends Application {

  
    /**
     *  Set up the JavaFx stage and start the chatbot by sending greet message
     */
    @Override
    public void start(Stage stage) {

        ChatBot bot = new ChatBot();      
        Scene s = bot.getUiComponent();
        stage.setTitle(bot.getName());
        stage.setResizable(false);
        stage.setMinHeight(600.0);
        stage.setMinWidth(400.0);
        stage.setScene(s); 
        stage.show(); // Render the stage.
        bot.start();
    }
    
}

