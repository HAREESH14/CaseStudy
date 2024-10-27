package udp6;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpClient {
    public static void main(String[] args) {
        int port = 9000;
        String filePath = "send_file.txt"; // File to send

        // Fetch the server's IPv6 address
        String serverAddress = IPv6AddressFetcher.getIPv6Address(); //  server runs on the same machine
        if (serverAddress == null) {
            System.out.println("No IPv6 address found. Exiting...");
            return;
        }

        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress address = InetAddress.getByName(serverAddress);
            @SuppressWarnings("resource")
			FileInputStream fileInputStream = new FileInputStream(filePath);
            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                DatagramPacket packet = new DatagramPacket(buffer, bytesRead, address, port);
                socket.send(packet); // Send the packet
                System.out.println("Sent packet to server: " + serverAddress + ":" + port);
            }
            System.out.println("File sent successfully in udp.");
        } catch (IOException e) {
            System.out.println("Client exception: " + e.getMessage());
        }
    }
}
