package udp6;


import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet6Address;


public class UdpServer {
    public static void main(String[] args) {
        int port = 9000;
        String saveFilePath = "received_file.txt"; // Path where the received file will be saved


        String ipv6Address = IPv6AddressFetcher.getIPv6Address();
        if (ipv6Address == null) {
            System.out.println("No IPv6 address found. Exiting...");
            return;
        }

        try (DatagramSocket socket = new DatagramSocket(port,Inet6Address.getByName(ipv6Address))) {
            System.out.println("UDP Server is listening on port " + port);

            // Create a buffer for receiving data
            byte[] buffer = new byte[4096];
            try (FileOutputStream fileOutputStream = new FileOutputStream(saveFilePath)) {
				while (true) {
				    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				    socket.receive(packet); // Receive the packet

				    // Write data to file
				    fileOutputStream.write(packet.getData(), 0, packet.getLength());
				    System.out.println("Received packet from: " + packet.getAddress().getHostAddress() + ":" + packet.getPort());
				}
			}
        } catch (IOException e) {
            System.out.println("Server exception: " + e.getMessage());
        }
    }
}
