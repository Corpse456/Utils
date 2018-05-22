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
        int calc = new FolderProperties().fileAmount("z:\\");
        System.err.println("time: " + (System.nanoTime() - time));
        System.err.println(calc);
        
        /*long calculateSize = new FolderProperties().calculateSize(new File("z:\\ASU\\Common\\"));
        System.out.println(calculateSize);*/
    }
}
