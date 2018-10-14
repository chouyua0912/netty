package z.learn;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class HttpServerConfigurationParser {

    public static ServerConfig parse(String configPath) throws IOException {
        URL url = HttpServerConfigurationParser
                .class.getClassLoader().getResource(configPath);
        FileInputStream prop_file = new FileInputStream(url.getFile());

        Properties properties = new Properties();
        properties.load(prop_file);

        ServerConfig config = new ServerConfig();

        config.setPort(Integer.parseInt(properties.getProperty("server.port")));

        return config;
    }
}
