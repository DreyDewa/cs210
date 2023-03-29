
// Note: This is an active class and must implemnet runnable 
public class Admin implements Runnable {
	

	private String name;
	private ChatServer chatServer; 
	private static int sleepScale = 100;
	private int numOfActions = 15;  //Could be either openning or closing a chatroom

	public Admin(String passedName) {
		// Set the initial value of class variables
		name = passedName;
	}

	public void assignServer(ChatServer passedChatServer) {
		// Store given Chat Server in Class Attribute
		chatServer = passedChatServer;
	}
	
	// Does this class require a run() method? If so consider how to ensure
	// when the thread is run that it performs all required actions.
	@Override
	public void run() {
		
		// you need to open the chat server and the chat rooms

		// run actions randomly (HINT: you may use a randomised sleep time before doing the action)
		// close the chat server and the chat rooms

		

		try {
			chatServer.open();
			numOfActions --;

			ChatRoom r1 = new ChatRoom(1, 3);
			chatServer.addChatRoom(r1);
			numOfActions --;

			r1.open();
			numOfActions--;
			int seconds = 1;
			Thread.sleep((long) (seconds*sleepScale));

			ChatRoom r2 = new ChatRoom(2, 3);
			chatServer.addChatRoom(r2);
			numOfActions --;
			seconds = 1;
			Thread.sleep((long) (seconds*sleepScale));

			r2.open();
			numOfActions--;
			seconds = 10;
			Thread.sleep((long) (seconds*sleepScale));

			r1.close();
			numOfActions--;
			seconds = 1;
			Thread.sleep((long) (seconds*sleepScale));

			ChatRoom r3 = new ChatRoom(3, 4);
			chatServer.addChatRoom(r2);
			numOfActions --;
			seconds = 1;
			Thread.sleep((long) (seconds*sleepScale));
			
			r3.open();
			numOfActions--;
			seconds = 5;
			Thread.sleep((long) (seconds*sleepScale));

			// r2.close();
			// numOfActions--;
			// seconds = 1;
			// Thread.sleep((long) (seconds*sleepScale));

			// r1.open();
			// numOfActions--;
			// seconds = 1;
			// Thread.sleep((long) (seconds*sleepScale));

			r2.close();
			numOfActions--;
			seconds = 1;
			Thread.sleep((long) (seconds*sleepScale));

			r3.close();
			numOfActions--;
			seconds = 1;
			Thread.sleep((long) (seconds*sleepScale));

			r1.close();
			numOfActions--;
			seconds = 1;
			Thread.sleep((long) (seconds*sleepScale));

			chatServer.close();
			numOfActions--;
			seconds = 1;
			Thread.sleep((long) (seconds*sleepScale));

		}
		catch(InterruptedException ex){
			System.out.println("Interrupted Arrival Thread");
            return;
		}

	}

}