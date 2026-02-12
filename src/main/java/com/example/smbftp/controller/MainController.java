package com.example.smbftp.controller;

import com.example.smbftp.ftp.FtpServerService;
import com.example.smbftp.smb.SmbService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;

public class MainController {

    @FXML private TextField smbHost;
    @FXML private TextField smbUser;
    @FXML private PasswordField smbPass;
    @FXML private TreeView<String> smbTree;
    @FXML private ListView<String> fileList;
    @FXML private TextArea logArea;

    private final SmbService smbService = new SmbService();
    private final FtpServerService ftpServer = new FtpServerService();

    public void browseSmb() {
        smbService.populateShares(smbHost.getText(), smbUser.getText(), smbPass.getText(), smbTree, logArea);
    }

    public void onTreeSelect() {
        TreeItem<String> sel = smbTree.getSelectionModel().getSelectedItem();
        if (sel != null) {
            smbService.listFiles(sel.getValue(), fileList, logArea);
        }
    }

    public void startFtp() {
        ftpServer.start(2121, System.getProperty("user.home") + "/ftp-home");
        logArea.appendText("FTP started on 2121\n");
    }

    public void stopFtp() {
        ftpServer.stop();
        logArea.appendText("FTP stopped\n");
    }

    public void addFtpUser() {
        TextInputDialog u = new TextInputDialog();
        u.setHeaderText("FTP Username");
        u.showAndWait().ifPresent(user -> {
            TextInputDialog p = new TextInputDialog();
            p.setHeaderText("FTP Password");
            p.showAndWait().ifPresent(pass -> {
                ftpServer.addUser(user, pass, System.getProperty("user.home") + "/ftp-home");
                logArea.appendText("FTP user added: " + user + "\n");
            });
        });
    }

    // Drag & drop stub for future SMB upload
    public void onDragOver(DragEvent e) {
        if (e.getDragboard().hasFiles()) e.acceptTransferModes(TransferMode.COPY);
        e.consume();
    }

    public void onDrop(DragEvent e) {
        logArea.appendText("Drag & Drop detected (implement SMB upload here)\n");
        e.setDropCompleted(true);
        e.consume();
    }

    public void exitApp() { System.exit(0); }
}
