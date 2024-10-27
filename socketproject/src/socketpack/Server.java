package socketpack;

//Server.java
import java.io.*;
import java.net.*;

public class Server {
	public static void main(String[] args) {
		final int port = 9000;
		String saveFilePath = "received_file.txt"; // Path where the received file will be saved
		String addr6 = IPv6AddressFetcher.getIPv6Address();
		try (ServerSocket serverSocket = new ServerSocket()) {
			serverSocket.bind(new InetSocketAddress(addr6, port)); // Bind directly to IPv6 address
			System.out.println("Server is listening on IPv6 address " + addr6 + "port is:" + port);

			while (true) {
				try (Socket socket = serverSocket.accept();
						InputStream inputStream = socket.getInputStream();
						FileOutputStream fileOutputStream = new FileOutputStream(saveFilePath)) {

					System.out.println("Client connected: " + socket.getInetAddress());

					byte[] buffer = new byte[4096];
					int bytesRead;

					while ((bytesRead = inputStream.read(buffer)) != -1) {
						fileOutputStream.write(buffer, 0, bytesRead);
					}

					System.out.println("File received and saved to " + saveFilePath);
				} catch (IOException e) {
					System.out.println("File transfer failed: " + e.getMessage());
				}
			}
		} catch (IOException e) {
			System.out.println("Server exception: " + e.getMessage());
		}
	}
}
