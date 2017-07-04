package top.lmoon.rc.test;

import java.awt.Dimension;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

/**
 * 控制端
 * @author syxChina
 *
 */
public class RCClient  {
    private ClientUI clientUI ;
    private DataInputStream dis;
    private ObjectOutputStream oos;
    private Socket client;
 
    /**
     * 连接被控制端
     * @param host
     * @param port
     * @return
     */
    public int connect(String host, int port) {
        int retCode = 0;
        try {
            client = new Socket(host, port);
            U.debug(client);
            oos = new ObjectOutputStream(client.getOutputStream());
            dis = new DataInputStream(client.getInputStream());
            U.debug("client open stream ok!");
        } catch (UnknownHostException e) {
            retCode = 1;
        } catch (IOException e) {
            retCode = 2;
        }
        return retCode;
    }
     
    /**
     * 显示图形界面
     * @throws Exception
     * @throws ClassNotFoundException
     */
    public void showClientUI() throws Exception, ClassNotFoundException {
        clientUI = new ClientUI(dis, oos);
        U.debug("start client UI");
        clientUI.updateSize(readServerSize());
        while(true) {
            long begin = System.currentTimeMillis();
            byte[] imageData = readBytes();
            clientUI.update(imageData);
            long end = System.currentTimeMillis();
            U.debug(U.f("time=%d,size=%d", end-begin, imageData.length));
        }
    }
    /**
     * 读被控制段发送来的数据
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public byte[] readBytes() throws IOException, ClassNotFoundException {
        int len = dis.readInt();
        byte[] data = new byte[len];
        dis.readFully(data);
        return data;
    }
    /**
     * 读被控制端分辨率
     * @return
     */
    public Dimension readServerSize() {
        double height = 100;
        double width = 100;
        try {
            height = dis.readDouble();
            width = dis.readDouble();
        } catch (IOException e) {
            U.debug("read server SIZE error!");
        }
        return new Dimension((int)width, (int)height);
    }
     
    public static void main(String[] args) throws Exception {
        String input = JOptionPane.showInputDialog("请输入要连接的服务器(192.168.0.2:18080):","192.168.65.90:18080");
        if(input == null) {
            return;
        }
        Pattern pattern = Pattern.compile("(\\d+.\\d+.\\d+.\\d+):(\\d+)");
        java.util.regex.Matcher m = pattern.matcher(input);
        if(!m.matches()) {
            return;
        }
        String host = m.group(1);
        int port = Integer.parseInt(m.group(2));
        RCClient rcc = new RCClient();
 
        rcc = new RCClient();
        U.debug(U.f("run client , connect server in [%s:%d]", host, port));
        int retCode = rcc.connect(host, port);//连接指定的被控制端
        if (retCode != 0) {
            U.error(U.f("connect server[%s:%d] error!app exit!", host, port));
            return;
        }
        try {
            rcc.showClientUI();
        } catch (Exception e) {
            U.error("disconnect with server!");
        }
    }
 
}

