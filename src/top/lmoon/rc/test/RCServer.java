package top.lmoon.rc.test;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class RCServer {
    private static RCServer rcs = new RCServer();
 
 
    public static void main(String[] args) throws Exception {
        U.debug("start Remote Control Server...");
        rcs.startServer(18080);
    }
 
    /**
     * 根据特定端口启动服务器
     * @param port
     * @throws Exception
     */
    public void startServer(int port) throws Exception {
        U.debug(U.f("run server in port:%d", port));
        ServerSocket ss = new ServerSocket(port);;
        while (true) {
            U.debug("Remote Control Server wait client...");
            Socket client = ss.accept();
            U.debug(U.f("a client[%s:%d] connect!", client.getLocalAddress(), client.getPort()));
 
            InputStream in = client.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(in);
             
            OutputStream os = client.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);
            U.debug("socket open stream ok!");
             
            ControlThread cont = new ControlThread(ois);
            cont.start();//启动控制线程
            CaptureThread capt = new CaptureThread(dos);
            capt.start();//启动屏幕传输线程
        }
    }
 
    public int stopServer() {
        return 0;
    }
}


