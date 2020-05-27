package menuLayout;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class menuController {
	
	
	private String hostname;
	private int port;
	private boolean isConnected = true;
	
	@FXML
	TextField loginFormUsername;
	
	@FXML
	TextField chatText;
	
	@FXML
	TextField chatNameText;
	
	
	public void connectButtonPressed ( ActionEvent event ) {
		
		System.out.println ("Button pressed!" );
		System.out.println ("Box contains: " + loginFormUsername.getText () );
		
		Client client = new Client("127.0.0.1", 19999);
		client.connect( loginFormUsername.getText () );
	}
	
	public void sendChatMessage ( ActionEvent event ) {
		
		System.out.println ("Button pressed!" );
		System.out.println ("Name is: " + chatNameText.getText () );
		
		
		System.out.println ("Msg is: " + chatText.getText () );
	}
}

