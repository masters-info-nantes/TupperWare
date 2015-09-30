package fr.alma.middleware.View;

import java.util.List;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class ClientView extends Application{

	private TabPane tabPane;
	private Tab newTab;
	private List<Tab> tabList;
	
	
	
	public Parent createContent(){
		
		tabPane = new TabPane();
		Tab tab1 = new Tab("Sport");
		Tab tab2 = new Tab("Musique");
		Tab tab3 = new Tab("Cinéma");
		newTab = new Tab();
		newTab.setClosable(false);
		
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
		
		tabPane.getTabs().add(tab1);
		tabPane.getTabs().add(tab2);
		tabPane.getTabs().add(tab3);
		tabPane.getTabs().add(newTab);
		
		VBox vbox = new VBox(4);
		
		
		return tabPane;
	}

	public void createNewTopicChan(){
		int index = tabPane.getTabs().size()-1;
		String topicName = "New Topic";
		Tab newTab = new Tab(topicName);
		VBox vbox = new VBox(2);
		SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
		//If the topic is not already create 
			//create the top and redirect the user into it
		//else
			//join the topic
		tabPane.getTabs().add(index, newTab);
		newTab.setContent(vbox);
		selectionModel.select(newTab);
		
	}
	
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}


}
