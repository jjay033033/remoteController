/**
 * @copyright 2017 tianya.cn
 */
package top.lmoon.rc.util;

import javax.swing.JOptionPane;

/**
 * @author guozy
 * @date 2017-6-30
 * 
 */
public class MyException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7931947484307369548L;
    public MyException(Exception e,String info){
       System.out.println(info);
       if(e!=null){
    	   e.printStackTrace();
       }     
       JOptionPane.showMessageDialog(null, info,"提示",JOptionPane.ERROR_MESSAGE);
    }
}
