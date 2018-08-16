package workWithFiles.fileAttributes.fileOwnerDeleter.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import workWithFiles.fileAttributes.fileOwnerDeleter.finder.FileOwner;
import workWithFiles.fileAttributes.fileOwnerDeleter.finder.MyModel;

@SuppressWarnings ("serial")
public class Result extends JFrame {
    private static final int MINIMUM_HEIGTH = 5;
    private JLabel result;
    // listener
    private JList<File> table;
    private JScrollPane scrollPane;
    private JLabel status = new JLabel();
    private JButton selectAll;
    private JButton delete;
    private JButton back;
    private JLabel sortLabel = new JLabel("Сортировать по");
    private JComboBox<String> sortChoose = new JComboBox<>(MyModel.sortTypes);
    private ButtonGroup radioButtons = new ButtonGroup();
    private JRadioButton increase = new JRadioButton("По возрастанию", true);;
    private JRadioButton decrease = new JRadioButton("По убыванию", false);
    private JProgressBar progressBar;
    private JLabel progressLabel = new JLabel("Подождите. Это может занять несколько минут");
    private GroupLayout layout;

    private String oldSortType;
    private String owner;
    private String folder;
    private Main main;
    private FileOwner fileOwner;
    private MyModel content;
    private int rows;

    public Result (Main main, String folder, String owner) {
        this.main = main;
        this.owner = owner;
        this.folder = folder;

        initialize();

        setResizable(false);
    }

    private void initialize () {
        content = new MyModel();
        fileOwner = new FileOwner(content, this);

        new Thread( () -> {
            if (!fileOwner.addListFilesOfCurrentOwnertoModel(folder, owner)) return;

            progressBar.setIndeterminate(false);
            progressLabel.setVisible(false);

            setFieldsEnabled(true);

            if (content.isEmpty()) JOptionPane.showMessageDialog(null,
                    "В папке " + folder + " файлы пользователя " + owner + " не найдены", "Файлы не найдены",
                    JOptionPane.INFORMATION_MESSAGE);
        }).start();

        result = new JLabel("Список фалов пользователя " + owner);
        status.setVisible(false);

        progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);

        sortingSetup();

        tableSetup();
        selectAllSetup();
        deleteSetup();
        backSetup();
        layoutSetup();
        setFieldsEnabled(false);
        windowSettings();
    }

    private void sortingSetup () {
        radioButtons.add(increase);
        radioButtons.add(decrease);
        increase.addItemListener(l -> {
            content.sort(oldSortType, increase.isSelected());
        });

        oldSortType = sortChoose.getSelectedItem().toString();
        sortChoose.addActionListener(l -> {
            String currentSortType = sortChoose.getSelectedItem().toString();
            if (currentSortType.equals(oldSortType)) return;

            content.sort(currentSortType, increase.isSelected());
            oldSortType = currentSortType;
        });
    }

    private void setFieldsEnabled (boolean enable) {
        increase.setEnabled(enable);
        decrease.setEnabled(enable);
        sortChoose.setEnabled(enable);
        selectAll.setEnabled(enable);
    }

    public void repackWindow () {
        pack();
        setVisibleRowCount();
        setLocationRelativeTo(null);
    }

    private void tableSetup () {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        rows = (int) (screenSize.getHeight() / 16 * 0.5);

        table = new JList<File>(content);
        table.setAutoscrolls(true);
        table.addListSelectionListener(l -> {
            List<File> selected = table.getSelectedValuesList();
            if (selected.isEmpty()) {
                status.setVisible(false);
                repackWindow();
                return;
            }

            status.setVisible(true);
            long size = 0;
            for (File file : selected) {
                size += file.length();
            }
            String sizeInMb = ((int) ((size / 1024.0 / 1024) * 1000)) / 1000.0 + "";
            status.setText("Размер выбранных файлов: " + sizeInMb + " Мб");
            repackWindow();
        });
        setVisibleRowCount();
        table.addListSelectionListener(l -> delete.setEnabled(true));
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(table.getBounds());
    }

    public void setVisibleRowCount () {
        table.setVisibleRowCount(rowcount());
    }

    private int rowcount () {
        return content.size() > rows ? rows : content.size() < MINIMUM_HEIGTH ? MINIMUM_HEIGTH : content.size();
    }

    private void windowSettings () {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Результаты");
        pack();
        setLocationRelativeTo(null);
    }

    private void backSetup () {
        back = new JButton("Назад");
        back.addActionListener(e -> {
            main.setVisible(true);
            fileOwner.stop();
            dispose();
        });
    }

    private void deleteSetup () {
        delete = new JButton("Удалить");
        delete.addActionListener(e -> {
            new Thread( () -> {
                List<File> values = table.getSelectedValuesList();
                StringBuilder undeletedFiles = new StringBuilder();
                StringBuilder undeletedFolders = new StringBuilder();

                progressBar.setMaximum(values.size());
                progressBar.setValue(0);
                values.forEach(v -> {
                    if (v.delete()) {
                        content.remove(v);
                        progressBar.setValue(progressBar.getValue() + 1);
                    } else {
                        if (v.isFile()) undeletedFiles.append(v + "\n");
                        else if (v.isDirectory()) undeletedFolders.append(v + "\n");
                    }
                });
                if (undeletedFiles.length() != 0 || undeletedFolders.length() != 0) {
                    String files = undeletedFiles.length() == 0 ? ""
                            : "Не удаётся удалить следующие файлы:\n " + undeletedFiles + "\n";
                    String folders = undeletedFolders.length() == 0 ? ""
                            : "Нельзя удалить непустой каталог:\n" + undeletedFolders;
                    JOptionPane.showMessageDialog(null, files + folders, "Ошибка при удалении",
                            JOptionPane.WARNING_MESSAGE);
                }
                delete.setEnabled(false);
            }).start();
        });
        delete.setEnabled(false);
    }

    private void selectAllSetup () {
        selectAll = new JButton("Выбрать всё");
        selectAll.addActionListener(e -> {
            table.setSelectionInterval(0, content.size() - 1);
            delete.setEnabled(true);
        });
    }

    private void layoutSetup () {
        layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup().addComponent(result)
                .addGroup(layout.createSequentialGroup().addComponent(sortLabel).addComponent(sortChoose)
                        .addGroup(layout.createSequentialGroup().addComponent(increase).addComponent(decrease)))
                .addGroup(layout.createSequentialGroup().addComponent(scrollPane).addComponent(status))
                .addGroup(Alignment.CENTER, layout.createParallelGroup().addComponent(progressLabel))
                .addGroup(layout.createSequentialGroup().addComponent(delete).addComponent(selectAll)
                        .addComponent(progressBar).addComponent(back)));
        layout.setVerticalGroup(layout.createSequentialGroup().addComponent(result)
                .addGroup(layout.createParallelGroup().addComponent(sortLabel).addComponent(sortChoose)
                        .addGroup(layout.createParallelGroup().addComponent(increase).addComponent(decrease)))
                .addGroup(layout.createParallelGroup().addComponent(scrollPane).addComponent(status))
                .addComponent(progressLabel).addGroup(layout.createParallelGroup().addComponent(delete)
                        .addComponent(selectAll).addComponent(progressBar).addComponent(back)));
    }

    public void run () {
        setVisible(true);
    }
}
