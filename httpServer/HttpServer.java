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
	 * WEB_ROOT��HTML�������ļ���ŵ�Ŀ¼. �����WEB_ROOTΪ����Ŀ¼�µ�webrootĿ¼
	 */
	static UserInteface ui=new UserInteface();//������ʼ��һ������
	private  static ServerSocket serverSocket = null;
	private  int port = 23333;
	public static final String WEB_ROOT = System.getProperty("user.dir") + File.separator + "webroot";

	// �رշ�������
	private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";

	public static void main(String[] args) {
		HttpServer server = new HttpServer();
		 ui.rootDir.setText(WEB_ROOT);
		//�ȴ���������
		server.init();
		for (int i = 0; i < 2; i++) {
			new Thread  (new HttpServer()  ).start();//����2���û�
		}
	}

	public void init() {

		try {
			//�������׽��ֶ���
			serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}



	}

	@Override
	public void run() {
		// TODO �Զ����ɵķ������
		Socket socket = null;
		InputStream input = null;
		OutputStream output = null;
		// ѭ���ȴ�һ������
		while (true) {
			System.out.println(Thread.currentThread().getName());
			try {
				//�ȴ����ӣ����ӳɹ��󣬷���һ��Socket����
				socket = serverSocket.accept();//tcp��������
				input = socket.getInputStream();//���� ���׽��ֶ�ȡ���Ľ�����Ϣ
				output = socket.getOutputStream();//����������ʱ�洢��Ҫ���͹�ȥ������

				// ����Request���󲢽���
				Request request = new Request(input);
				request.parse();

				// ����Ƿ��ǹرշ�������
				if(request.getUri() != null) {
					if (request.getUri().equals(SHUTDOWN_COMMAND)) {
						break;
					}
				}


				// ���� Response ����
				Response response = new Response(output);
				response.setRequest(request);
				response.sendStaticResource();
				socket.close();//�ر��׽��ֲ�����output���ݣ�����ջ�����


			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}
}