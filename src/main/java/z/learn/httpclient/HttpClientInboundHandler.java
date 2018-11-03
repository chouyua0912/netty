package z.learn.httpclient;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;

public class HttpClientInboundHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpResponse) {
            HttpResponse response = (HttpResponse) msg;
            System.out.println("CONTENT_TYPE:" + response.headers().get(HttpHeaders.Names.CONTENT_TYPE));
        }
        if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent) msg;
            ByteBuf buf = content.content();
            System.out.println(buf.toString(io.netty.util.CharsetUtil.UTF_8));
            buf.release();      // 使用完之后需要release
        }
    }
}
/**
 * channelRead:24, HttpClientInboundHandler (z.learn.httpclient)
 * invokeChannelRead:362, AbstractChannelHandlerContext (io.netty.channel)
 * invokeChannelRead:348, AbstractChannelHandlerContext (io.netty.channel)
 * fireChannelRead:340, AbstractChannelHandlerContext (io.netty.channel)
 * fireChannelRead:310, ByteToMessageDecoder (io.netty.handler.codec)
 * fireChannelRead:297, ByteToMessageDecoder (io.netty.handler.codec)
 * callDecode:413, ByteToMessageDecoder (io.netty.handler.codec)
 * channelRead:265, ByteToMessageDecoder (io.netty.handler.codec)
 * invokeChannelRead:362, AbstractChannelHandlerContext (io.netty.channel)
 * invokeChannelRead:348, AbstractChannelHandlerContext (io.netty.channel)
 * fireChannelRead:340, AbstractChannelHandlerContext (io.netty.channel)
 * channelRead:1434, DefaultChannelPipeline$HeadContext (io.netty.channel)
 * invokeChannelRead:362, AbstractChannelHandlerContext (io.netty.channel)
 * invokeChannelRead:348, AbstractChannelHandlerContext (io.netty.channel)
 * fireChannelRead:965, DefaultChannelPipeline (io.netty.channel)
 * read:163, AbstractNioByteChannel$NioByteUnsafe (io.netty.channel.nio)
 * processSelectedKey:645, NioEventLoop (io.netty.channel.nio)
 * processSelectedKeysOptimized:580, NioEventLoop (io.netty.channel.nio)
 * processSelectedKeys:497, NioEventLoop (io.netty.channel.nio)
 * run:459, NioEventLoop (io.netty.channel.nio)
 * run:884, SingleThreadEventExecutor$5 (io.netty.util.concurrent)
 * run:30, FastThreadLocalRunnable (io.netty.util.concurrent)
 * run:748, Thread (java.lang)
 */