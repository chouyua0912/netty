package z.learn.nio;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NioServerInputProcessDelegate {

    public static void processInput(Selector selector, SelectionKey key) throws IOException {
        if (key.isValid()) {
            if (key.isAcceptable()) {                   // key有效，已经触发了可以建立连接的事件
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel sc = ssc.accept();        // 建立一个新的监听连接
                sc.configureBlocking(false);                    // 上新建立的连接会再次被注册到相同的selector上，下次select的时候可能就会查到read事件
                sc.register(selector, SelectionKey.OP_READ);    // 把socket注册到selector
            }

            if (key.isReadable()) {
                SocketChannel sc = (SocketChannel) key.channel();
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int readBytes = sc.read(readBuffer);    // 读取从socket，存入readBuffer
                if (readBytes > 0) {                    // 有读取到数据
                    readBuffer.flip();

                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);

                    String body = new String(bytes, "UTF-8");
                    System.out.println("Server recived:" + body);
                } else if (readBytes < 0) {             //  对方已经关闭链路
                    key.cancel();
                    sc.close();
                } else {                                //  未读取到数据

                }
            }

        }
    }

    public static void processOutput(SocketChannel sc, String message) throws IOException {
        if (StringUtils.isNotEmpty(message)) {
            byte[] bytes = message.getBytes();
            ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
            buffer.put(bytes);
            buffer.flip();
            sc.write(buffer);
        }
    }
}
