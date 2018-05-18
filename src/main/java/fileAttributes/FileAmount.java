package fileAttributes;

import java.io.File;
import java.util.concurrent.CopyOnWriteArrayList;

public class FileAmount {
    private int amount;
    private CopyOnWriteArrayList<Thread> listThread;
    
    public int calc (String path) {
        File[] catalog = new File(path).listFiles();
        
        return counter(catalog);
    }

    private int counter (File[] catalog) {
        listThread = new CopyOnWriteArrayList<Thread>();
        amount = 0;
        for (File file : catalog) {
            if (file.isDirectory()) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        amount += counter(file.listFiles());
                        System.out.println("after " + listThread.size());
                        listThread.remove(listThread.size() - 1);
                    }
                });
                listThread.add(thread);
                System.out.println(listThread.size());
                thread.start();
            }
            else amount++;
        }
        
        while(listThread.size() > 0);
        
        return amount;
    }
    
    public static void main (String[] args) {
        int calc = new FileAmount().calc("d:\\Java\\Study\\Lessons\\src\\main\\java\\lesson50\\");
        System.out.println(calc);
    }
}
