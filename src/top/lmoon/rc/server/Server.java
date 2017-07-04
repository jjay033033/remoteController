/**
 * @copyright 2017 tianya.cn
 */
package top.lmoon.rc.server;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import top.lmoon.rc.util.ErrorHandler;
import top.lmoon.rc.util.MyException;
import top.lmoon.rc.util.ThreadPoolUtil;

/**
 * 
 * @author LMoon
 * @date 2017年7月4日
 *
 */
public class Server {

	private static final int PORT = 8888;

	private static Server instance = null;

	public static Server getInstance() {
		if (instance == null) {
			instance = new Server();
		}
		return instance;
	}

	private Server() {
		try {
			ServerSocket ss = new ServerSocket(PORT);
			System.out.println("Server starts!");
			ThreadPoolUtil.getPool().submit(new ServerThread(ss));
		} catch (IOException e) {
			ErrorHandler.error(e, "客户端输入输出流中断，无法发送图片信息。。");
		}
	}

	public class ServerThread extends Thread {

		private ServerSocket serverSocket;

		public ServerThread(ServerSocket serverSocket) {
			this.serverSocket = serverSocket;
		}

		@Override
		public void run() {
			try {
				while (true) {
					Socket socket = serverSocket.accept();
					DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
					ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
					ThreadPoolUtil.getPool().submit(new InputStreamHandleThread(ois));
					ThreadPoolUtil.getPool().submit(new OutputStreamHandleThread(dos));

				}
			} catch (IOException e) {
				ErrorHandler.error(e, "客户端输入输出流中断，无法发送图片信息。。");
			}
		}

	}

	public static void main(String[] args) {
		getInstance();
	}

}
