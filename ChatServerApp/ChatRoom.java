import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

// Is this a passive or active class?
// Active Class
public class ChatRoom {
	
	private int chatRoomID;
	private int capacity;
	
	private List<User> users;
	private boolean isOpen;

	public ChatRoom(int chatRoomID, int capacity) {
		// Set the initial value of class variables.
		// Think carefully about how to protect users from
		// unintended synchronous activity.
		this.capacity = capacity;
		this.chatRoomID = chatRoomID;
		this.users = new ArrayList<User>();
	}

	// Consider if this should be run asynchronously.
	public synchronized void open() {
		// Code to open the Chat Room.
		isOpen = true;
		System.out.println("Chat Room " + chatRoomID + " open!");
	}
	
	// Consider if this should be run asynchronously.
	public synchronized void close()
		throws InterruptedException{
		System.out.println("Chat Room " + chatRoomID + " is being closed!");
		isOpen = false;
		// Code to close the Chat Room.
		// Think carefully about when you can successfully 
		// close the Chat Room.

			while(users.isEmpty() == false) {
				wait();
			}

		System.out.println("Chat Room " + chatRoomID + " closed!");	
	}

	// Consider if this should be run asynchronously.
	public synchronized boolean enterRoom(User user) {
		// Code for a User to enter a Chat Room.
		// Consider conditions that need to be true for this 
		// to be successful. 
		// Returns true if joined successfully, false otherwise.

		if (users.size() < capacity && isOpen){
			System.out.println("User " + user.getID() + " joined Chat Room " + chatRoomID + ". (" + user.getWantToChat() + ")");
			users.add(user);
			return true;
		}
		else{
			System.out.println("User " + user.getID() + " not joined Chat Room " + chatRoomID + ". (" + user.getWantToChat() + ")");
			return false;
		}

	}

	// Consider if this should be run asynchronously.
	public void leaveRoom(User user) {
		// Code for a User to leave a Chat Room.
		users.remove(user);
		System.out.println("User " + user.getID() + " left Chat Room " + chatRoomID + ". (" + user.getWantToChat() + ")");
	}

	public boolean getIsOpen() {
		return isOpen;
	}

	public int getID(){
		return chatRoomID;
	}

	public boolean isFull(){
		return users.size() == capacity;
	}

}