package httpServer;

import java.awt.BorderLayout;
import java.awt.Label;

import javax.swing.*;
import javax.swing.JTextArea;

public class UserInteface extends JFrame{

	//定义一些控件  
	public JTextArea log = new JTextArea();//存放日志
	public   JTextField rootDir= new JTextField("null");  //显示目录
	public  JTextArea count=new  JTextArea("流量统计：" );
	public JTextField inCount=new JTextField();
	public JTextField outCount=new JTextField();
	JScrollPane js=new JScrollPane(log);

	public UserInteface(){
		setTitle("http web server UI");
		//大小
		setSize(800, 600);
		//不可缩放
		setResizable(false);
		//设置布局 
		//setLayout();
		//添加控件
		this.add(js,BorderLayout.CENTER);
		this.add(rootDir,BorderLayout.NORTH);
		this.add(count,BorderLayout.SOUTH );
		
		//控件属性
		rootDir.setEditable(false);
		log.setEditable(false);
		count.setEditable(false);
	
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
		this.setVisible(true);  
	}


}
