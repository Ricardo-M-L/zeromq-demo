package demo.rep;


import lombok.extern.slf4j.Slf4j;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;


@Slf4j
public class hwclient {

    public static void main(String[] args) {

        try (ZContext context = new ZContext()) {
            System.out.println("Connecting to hello world server");

            //请求应答模式
            ZMQ.Socket socket = context.createSocket(SocketType.REQ);
            socket.connect("tcp://192.168.8.76:5555");

            for (int requestNbr = 0; requestNbr < 10; requestNbr++) {
                String request = "Hello";
                socket.send(request.getBytes(ZMQ.CHARSET), 0);
                log.info("\u001B[32m" + "java客户端第" + (requestNbr + 1) + "通过请求答复模式发送消息：" + request + " 给服务端" + "\u001B[0m");

                byte[] reply = socket.recv(0);
                log.info("\u001B[34m" + "java客户端第" + (requestNbr + 1) + "次通过请求答复模式接收到来自服务端的响应消息："
                        + new String(reply, ZMQ.CHARSET) + "\u001B[0m" + "\n");
            }
        }
    }
}
