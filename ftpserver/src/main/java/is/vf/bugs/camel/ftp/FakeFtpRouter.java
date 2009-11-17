package is.vf.bugs.camel.ftp;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

/**
 * Simple camel router that attempts to write a file to an ftp server once every 10 seconds.
 * User: karlp
 * Date: 17.11.2009
 * Time: 15:14:06
 */
public class FakeFtpRouter extends RouteBuilder {

    private String ftpEndpoint;

    public void configure() throws Exception {
        // 10000 just needs to be bigger than the idletime configured on the ftp server...
        from("timer://foo?fixedRate=ture&period=10000")
                .setHeader(Exchange.FILE_NAME, constant("writeAttempt.txt"))
                .setBody(constant("File written periodically by conan to keep the ftp endpoint alive..."))
                .to(ftpEndpoint);

    }

    public void setFtpEndpoint(String ftpEndpoint) {
        this.ftpEndpoint = ftpEndpoint;
    }
}
