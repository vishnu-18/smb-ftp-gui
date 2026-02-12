package com.example.smbftp.ftp;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import java.io.File;

public class FtpServerService {
    private FtpServer server;
    private final PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();

    public void start(int port, String homeDir) {
        try {
            new File(homeDir).mkdirs();
            FtpServerFactory factory = new FtpServerFactory();
            ListenerFactory lf = new ListenerFactory();
            lf.setPort(port);
            factory.addListener("default", lf.createListener());
            factory.setUserManager(userManagerFactory.createUserManager());
            server = factory.createServer();
            server.start();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void addUser(String u, String p, String home) {
        try {
            BaseUser user = new BaseUser();
            user.setName(u); user.setPassword(p); user.setHomeDirectory(home);
            userManagerFactory.createUserManager().save(user);
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void stop() { if (server != null && !server.isStopped()) server.stop(); }
}
