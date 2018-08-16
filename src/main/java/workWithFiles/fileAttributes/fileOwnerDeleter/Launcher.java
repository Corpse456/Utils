package workWithFiles.fileAttributes.fileOwnerDeleter;

import java.net.UnknownHostException;

import javax.swing.UIManager;

import workWithFiles.fileAttributes.fileOwnerDeleter.gui.Main;

public class Launcher {

    public static void main (String[] args) throws UnknownHostException {
        String systemLook = UIManager.getSystemLookAndFeelClassName();

        try {
            UIManager.setLookAndFeel(systemLook);
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Main().run();
    }
}
