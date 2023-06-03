import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException, RuntimeException {
        int port = 6868;
        ServerSocket server_skt = new ServerSocket(port);
        try {
            Socket skt = server_skt.accept();
            // The following code block sends messages.
            new Thread(() -> {
                try {
                    OutputStream outputStream = skt.getOutputStream();
                    PrintWriter writer = new PrintWriter(outputStream, true);
                    writer.println("Hello client!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
            // The following code block receives messages
            new Thread(() -> {
                InputStream inputStream;
                String message;
                try {
                    while(!skt.isClosed()){
                        inputStream = skt.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        message = reader.readLine();
                        System.out.println("Received message from client: " + message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
