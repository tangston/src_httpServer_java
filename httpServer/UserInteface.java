package httpServer;

import java.awt.BorderLayout;
import java.awt.Label;

import javax.swing.*;
import javax.swing.JTextArea;

public class UserInteface extends JFrame{

	//����һЩ�ؼ�  
	public JTextArea log = new JTextArea();//�����־
	public   JTextField rootDir= new JTextField("null");  //��ʾĿ¼
	public  JTextArea count=new  JTextArea("����ͳ�ƣ�" );
	public JTextField inCount=new JTextField();
	public JTextField outCount=new JTextField();
	JScrollPane js=new JScrollPane(log);

	public UserInteface(){
		setTitle("http web server UI");
		//��С
		setSize(800, 600);
		//��������
		setResizable(false);
		//���ò��� 
		//setLayout();
		//��ӿؼ�
		this.add(js,BorderLayout.CENTER);
		this.add(rootDir,BorderLayout.NORTH);
		this.add(count,BorderLayout.SOUTH );
		
		//�ؼ�����
		rootDir.setEditable(false);
		log.setEditable(false);
		count.setEditable(false);
	
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		this.setVisible(true);  
	}


}
