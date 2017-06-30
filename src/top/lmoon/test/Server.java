/**
 * @copyright 2017 tianya.cn
 */
package top.lmoon.test;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author guozy
 * @date 2017-6-30
 * 
 */
public class Server {

	private static final int PORT = 8888;
	
	private static Server instance = null;
	
	public static Server getInstance(){
		if(instance==null){
			instance = new Server();
		}
		return instance;
	}

	private Server() {
		try {
			ServerSocket ss = new ServerSocket(PORT);
			System.out.println("服务端启动！");
			new ServerThread(ss).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public class ServerThread extends Thread {

		private ServerSocket serverSocket;

		public ServerThread(ServerSocket serverSocket) {
			this.serverSocket = serverSocket;
		}

		@Override
		public void run() {
			DataOutputStream dos = null;
			ObjectInputStream ois = null;
			Socket socket = null;
			// 截图，发送
			try {
				Robot robot = new Robot();
				socket = serverSocket.accept();
				dos = new DataOutputStream(socket.getOutputStream());
				ois = new ObjectInputStream(socket.getInputStream());
				while (true) {									
					ServerDealObject.handleInputStream(robot, ois);
					ServerDealObject.handleOutputStream(dos);
					Thread.sleep(50);
				}
//			} catch (SocketException ef) {
//				// ef.printStackTrace();
//				rs.destroy();
//				throw new MyException("客户端SOCKET已断开连接，无法发送图片信息。。");

//			} catch (InterruptedException e) {
//				// TODO Auto-generatedcatch block
//				rs.destroy();
//				throw new MyException(e,"客户端已中断连接，无法发送图片信息。。");
			} catch (IOException e) {
				// TODO Auto-generatedcatch block
//				rs.destroy();
				throw new MyException(e,"客户端输入输出流中断，无法发送图片信息。。");
			} catch (Exception e) {
				// TODO Auto-generatedcatch block
//				rs.destroy();
				throw new MyException(e,"客户端已断开连接，无法发送图片信息。。");
			}
		}

	}

	
	
	public static void main(String[] args) {
		getInstance();
	}

}
