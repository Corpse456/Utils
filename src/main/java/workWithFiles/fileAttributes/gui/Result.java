package workWithFiles.fileAttributes.gui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout.Alignment;

@SuppressWarnings ("serial")
public class Result extends JFrame {
    private JLabel result;
    private JList<String> table;
    private JScrollPane scrollPane;
    private JButton selectAll;
    private JButton delete;
    @SuppressWarnings ("unused")
    //listener
    private JButton back;
    private GroupLayout layout;
    
    private String[] findedFiles;
    private String owner;

    public Result (String[] files, String owner) {
        findedFiles = files;
        this.owner = owner;
        
        initialize();
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Результаты");
        pack();
        setLocationRelativeTo(null);
    }

    private void initialize () {
        result = new JLabel("Список фалов пользователя " + owner);
        
        table = new JList<String>(findedFiles);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //высота не работает
        table.setMaximumSize(new Dimension((int) (screenSize.getWidth() / 1.5), (int) (screenSize.getHeight() * 0.7)));
        table.setAutoscrolls(true);
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(table.getBounds());
        //доработать Scroll
        //продумать момент с пустм списком
        //изменять данные в листе в реальном времени
        
        /*private void output () {
            output = new JTextArea();
            output.setFont(new Font("Segoe Print", Font.BOLD, 14));
            output.setBounds(20, 60, getWidth() - 180, 460);
            output.setEditable(false);
            output.setAutoscrolls(true);
            output.setText(TEXT);
            output.setWrapStyleWord(true);
            scrollPane = new JScrollPane(output);
            scrollPane.setBounds(output.getBounds());
            }*/
        
        browseSetup();
        
        findSetup();
        
        layoutSetup();
    }
    
    private void findSetup () {
        delete = new JButton("Поиск");
        delete.addActionListener(e -> {
            //TO DO
        });
    }

    private void browseSetup () {
        selectAll = new JButton("Обзор");
        selectAll.addActionListener(e -> {
            //TO DO
        });
    }
    
    private void layoutSetup () {
        layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup(Alignment.CENTER)
                .addComponent(result)
                .addComponent(scrollPane));
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(result)
                .addComponent(scrollPane));
        
        //добавить кнопки
        //добавить полосу загрузки
    }

    public void run () {
        setVisible(true);
    }
}
