package z.learn;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.handler.codec.http.DefaultHttpRequest;

import java.io.IOException;

public class NettyNioHttpServer {

    public static void main(String[] args) throws IOException {
        ServerConfig serverConfig = HttpServerConfigurationParser.parse(default_properties);

    }

    private static final String default_properties = "server.properties";
}
