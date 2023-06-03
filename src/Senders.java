import java.net.Socket;
import java.net.ServerSocket;
public class Senders implements Runnable{
    Socket skt;

    Senders(Socket skt){
        this.skt = skt;
    }

    @Override
    public void run() {

    }
}
