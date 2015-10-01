package fr.alma.middleware.view;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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
					System.out.println("Ca marche!");
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
	
	
	@Override
	public void start(Stage primaryStage) {
		controller = new ClientController();
		refreshTopics();
		primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}


}
