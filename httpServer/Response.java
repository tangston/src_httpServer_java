package httpServer;


import java.io.OutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.File;

/*
  HTTP Response = Status-Line
    *(( general-header | response-header | entity-header ) CRLF)
    CRLF
    [ message-body ]
    Status-Line = HTTP-Version SP Status-Code SP Reason-Phrase CRLF
*/

public class Response {

    private static final int BUFFER_SIZE = 1024;
    Request request;
    OutputStream output;

    public Response(OutputStream output) {
        this.output = output;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void sendStaticResource() throws IOException {
        byte[] bytes = new byte[BUFFER_SIZE];
        FileInputStream fis = null;
        // Ҫ���������ļ����Ϳ��ļ���----------------------------------------
        try {
        	if(request.getUri()==null) {//��ʱ��֧��post
        		//------------------�����get������˵����Ĭ��Ŀ¼�����Ƿ���index.html�������post������Ҫȥ��ȡ���ݲ����������Ŀ¼
        		String errorMessage = "HTTP/1.1  404 File Not Found\r\n" + "Content-Type: text/html\r\n"
                        + "Content-Length: 23"+  "\r\n" + "\r\n" + "<h1>404 File Not Found</h1>";
                output.write(errorMessage.getBytes());
                HttpServer.ui.log.append(errorMessage);//--------------ui log����
                HttpServer.ui.log.setCaretPosition(  HttpServer.ui.log.getText().length());
        	}
        //��web�ļ�д�뵽OutputStream�ֽ�����
            File file = new File(HttpServer.WEB_ROOT, request.getUri());//��һ���������ļ��У��ڶ������ļ�
            if (file.exists()) {
                fis = new FileInputStream(file);
                String head=	"HTTP/1.1 200\r\n" + "Content-Type: text/html\r\n" + "Content-Length: " + file.length() + "\r\n" + "\r\n";
                HttpServer.ui.log.append(head);//--------------ui log����
                int totalBytes=0;
                int ch = fis.read(bytes, 0, BUFFER_SIZE);
                totalBytes+=ch;
                output.write(head.getBytes(),0,head.getBytes().length);          
                while (ch != -1) {
                    output.write(bytes, 0, ch);
                    HttpServer.ui.log.append(bytes.toString());//--------------ui log����
                    HttpServer.ui.log.setCaretPosition(  HttpServer.ui.log.getText().length());
                    ch = fis.read(bytes, 0, BUFFER_SIZE);
                    
                    
                    totalBytes+=ch;
                }
                HttpServer.ui.count.append("  ���������"+totalBytes+" Bytes" );
               
            } else {
                // file not found
          
                String errorMessage = "HTTP/1.1  404 File Not Found\r\n" + "Content-Type: text/html\r\n"
                        + "Content-Length: 23"+  "\r\n" + "\r\n" + "<h1>404 File Not Found</h1>";
                output.write(errorMessage.getBytes());
                HttpServer.ui.log.append(errorMessage);//--------------ui log����
                HttpServer.ui.log.setCaretPosition(  HttpServer.ui.log.getText().length());
            }
        } catch (Exception e) {
            // thrown if cannot instantiate a File object
            System.out.println(e.toString());
        } finally {
            if (fis != null)
                fis.close();
        }
    }
}