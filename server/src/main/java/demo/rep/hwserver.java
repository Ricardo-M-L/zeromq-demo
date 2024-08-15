package demo.rep;

import lombok.extern.slf4j.Slf4j;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;


@Slf4j
public class hwserver
{
  public static void main(String[] args) throws Exception
  {
    try (ZContext context = new ZContext()) {
      //  请求应答模式
      ZMQ.Socket socket = context.createSocket(SocketType.REP);
      //服务端口将监听所有可用的网络接口上的5555端口
      socket.bind("tcp://*:5555");

      while (!Thread.currentThread().isInterrupted()) {
        //recv方法接收来自客户端的请求，会阻塞，直到接收到消息
        byte[] reply = socket.recv(0);
        log.info("\u001B[32m" + "java服务端通过请求应答模式接收到消息为：" + new String(reply, ZMQ.CHARSET) + "\u001B[0m");
        String response = "world";
        //将响应发送回客户端
        socket.send(response.getBytes(ZMQ.CHARSET), 0);
        Thread.sleep(1000); //  Do some 'work'
      }
    }
  }
}
