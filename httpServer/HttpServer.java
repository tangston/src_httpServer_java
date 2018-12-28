package httpServer;
//use gbk
import java.net.Socket;
import java.rmi.server.UID;
import java.util.concurrent.ExecutorService;
import java.net.ServerSocket;
import java.net.InetAddress;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.File;

public class HttpServer implements Runnable{

	/**
	 * WEB_ROOT是HTML和其它文件存放的目录. 这里的WEB_ROOT为工作目录下的webroot目录
	 */
	static UserInteface ui=new UserInteface();//仅仅初始化一个界面
	private  static ServerSocket serverSocket = null;
	private  int port = 23333;
	public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";

	// 关闭服务命令
	private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

	public static void main(String[] args) {
		HttpServer server = new HttpServer();
		 ui.rootDir.setText(WEB_ROOT);
		//等待连接请求
		server.init();
		for (int i = 0; i < 2; i++) {
			new Thread  (new HttpServer()  ).start();//并发2个用户
		}
	}

	public void init() {

		try {
			//服务器套接字对象
			serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}



	}

	@Override
	public void run() {
		// TODO 自动生成的方法存根
		Socket socket = null;
		InputStream input = null;
		OutputStream output = null;
		// 循环等待一个请求
		while (true) {
			System.out.println(Thread.currentThread().getName());
			try {
				//等待连接，连接成功后，返回一个Socket对象
				socket = serverSocket.accept();//tcp才有链接
				input = socket.getInputStream();//返回 用套接字读取到的接受信息
				output = socket.getOutputStream();//缓冲区，暂时存储将要发送过去的数据

				// 创建Request对象并解析
				Request request = new Request(input);
				request.parse();

				// 检查是否是关闭服务命令
				if(request.getUri() != null) {
					if (request.getUri().equals(SHUTDOWN_COMMAND)) {
						break;
					}
				}


				// 创建 Response 对象
				Response response = new Response(output);
				response.setRequest(request);
				response.sendStaticResource();
				socket.close();//关闭套接字并发送output内容，再清空缓存区


			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}
}