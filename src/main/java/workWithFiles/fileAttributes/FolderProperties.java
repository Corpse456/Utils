package workWithFiles.fileAttributes;

import java.io.File;

public class FolderProperties {

    public int fileAmount (String path) {
        File file = new File(path);
        
        if (file.isFile()) return 1;
        
        if (file.isDirectory()) {
            File[] catalog = file.listFiles();
            //вызвать counter, затем пройтись по списку складывая значения в нём
            int amount = counter(catalog);
            return amount;
        }
        
        return 0;
    }
    
    private int counter (File[] catalog) {
        int amount = 0;
        
        for (File file : catalog) {
            if (file.isDirectory() && file.listFiles() != null) {
                //не amount+= , а просто рекурсивный вызов
                amount += counter(file.listFiles());
            } else amount++;
        }
        //не возвращать, а добавлять в потокобезопасный список, если не равно 0
        //!!!!!или потокобезопасное сложение с переменной!!!!
        return amount;
    }
    
    /**
     * @param path to file or folder for which needs to calculate the size
     * @return <b>long</b> size
     */
    public long calculateSize(String path) {
        return calculateSize(new File(path));
    }
    
    /**
     * @param file for which needs to calculate the size
     * @return <b>long</b> size
     */
    public long calculateSize(File file) {
        if (file.isFile()) return file.length();
        
        long size = 0;
        if (file.listFiles() != null) {
            for (File f1 : file.listFiles()) {
                size += calculateSize(f1);
            }
        }
        return size;
    }

    public static void main (String[] args) {
        long time = System.nanoTime();
        int calc = new FolderProperties().fileAmount("z:\\");
        System.err.println("time: " + (System.nanoTime() - time));
        System.err.println(calc);
        
        /*long calculateSize = new FolderProperties().calculateSize(new File("z:\\ASU\\Common\\"));
        System.out.println(calculateSize);*/
    }
}
