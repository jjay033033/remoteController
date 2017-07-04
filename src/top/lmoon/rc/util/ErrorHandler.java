/**
 * 
 */
package top.lmoon.rc.util;

import java.awt.Component;

import javax.swing.JOptionPane;

/**
 * @author LMoon
 * @date 2017年7月4日
 * 
 */
public class ErrorHandler {

	public static void error(Exception e) {
		if (e != null) {
			e.printStackTrace();
		}
	}

	public static void errorDialog(String msg,Component parentComponent) {
		if (msg != null) {
			log(msg);
			JOptionPane.showMessageDialog(parentComponent, msg, "提示", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void errorDialog(String msg) {
		errorDialog(msg,null);
	}

	public static void error(Exception e, String msg,Component parentComponent) {
		errorDialog(msg,parentComponent);
		error(e);
	}
	
	public static void error(Exception e, String msg) {
		error(e, msg, null);
	}
	
	public static void log(String msg){
		System.out.println(msg);
	}

}
