package chatserver;


import java.net.InetSocketAddress;
import java.util.*;

public class UserMap {

	private static volatile Map<String, InetSocketAddress> loggedInUsers = new HashMap<>();
	private static volatile Map<String, InetSocketAddress> registeredUsers = new HashMap<>();

	public synchronized boolean loginUser(String name, InetSocketAddress adress){
		if(!loggedInUsers.containsKey(name)) {
			loggedInUsers.put(name, adress);
			return true;
		}
		return false;
	}

	public synchronized boolean registerUser(String name, InetSocketAddress address){
		if(!registeredUsers.containsKey(name)){
			registeredUsers.put(name,address);
			return true;
		}
		return false;
	}

	public synchronized boolean logoutUser(InetSocketAddress address){
		String username = "";
		for(String name : loggedInUsers.keySet()){
			if(loggedInUsers.get(name).equals(address)){
				username = name;
			}
		}
		if(username.isEmpty()){
			return false;
		}

		loggedInUsers.remove(username);
		registeredUsers.remove(username);

		return true;
	}

	public List<InetSocketAddress> getAllLoggedInUsersAddresses(){
		List<InetSocketAddress> list = new ArrayList<>();
		for(InetSocketAddress address : loggedInUsers.values()){
			list.add(address);
		}
		return list;
	}

	public List<String> getAllLoggedInUsersnames(){
		List<String> list = new ArrayList<>();
		for(String name : loggedInUsers.keySet()){
			list.add(name);
		}
		return list;
	}

	public String getLoggedInUsername(InetSocketAddress address){
		String name = "";
		for(String user : loggedInUsers.keySet()){
			if(loggedInUsers.get(user).equals(address)){
				name = user;
				break;
			}
		}
		return name;
	}

	public List<String> getAllLoggedInUsernames(){
		Set<String> set = loggedInUsers.keySet();
		List<String> list = new ArrayList<>();
		list.addAll(set);
		return list;
	}

	public boolean isUserLoggedIn(String username){
		return loggedInUsers.containsKey(username);
	}

	public boolean isUserLoggedIn(InetSocketAddress address){
		return loggedInUsers.values().contains(address);
	}

	public synchronized void clear(){
		loggedInUsers.clear();
		registeredUsers.clear();
	}

	public InetSocketAddress getRegisteredUser(String username) {
		return registeredUsers.get(username);
	}
}
