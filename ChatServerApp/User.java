
// Is this a passive or active class?
// Active class
public class User implements Runnable {

	private static int sleepScale = 100;
	
	private int userID;
	private ChatServer chatServer;

	private boolean joinedServer;
	private boolean joinedMainRoom = false;

	private double randomSleep;
	private int wantToChat;

	public User(int userID, ChatServer chatServer) {
		// Set the initial value of class variables.
		this.userID = userID;
		this.chatServer = chatServer;
		// Set wantToChat to random value in range 
		// of 10 to 15.
		// Int Range (MAX, MIN) -> (int)Math.random() * (MAX-MIN+1) + MIN
		wantToChat = (int)(Math.random() * (15-10+1) + 10);
	}

	public int getWantToChat() {
		return wantToChat;
	}

	public int getID() {
		return userID;
	}

	// Within the run method we need to code the different actions
	// a user will take when started.
	public synchronized void run() {
		try {
			// While the user is still intersted in chatting ...
			while (wantToChat > 0) {

				randomSleep = Math.random() + 1;
				Thread.sleep((long) (randomSleep*sleepScale));

				if (!joinedServer) {
					// Try and join Chat Server
					wantToChat -= 0.5;
				if (chatServer.join(this)){ 
					joinedServer = true;
				}
				else{
					wait();
				}

				} else if (!joinedMainRoom && chatServer.isRoomOpen(1)) {
					// Try and join Main Chat Room
					wantToChat -= 1;

					if (chatServer.getNumberOfRooms() > 0){
					if (chatServer.enterRoom(this, 1)){
					joinedMainRoom = true;
					randomSleep = 3 + (Math.random() * 2);
					Thread.sleep((long) (randomSleep*sleepScale));
					wantToChat =- (int)(randomSleep);
					chatServer.leaveRoom(this, 1);
					}
				}			

				} else {
					// Try and join a random Chat Room
					wantToChat -= 2;
					int x = (int)(Math.random() * (3-2+1) + 2);
					if(chatServer.enterRoom(this, x)){
						randomSleep = 3 + (Math.random() * 2);
						Thread.sleep((long) (randomSleep*sleepScale));
						wantToChat =- (int)(randomSleep);
						chatServer.leaveRoom(this, x);
					}
					
				}
			}
			// What should happen when the user no longer wants to chat?
			chatServer.leave(this);
		} catch (InterruptedException ex) {
			System.out.println("Interrupted User Thread (" + userID + ")");
		}
		System.out.println("User Thread (" + userID + ") has ended!");
		return;
	}

}