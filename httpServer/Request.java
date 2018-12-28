package httpServer;

import java.io.InputStream;
import java.rmi.server.UID;
import java.io.IOException;

public class Request {//���������������󣬿����ͻ��˵���Ҫʲô

    private InputStream input;//�ͻ��˷�������������
    private String uri;//������Ҫ�ĸ��ļ�

    public Request(InputStream input) {
        this.input = input;
    }

    //��InputStream�ж�ȡrequest��Ϣ������request�л�ȡuriֵ
    public void parse() {
        StringBuffer request = new StringBuffer(2048);
        int i;
        byte[] buffer = new byte[2048];
        try {
            i = input.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            i = -1;
            System.out.println("input is empty");
        }
        for (int j = 0; j < i; j++) {
            request.append((char) buffer[j]);
        }
        System.out.print(request.toString());
        HttpServer.ui.log.append(request.toString());//----------------------------ui log ����
        HttpServer.ui.log.setCaretPosition(  HttpServer.ui.log.getText().length());
        HttpServer.ui.count.setText("����������"+i+" bytes		");
        uri = parseUri(request.toString());
    }

    /**
     * 
     * requestString��ʽ���£�
     * GET /index.html HTTP/1.1
     * Host: localhost:8080
     * Connection: keep-alive
     * Cache-Control: max-age=0
     * ...
     * �ú���Ŀ�ľ���Ϊ�˻�ȡ/index.html�ַ���
     */
    private String parseUri(String requestString) {
        int index1, index2;
        index1 = requestString.indexOf(' ');//���index.html��ߵĿո������±�
        if (index1 != -1) {
            index2 = requestString.indexOf(' ', index1 + 1);//���index.html�ұ߱ߵĿո������±�
            if (index2 > index1)
                return requestString.substring(index1 + 1, index2);//ȡ�z�ո��м���ַ���
        }
        return null;
    }

    public String getUri() {
        return uri;
    }

}