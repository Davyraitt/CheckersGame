package menuLayout;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class menuController {
	
	private String hostname;
	private int port;
	private boolean isConnected = true;
	private Client client;
	
	@FXML
	TextField loginFormUsername;
	
	@FXML
	TextField chatText;
	
	@FXML
	TextField chatNameText;
	
	@FXML
	ListView listView;
	
	@FXML
	Label labelAbovePlay;
	
	private static ObservableList < String > listOfMessages = FXCollections.observableList ( new ArrayList <>( ) );;
	
	
	public void connectButtonPressed ( ActionEvent event ) {
		this.client = new Client ( "127.0.0.1" , 19999 );
		client.connect ( loginFormUsername.getText ( ) );
		labelAbovePlay.setText ( "Welcome " + loginFormUsername.getText ( ) + " press the play button to start a game!" );
	}
	
	
	public void sendChatMessage ( ActionEvent event ) {
		this.client = new Client ( "127.0.0.1" , 19999 );
		client.sendMessageToServer ( chatNameText.getText ( ) , chatText.getText ( )  );
		listView.setItems ( listOfMessages );
		
	}
	
	public static ObservableList < String > getListOfMessages ( ) {
		return listOfMessages;
	}
	
	public void setListOfMessages ( ObservableList < String > listOfMessages ) {
		this.listOfMessages = listOfMessages;
	}
}

