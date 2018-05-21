package workWithFiles.fileAttributes;

import java.io.File;

public class FolderProperties {

    public int fileAmount (String path) {
        File file = new File(path);
        
        if (file.isFile()) return 1;
        
        if (file.isDirectory()) {
            File[] catalog = file.listFiles();
            int amount = counter(catalog);
            return amount;
        }
        
        return 0;
    }
    
    /**
     * @param f - файл или папка, для которго необходимо посчитать размер
     * @return
     */
    public long calculateSize(File f) {
        if (f.isFile()) return f.length();
        
        long size = 0;
        if (f.listFiles() != null) {
            for (File f1 : f.listFiles()) {
                size += calculateSize(f1);
            }
        }
        return size;
    }

    private int counter (File[] catalog) {
        int amount = 0;
        
        for (File file : catalog) {
            if (file.isDirectory() && file.listFiles() != null) {
                amount += counter(file.listFiles());
            } else amount++;
        }

        return amount;
    }

    public static void main (String[] args) {
        long time = System.nanoTime();
        int calc = new FolderProperties().fileAmount("z:\\Common\\ОАСУ\\Пи-трубы (1) с 24.05.xls");
        System.err.println("time: " + (System.nanoTime() - time));
        System.err.println(calc);
    }
}
