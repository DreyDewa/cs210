import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

// Is this a passive or active class?
// Passive class.
public class ChatServer {
	
	private ArrayList<ChatRoom> rooms;
	private int numOfRooms;
	private List<User> users;
	private Admin admin;

	private int capacity;
	private boolean isOpen;

	public ChatServer(int passedcapacity, int numRooms, Admin passedadmin) {
		// Set the initial value of class variables.
		// Think carefully about how to protect users from
		// unintended synchronous activity.
		
		capacity = passedcapacity;
		numOfRooms = numRooms;
		rooms = new ArrayList<ChatRoom>();
		users = new ArrayList<>();
		// We initalise the admin attribute and call the 
		// assignServer method of the admin with this object 
		// as the parameter.  
		admin = passedadmin;
		admin.assignServer(this);
	}

	// Consider if this should be run asynchronously.
	public synchronized void open() {
		// Code to open the Chat Room.
		isOpen = true;
		notify();
		System.out.println("Chat Server is Opened.");
	}
	
	// Consider if this should be run asynchronously.
	public void close() {
		System.out.println("Chat Server is being Closed.");
		// Code to close the Chat Server.
		// Think carefully about when you can successfully 
		// close the Chat Server.
		boolean closeAble = true;
		for (int x = 0; x < rooms.size(); x++){
			if (rooms.get(x).getIsOpen()) {
				closeAble = false;
				System.out.println("Unable to close Chat Server.");
			}
		}
		if (closeAble){
		isOpen = false;
		// while (!users.isEmpty()){
		// 	wait();
		// }
		System.out.println("Chat Server is Closed.");
		}
	}

	// Consider if this should be run asynchronously.
	public boolean join(User user) {
		// Code for a User to enter the Chat Server.
		// Consider conditions that need to be true for this 
		// to be successful.
		// Returns true if joined successfully, false otherwise.
		if (users.size() <= capacity && isOpen){
		System.out.println("User " + user.getID() + " admitted to Chat Server (" + user.getWantToChat() + ").");
		users.add(user);
		return true;
		}
		else{
		System.out.println("User " + user.getID() + " failed to join Chat Server (" + user.getWantToChat() + ").");
		return false;
		}
	}

	// Consider if this should be run asynchronously.
	public synchronized void leave(User user) {
		// Code for a User to leave the Chat Server.
		if (users.contains(user)){
		System.out.println("User " + user.getID() + " left the Chat Server.");
		users.remove(user);
		notify();
		}
		else{
		System.out.println("Could not remove User " + user.getID() + " as is not in the Chat Server.");
		}
	}

	public void addChatRoom(ChatRoom room){
		rooms.add(room);
	}

	public void openChatRoom(int chatRoomID) {
		// Code to open Chat Room.
		if (isOpen){
			for (int x = 0; x < rooms.size(); x++){
			if (rooms.get(x).getID() == chatRoomID){
				rooms.get(x).open();
			}
			}
		}
	}

	public void closeChatRoom(int chatRoomID) 
	throws InterruptedException {
		// Code to close Chat Room.
		if (isOpen){
			rooms.get(chatRoomID).close();
		}
	}

	public boolean enterRoom(User user, int chatRoomID) {
		// Code to allow user to enter Chat Room.
		boolean bool = false;
		for (int x = 0; x < rooms.size(); x++){
			if (rooms.get(x).getID() == chatRoomID){
				if (rooms.get(x).enterRoom(user)){
				bool = true;
				}
			}
			}
			return bool;
		}

	public synchronized void leaveRoom(User user, int chatRoomID) {
		// Code to allow user to leave Chat Room.
		for (int x = 0; x < rooms.size(); x++){
			if (rooms.get(x).getID() == chatRoomID){
				rooms.get(x).leaveRoom(user);
				notify();
			}
			}
		
	}

	public int getNumberOfRooms() {
		return rooms.size();
	}

	public boolean isRoomOpen(int chatRoomID) {
		return rooms.get(chatRoomID).getIsOpen();
	}

	public int getNumberOfUsers() {
		return users.size();
	}

	public boolean isFull(){
		return users.size() == capacity;
	}

	public ChatRoom getRoom(int id){
		return rooms.get(id);
	}

}