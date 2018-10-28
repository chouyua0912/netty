package z.learn.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

import static java.lang.System.exit;

/**
 * Selector 把Socket注册到Selector上，监听IO事件，选择准备好的事件进行处理
 *
 * 都继承于SelectableChannel
 * ServerSocketChannel
 * SocketChannel
 */
public class NioServerImpl implements Runnable {

    private Selector selector;      // 多路复用器   用来注册
    private ServerSocketChannel serverSocketChannel;

    private volatile boolean stop = false;

    public NioServerImpl(int port) {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();       // 创建ServerSocket实例，然后进行配置
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(port), 1024);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);     // 注册监听事件。 Java底层有原生的监听分发器？
            System.out.println("Nio Server start on port:" + port);
        } catch (IOException e) {
            e.printStackTrace();
            exit(1);
        }
    }

    public void run() {
        while (!stop) {
            try {
                selector.select(1000);                                  // 去poll准备好的channel，处理他们的事件
                Set<SelectionKey> selectedKeys = selector.selectedKeys();       // 获取前一次select到的事件

                Iterator<SelectionKey> iterator = selectedKeys.iterator();
                SelectionKey selectionKey = null;
                while (iterator.hasNext()) {
                    selectionKey = iterator.next();
                    iterator.remove();

                    NioServerInputProcessDelegate.processInput(selector, selectionKey);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }
}
