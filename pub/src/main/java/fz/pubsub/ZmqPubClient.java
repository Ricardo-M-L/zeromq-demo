package fz.pubsub;

import org.zeromq.ZMQ;

/**
 * ZeroMQ 发布订阅模式客户端类
 * 此类用于在ZeroMQ中作为发布者，发送数据到订阅者
 */
public class ZmqPubClient {

    /**
     * ZeroMQ 启动时使用的线程数
     * 这是一个配置参数，可以根据需要调整
     */
    private int ZMQThreadCount = 1;

    /**
     * ZeroMQ 数据广播的协议类型加IP地址加端口（如tcp://localhost:5555）
     * 发布者将在这个地址上发送数据
     */
    private String ZMQPubAddress;


    /**
     * ZeroMQ 的上下文环境
     * 用于创建和管理ZeroMQ套接字
     */
    private ZMQ.Context context;

    /**
     * ZeroMQ 的发布者套接字
     */
    private ZMQ.Socket pubSock;

    /**
     * 构造函数
     * 初始化发布者客户端，设置IP和端口，并尝试初始化ZMQ环境
     * @param ZMQPubAddress 数据广播的IP地址加端口
     */
    public ZmqPubClient(String ZMQPubAddress) {
        this.ZMQPubAddress = ZMQPubAddress;
        // 如果pubSock还未初始化，则初始化ZMQ环境
        if (pubSock == null) {
            initZMQ();
        }
    }

    /**
     * 初始化ZeroMQ对象
     * 包括创建ZMQ上下文，套接字，并绑定到指定的IP和端口
     */
    private void initZMQ() {
        // 检查发布地址是否为空
        if (ZMQPubAddress == null || "".equals(ZMQPubAddress)) {
            throw new RuntimeException("IP Error!");
        }
        if (Integer.parseInt(ZMQPubAddress.substring(ZMQPubAddress.length()-4)) == 0)  {
            // 如果端口号为0，抛出异常
            throw new RuntimeException("Error! Port cannot be 0.");
        }

        // 如果ZMQ上下文还未创建，则创建
        if (context == null) {
            context = ZMQ.context(ZMQThreadCount);
        }
        // 如果端口号有效，则创建发布者套接字并绑定
        pubSock = context.socket(ZMQ.PUB);
        String bindUri = ZMQPubAddress;
        pubSock.bind(bindUri);

        // 等待一小段时间以确保绑定完成（这里通常不需要，除非有特别的理由）
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送数据
     * @param msg 要发送的数据字节数组
     */
    public void sendData(byte[] topic,byte[] msg) {
        // 假设我们有两个主题："weather" 和 "news"

        // 发送初始的主题信息（注意：在PUB套接字中，这通常不是必需的）
        pubSock.send(topic, ZMQ.SNDMORE);
        // 使用非阻塞方式发送数据
        pubSock.send(msg, ZMQ.NOBLOCK);
    }

    /**
     * 关闭ZeroMQ资源
     * 释放发布者套接字和上下文
     */
    public void close() {
        if (pubSock != null) {
            pubSock.close();
        }
        if (context != null) {
            context.term();
        }
    }

}