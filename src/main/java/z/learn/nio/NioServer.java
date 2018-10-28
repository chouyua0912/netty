package z.learn.nio;


public class NioServer {

    public static void main(String[] args) {
        int port = 8180;
        if (null != args && args.length > 0) {
            port = Integer.parseInt(args[0]);
        }

        NioServerImpl nioServer = new NioServerImpl(port);
        Thread t = new Thread(nioServer, "NioServerThread");
        t.start();

        System.out.println(Thread.currentThread().getName() + ", end.");
    }
}
