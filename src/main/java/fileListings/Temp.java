package fileListings;

import java.io.File;

public class Temp {
    public static void main(String[] args) {
	File file = new File("c:\\1"); // создаем объект на файл test.txt
	if (file.exists()) { // если файл существует, то переименовываем его
	    file.renameTo(new File("c:\\2"));
	} else {
	    System.out.println("File not found!");
	}
    }
}
