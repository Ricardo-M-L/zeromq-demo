package fz.pubsub;

import org.zeromq.ZMQ;

/**
 * zeromq发布订阅模式服务接受端
 */
public abstract class ZmqSubThread implements Runnable {

    /**
     * ZMQ启动线程数
     */
    private int ZMQThreadCount = 1;

    /**
     * ZMQ监听接收IP地址加端口
     */
    private String ZMQPubAddress;

    /**
     * ZeroMQ 的上下文环境
     * 用于创建和管理ZeroMQ套接字
     */
    private ZMQ.Context context = null;

    /**
     * ZeroMQ 的发布者套接字
     */
    private ZMQ.Socket subSock = null;

    public ZmqSubThread(String ZMQPubAddress, String topic) {
        this.ZMQPubAddress = ZMQPubAddress;
        initZMQ(topic);
    }

    /**
     * 初始化ZMQ对象
     */
    public void initZMQ(String topic) {
        if (ZMQPubAddress == null || "".equals(ZMQPubAddress)) {
            throw new RuntimeException("IP Error!");
        }

        if (Integer.parseInt(ZMQPubAddress.substring(ZMQPubAddress.length() - 4)) == 0) {
            // 如果端口号为0，抛出异常
            throw new RuntimeException("Error! Port cannot be 0.");
        }
        // 如果ZMQ上下文还未创建，则创建
        if (context == null) {
            context = ZMQ.context(ZMQThreadCount);
        }
        subSock = context.socket(ZMQ.SUB);
        String ConUri = ZMQPubAddress;
        subSock.connect(ConUri);
        subSock.subscribe(topic.getBytes(ZMQ.CHARSET));

    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                byte[] topicBytes = subSock.recv(0);
                byte[] contentBytes = subSock.recv(0);
                if (topicBytes == null || contentBytes == null) {
                    continue;
                }
                dealWithReceive(topicBytes, contentBytes);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 处理接收到数据的抽象方法
     */
    public abstract void dealWithReceive(byte[] topicBytes, byte[] contentBytes);

}