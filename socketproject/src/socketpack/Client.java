package socketpack;

//Client.java
import java.io.*;
import java.net.*;

public class Client {
	public static void main(String[] args) {
		String serverAddress = IPv6AddressFetcher.getIPv6Address(); 
		final int port = 9000;
		String filePath = "send_file.txt"; // Change this to the path of the file you want to send

		try (Socket socket = new Socket(InetAddress.getByName(serverAddress), port);
				FileInputStream fileInputStream = new FileInputStream(filePath);
				OutputStream outputStream = socket.getOutputStream()) {

			System.out.println("Connected to server: " + serverAddress);

			byte[] buffer = new byte[4096];
			int bytesRead;

			while ((bytesRead = fileInputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}

			System.out.println("File sent successfully.");
		} catch (IOException e) {
			System.out.println("Client exception: " + e.getMessage());
		}
	}
}
