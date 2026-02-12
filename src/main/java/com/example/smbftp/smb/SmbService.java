package com.example.smbftp.smb;

import com.hierynomus.smbj.SMBClient;
import com.hierynomus.smbj.auth.AuthenticationContext;
import com.hierynomus.smbj.connection.Connection;
import com.hierynomus.smbj.session.Session;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class SmbService {

    public void populateShares(String host, String user, String pass, TreeView<String> tree, TextArea log) {
        try (SMBClient client = new SMBClient();
             Connection conn = client.connect(host);
             Session session = conn.authenticate(new AuthenticationContext(user, pass.toCharArray(), ""))) {

            TreeItem<String> root = new TreeItem<>(host);
            root.setExpanded(true);

            session.getConnection()
                   .getServer()
                   .listShares()
                   .forEach(share -> root.getChildren().add(new TreeItem<>(share)));

            tree.setRoot(root);
            log.appendText("Loaded shares from " + host + "\n");

        } catch (Exception e) {
            log.appendText("SMB error: " + e.getMessage() + "\n");
        }
    }

    public void listFiles(String share, ListView<String> files, TextArea log) {
        files.getItems().clear();
        files.getItems().add("[Demo] File listing stub for share: " + share);
        log.appendText("Select a share to implement directory browsing next.\n");
    }
}
