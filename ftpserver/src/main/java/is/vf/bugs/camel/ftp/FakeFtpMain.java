package is.vf.bugs.camel.ftp;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.ClearTextPasswordEncryptor;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;

/**
 * Startup the ftp server on 2121, and load up the spring context.  We can't autostart the ftp server from spring
 * User: karlp
 * Date: 17.11.2009
 * Time: 15:17:34
 */
public class FakeFtpMain {
    public static void main(String[] args) throws Exception {
        final ApplicationContext classPathXmlApplicationContext =
                new ClassPathXmlApplicationContext("/META-INF/spring/context-camel.xml");

        FtpServerFactory serverFactory = new FtpServerFactory();
        ListenerFactory factory = new ListenerFactory();

        // replace the default listener
        factory.setPort(2121);
        serverFactory.addListener("default", factory.createListener());

        PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
        userManagerFactory.setFile(new File("users.properties"));
        userManagerFactory.setPasswordEncryptor(new ClearTextPasswordEncryptor());

        serverFactory.setUserManager(userManagerFactory.createUserManager());

        FtpServer server = serverFactory.createServer();
        server.start();
    }

}
