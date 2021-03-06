package workWithFiles.fileAttributes.fileOwnerDeleter.gui;

import java.io.File;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

@SuppressWarnings ("serial")
public class Main extends JFrame {
    private static final int DEFAULT = GroupLayout.DEFAULT_SIZE;
    private static final int PREF = GroupLayout.PREFERRED_SIZE;
    private JTextField destinationFolder;
    private JTextField owner;
    private JLabel pathLabel;
    private JLabel nameLabel;
    private JButton browse;
    private JButton findIt;
    private GroupLayout layout;
    private JFileChooser chooser;
    @SuppressWarnings ("unused")
    private JLabel progressLabel;
    private Result result;

    /**
     * Create the application.
     */
    public Main () {
        initialize();
        
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Поиск файлов");
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize () {
        chooserSetup();
        
        pathLabel = new JLabel("Где искать");
        
        nameLabel = new JLabel("Имя пользователя владельца");

        browseSetup();

        fileChooseSetup();

        nameFieldSetup();

        findSetup();

        layoutSetup();
    }

    private void findSetup () {
        findIt = new JButton("Поиск");
        findIt.addActionListener(e -> {
            if (!new File(destinationFolder.getText()).exists()) {
                JOptionPane.showMessageDialog(null, "Неверно указан путь к папке", "Ошибка", JOptionPane.WARNING_MESSAGE);
                return; 
            }
                
            result = new Result(this, destinationFolder.getText(), owner.getText());
            result.run();
            setVisible(false);
        });
    }

    private void chooserSetup () {
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("d:\\"));
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
    }

    private void browseSetup () {
        browse = new JButton("Обзор");
        browse.addActionListener(e -> {
            int returnValue = chooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();
                destinationFolder.setText(selectedFile.getAbsolutePath());
            }
        });
    }

    private void fileChooseSetup () {
        destinationFolder = new JTextField();
        destinationFolder.setText("D:\\Me\\JAVA\\Utils");
        destinationFolder.setColumns(20);
    }

    private void nameFieldSetup () {
        owner = new JTextField();
        owner.setText(System.getProperty("user.name"));
        owner.setColumns(10);
    }

    private void layoutSetup () {
        layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(Alignment.CENTER)
                .addComponent(pathLabel)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(destinationFolder)
                        .addComponent(browse))
                .addComponent(nameLabel)
                .addComponent(owner, PREF, DEFAULT, PREF)
                .addComponent(findIt));
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(pathLabel)
                .addGroup(layout.createParallelGroup(Alignment.CENTER)
                        .addComponent(destinationFolder)
                        .addComponent(browse))
                .addComponent(nameLabel)
                .addComponent(owner, PREF, DEFAULT, PREF)
                .addComponent(findIt));
    }

    public void run () {
        setVisible(true);
    }
}
