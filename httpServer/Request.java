package httpServer;

import java.io.InputStream;
import java.rmi.server.UID;
import java.io.IOException;

public class Request {//仅仅用来解析请求，看看客户端到底要什么

    private InputStream input;//客户端发过来的输入流
    private String uri;//分析出要哪个文件

    public Request(InputStream input) {
        this.input = input;
    }

    //从InputStream中读取request信息，并从request中获取uri值
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
        HttpServer.ui.log.append(request.toString());//----------------------------ui log 更新
        HttpServer.ui.log.setCaretPosition(  HttpServer.ui.log.getText().length());
        HttpServer.ui.count.setText("输入流量："+i+" bytes		");
        uri = parseUri(request.toString());
    }

    /**
     * 
     * requestString形式如下：
     * GET /index.html HTTP/1.1
     * Host: localhost:8080
     * Connection: keep-alive
     * Cache-Control: max-age=0
     * ...
     * 该函数目的就是为了获取/index.html字符串
     */
    private String parseUri(String requestString) {
        int index1, index2;
        index1 = requestString.indexOf(' ');//获得index.html左边的空格所在下标
        if (index1 != -1) {
            index2 = requestString.indexOf(' ', index1 + 1);//获得index.html右边边的空格所在下标
            if (index2 > index1)
                return requestString.substring(index1 + 1, index2);//取倆空格中间的字符串
        }
        return null;
    }

    public String getUri() {
        return uri;
    }

}