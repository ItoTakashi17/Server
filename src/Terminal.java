import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Terminal {
    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        String serverAddress = "localhost";  // 服务器IP地址
        int serverPort = 6868;  // 服务器端口号
        // 创建一个Socket连接到服务器
        Socket socket = new Socket(serverAddress, serverPort);
        new Thread(new Receivers(socket)).start();
//        String new_message = input.next();
        new Thread(new Senders(socket, "Hello, I'm terminal 2")).start();
    }

    static class Receivers implements Runnable {
        private final Socket skt;

        public Receivers(Socket socket) {
            this.skt = socket;
        }

        @Override
        public void run() {
            try {
                while(true) {
                    InputStream inputStream = skt.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String message = reader.readLine();
                    System.out.println("Received message from server: " + message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class Senders implements Runnable {
        private final Socket skt;
        String new_message;

        Senders(Socket skt, String new_message) {
            this.skt = skt;
            this.new_message = new_message;
        }

        @Override
        public void run() {
            OutputStream outputStream;
            try {
                while (!skt.isClosed()) {
                    if (!this.new_message.isEmpty()) {
                        outputStream = skt.getOutputStream();
                        PrintWriter writer = new PrintWriter(outputStream, true);
                        writer.println(new_message);
                        writer.flush(); // 强制输出缓冲区中的数据
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
