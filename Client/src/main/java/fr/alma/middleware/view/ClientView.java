package fr.alma.middleware.view;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Optional;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Pair;
import fr.alma.middleware.controller.ClientController;


public class ClientView extends Application{

	private TabPane tabPane;
	private ClientController controller;
	private TextArea textArea;
	private TextField textField;
	private ListView<String> listView;
	private Button refresh;
	private Button newTopic;
	private ObservableList<String> items;


	public Parent createContent(){

		VBox mainBox = new VBox();
		HBox centerBox = new HBox();
		VBox leftBox = new VBox();
		VBox rightBox = new VBox();
		HBox bottomRightBox = new HBox();



		textArea = new TextArea();
		textArea.setEditable(false);
		textField = new TextField();
		listView = new ListView<>();
		refresh = new Button("Refresh");
		newTopic = new Button("New Topic");
		tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

		leftBox.getChildren().addAll(textArea, textField);
		bottomRightBox.getChildren().addAll(refresh, newTopic);
		rightBox.getChildren().addAll(listView, bottomRightBox);




		centerBox.getChildren().addAll(leftBox, rightBox);

		mainBox.getChildren().addAll(tabPane, centerBox);
		//String firstSubcriptionTopic = controller.getSubcriptionByIndex(0);

		//Open the first topic in the list of subscribing
		//textArea = new TextArea(controller.getLogsContent(firstSubcriptionTopic));

		createListener();

		return mainBox;
	}


	public <T> void createListener(){
		newTopic.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				createNewTopic();

			}
		});

		refresh.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				refreshTopics();
			}
		});


		textField.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent ke) {

				if(ke.getCode() == KeyCode.ENTER){
					//controller.setCurrentTopic(topicName);
					controller.write(textField.getText(), controller.getCurrentTopicName());
					textField.setText("");
					updateTextArea();
					System.out.println("LOGS CONTENT ON :"+controller.getCurrentTopicName());
					System.out.println(controller.getLogsContent(controller.getCurrentTopicName()));
					System.out.println("LOGS CONTENT END");
				}

			}
		});



		listView.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(event.getButton() == MouseButton.PRIMARY){
					if(event.getClickCount() == 2){
						try {
							String topic = listView.getSelectionModel().getSelectedItem();
							checkSubscriptionOn(topic);
						} catch (RemoteException e) {
							e.printStackTrace();
						}
					}

				}
			}
		});

		tabPane.getSelectionModel().selectedItemProperty().addListener(
				new ChangeListener<Tab>() {

					@Override
					public void changed(ObservableValue<? extends Tab> ov,
							Tab t1, Tab t2) {
						String tabName = tabPane.getSelectionModel().getSelectedItem().getText();
						controller.setCurrentTopic(tabPane.getSelectionModel().getSelectedItem().getText());
						updateTextArea();
					}

				});

	}


	public String showTopicsBox(){
		String topicName = null;
		TextInputDialog dialog = new TextInputDialog("");
		Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
		dialog.setTitle("Text Input Dialog");
		dialog.setHeaderText("Look, a Text Input Dialog");
		dialog.setContentText("Please enter your name:");
		okButton.setDisable(true);
		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			topicName = result.get();
		}
		
		// The Java 8 way to get the response value (with lambda expression).
		//result.ifPresent(name -> .println("Your name: " + name));
		return topicName;
	}

	public void createNewTopic(){
		String topicName = showTopicsBox();
		controller.createTopic(topicName);
		refreshTopics();
		int editableRowPosition = controller.getExistingTopics().size();
		System.out.println("New topic");

	}



	private void checkSubscriptionOn(String topic) throws RemoteException{

		if(!controller.isAlreadySubscribeOn(topic)){
			Tab newTab;
			tabPane.getTabs().add(newTab = new Tab(topic));
			tabPane.getSelectionModel().select(newTab);
			updateTextArea();

		}else{
			tabPane.getTabs().remove(topic);
			tabPane.getSelectionModel().select(0);
		}


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
		items =FXCollections.observableArrayList (controller.getExistingTopics());
		listView.setItems(items);
	}

	public void colorOnSubscribe(){
		for(int i = 0; i < controller.getTopicListSize(); i++){
			List<String> subList = controller.getSubcriptionsList();
			String topic = controller.getExistingTopics().get(i);
			if(subList.contains(topic)){
				//Add specific color for a subscribed topic
				//listView.getItems().get(i).
			}
		}
	}

	public void updateTextArea(){
		if(controller.getCurrentTopicName() != null){
			textArea.setText(controller.getLogsContent(controller.getCurrentTopicName()));
		}
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

		controller.setUsername(username.getText());
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			controller = new ClientController(this);
			dialogBox();
			primaryStage.setScene(new Scene(createContent()));
			primaryStage.setResizable(false);
			refreshTopics();
			updateTextArea();
			primaryStage.setTitle("Forum RMI - "+controller.getName());
			primaryStage.show();

			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent we) {
					System.out.println("Stage is closing");
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}


	}


	public static void main(String[] args) {
		launch(args);
	}




}
