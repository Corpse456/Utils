package workWithFiles.fileAttributes.fileOwnerDeleter.finder;

import java.io.File;
import java.util.ArrayList;

import javax.swing.AbstractListModel;

@SuppressWarnings ("serial")
public class MyModel extends AbstractListModel<File> {

    public static final String[] sortTypes = {"Полный путь", "Имя", "Размер", "Дата изменения"};
    private ArrayList<File> files = new ArrayList<>();

    @Override
    public int getSize () {
        return files.size();
    }

    @Override
    public File getElementAt (int index) {
        return files.get(index);
    }

    public void add (File file) {
        int index = files.size();
        files.add(file);
        fireIntervalAdded(this, index, index);
    }
    
    public void remove (File file) {
        int index = files.indexOf(file);
        files.remove(file);
        if (index >= 0) {
            fireIntervalRemoved(this, index, index);
        }
    }

    public boolean isEmpty () {
        return files.isEmpty();
    }

    public void sort (String currentSortType, boolean increase) {
        int direction = increase ? 1 : -1;
        if (sortTypes[0].equals(currentSortType)) {
            files.sort((o1, o2) -> o1.getAbsolutePath().compareTo(o2.getAbsolutePath()) * direction);
        } else if (sortTypes[1].equals(currentSortType)) {
            files.sort((o1, o2) -> o1.getName().compareTo(o2.getName()) * direction);
        } else if (sortTypes[2].equals(currentSortType)) {
            files.sort((o1, o2) -> {
                long one = o1.length();
                long two = o2.length();
                return (one > two ? 1 : one < two ? -1 : 0) * direction;
            });
        } else if (sortTypes[3].equals(currentSortType)) {
            files.sort((o1, o2) -> {
                long one = o1.lastModified();
                long two = o2.lastModified();
                return (one > two ? 1 : one < two ? -1 : 0) * direction;
            });
        }
        fireContentsChanged(this, 0, files.size());
    }

    public int size () {
        return files.size();
    }
}
