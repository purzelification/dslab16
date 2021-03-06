package chatserver.executor;

import chatserver.Chatserver;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

public class SendExecutor implements IRequestExecutor {

	private List<String> arguments;
	private Socket socket;

	public SendExecutor(List<String> arguments, Socket socket) {
		this.arguments = arguments;
		this.socket = socket;
	}

	@Override
	public void execute(Chatserver chatserver) {
		String username = chatserver.getUserMap().getLoggedInUsername((InetSocketAddress) socket.getRemoteSocketAddress());
		String message = "";
		for(String part : arguments){
			message += part+" ";
		}
		for(Chatserver c: chatserver.getInstances()){
			Socket tmp = c.getSocket();
			if(tmp.getRemoteSocketAddress().equals(socket.getRemoteSocketAddress())){
				continue;
			}

			c.answer("!pub "+username+": "+message);

		}
	}
}
