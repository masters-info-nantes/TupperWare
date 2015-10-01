package fr.alma.middleware.view;

import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;
import fr.alma.middleware.controller.ClientController;


public class ClientView extends Application{

	private TabPane tabPane;
	private Tab newTab;
	private ClientController controller;
	private TextArea textArea;
	
	
	public Parent createContent(){
		
		tabPane = new TabPane();
		Tab tab1 = new Tab("Sport");
		Tab tab2 = new Tab("Musique");
		Tab tab3 = new Tab("Cinéma");
		newTab = new Tab();
		newTab.setClosable(false);
		
		//Open the first topic in the list of subscribing
		textArea = new TextArea(controller.getLogsContent(0));
		
		newTab.setOnSelectionChanged(new EventHandler<Event>() {
			
			@Override
			public void handle(Event arg0) {
				if(newTab.isSelected() == true){
					//Open Dialog for topic creation
					createNewTopicChan();
				}
				
			}
		});
		
		tabPane.getTabs().add(newTab);
		
		
		
		return tabPane;
	}

	
	//If the topic is not already create 
		//create the topic (on the server) and redirect the user into it
	//else
		//join the topic
	public void createNewTopicChan(){
		int index = tabPane.getTabs().size()-1;
		String topicName = "New Topic";
		
		Tab newTab = new Tab(topicName);
		VBox vbox = new VBox(2);
		TextArea newTextArea = new TextArea();
		newTextArea.setEditable(false);
		TextField newTextField = new TextField();
		
		vbox.getChildren().add(newTextArea);
		vbox.getChildren().add(newTextField);
		
		/*try {
			controller.getISF().proposeSujet(topicName, null);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
		
		tabPane.getTabs().add(index, newTab);
		newTab.setContent(vbox);
		selectionModel.select(newTab);
		
	}
	
	
	public void refreshTopics(){
		//Retrieve all the topics already created
		controller.getExistingTopics();
	}
	
	public void refreshTextArea(){
		textArea.setText(controller.getLogsContent(controller.getCurrentTab()));
	}
	
	
	public void dialogBox(){
		// Create the custom dialog.
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Login Dialog");
		dialog.setHeaderText("Look, a Custom Login Dialog");

		// Set the icon (must be included in the project).

		// Set the button types.
		ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField username = new TextField();
		username.setPromptText("Username");
		PasswordField password = new PasswordField();
		password.setPromptText("Password");

		grid.add(new Label("Username:"), 0, 0);
		grid.add(username, 1, 0);
		grid.add(new Label("Password:"), 0, 1);
		grid.add(password, 1, 1);

		// Enable/Disable login button depending on whether a username was entered.
		Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
		loginButton.setDisable(true);

		// Do some validation (using the Java 8 lambda syntax).
		username.textProperty().addListener((observable, oldValue, newValue) -> {
		    loginButton.setDisable(newValue.trim().isEmpty());
		});

		dialog.getDialogPane().setContent(grid);

		// Request focus on the username field by default.
		Platform.runLater(() -> username.requestFocus());

		// Convert the result to a username-password-pair when the login button is clicked.
		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == loginButtonType) {
		        return new Pair<>(username.getText(), password.getText());
		    }
		    return null;
		});

		Optional<Pair<String, String>> result = dialog.showAndWait();

		result.ifPresent(usernamePassword -> {
		    System.out.println("Username=" + usernamePassword.getKey() + ", Password=" + usernamePassword.getValue());
		});
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
            controller = new ClientController();
    		refreshTopics();
    		dialogBox();
            primaryStage.setScene(new Scene(createContent()));
            primaryStage.show();
            
            System.out.println("Connection to the server");
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}


}
