/**
 * @copyright 2017 tianya.cn
 */
package top.lmoon.test;

import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * @author guozy
 * @date 2017-6-30
 * 
 */
public class Client extends JFrame {
	 
    /**
     * @param args
     */
    public JTextField iptf  =new JTextField(10);
    public JTextField porttf  =new JTextField(3);
    public JButton jb1 = new JButton("连接");
    public JButton jb2 = new JButton("断开");
    public void launch(){
 
            
           this.setTitle("远程控制客户端---连接窗口");
           this.setLocation(300, 200);
           this.setSize(300, 100);
           this.setAlwaysOnTop(true);
          
           JLayeredPane jlp  =new JLayeredPane();
           jlp.setLayout(new FlowLayout());
          
           jlp.add(new JLabel("远程IP地址："));
//           iptf.setText("192.168.1.103");
           iptf.setText("127.0.0.1");
           porttf.setText("8888");
           jlp.add(iptf);
           jlp.add(new JLabel("端口："));
           jlp.add(porttf);
           jb1.addMouseListener(new MouseAdapter(){
 
                 
                  public void mouseClicked(MouseEvent e) {
                        
                         try {
                               
                                ClientDealObject.connect(iptf.getText(),Integer.parseInt(porttf.getText().trim()));
                               
                         } catch (NumberFormatException e1) {
                                //端口号格式输入出错
                                JOptionPane.showMessageDialog(null,"IP地址或端口号输入出错，无法取得连接。。","提示",JOptionPane.ERROR_MESSAGE);
                                System.exit(0);
                                destroy();
//                             
                         } catch (MyException e1) {
//                              JOptionPane.showMessageDialog(null,e1.info,"提示",JOptionPane.ERROR_MESSAGE);
                        
//                             
                         }catch(Exception ed){
                                // "远程不允许被控，无法取得连接。。
                                JOptionPane.showMessageDialog(null,"远程不允许被控，无法取得连接。。","提示",JOptionPane.ERROR_MESSAGE);
                                System.exit(0);
                                destroy();
                         }
                         //连接成功
                        
                 
                        
                  }
                 
           });
           jb2.addMouseListener(new MouseAdapter(){
 
   
                  public void mouseClicked(MouseEvent e) {
                         JOptionPane.showMessageDialog(null, "断开控制端窗口","提示",JOptionPane.INFORMATION_MESSAGE);
                         System.exit(0);
                         destroy();
                  }
                 
           });
           jlp.add(jb1);
           jlp.add(jb2);
           this.setLayeredPane(jlp);
           this.addWindowListener(new WindowAdapter(){
 
                  public void windowClosing(WindowEvent e) {
                         // 关闭窗口
                         JOptionPane.showMessageDialog(null, "关闭连接窗口","提示",JOptionPane.INFORMATION_MESSAGE);
                         System.exit(0);
                        
                  }     
                 
           });
          
           this.setVisible(true);
          
   
    }
    public static void main(String[] args) {
           // TODO Auto-generated method stub
//         new RemoteControlLoginFrame().getLocalIP();
           new Client().launch();
    }
 
    public String getLocalIP(){
           String ipstr = "";
           InetAddress ip = null;
           try {
                  ip = InetAddress.getLocalHost();
           } catch (UnknownHostException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
           }
           ipstr = ip.toString();
           ipstr = ipstr.substring(ipstr.indexOf("/")+1);
           System.out.println(ipstr);
           return ipstr;
    }
    public void destroy(){
           this.destroy();
    }
 
 
 
}
