package workWithFiles.images;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

import org.apache.commons.io.IOUtils;

public class ImageToBase64 {

	public static void main(String[] args) {
		File image = new File("/opt/Downloads/Washington_capitals_alternate_logo.gif");
		byte[] base64 = toBase64(image);
		System.out.println(new String(base64));
	}

	private static byte[] toBase64(File image) {
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); InputStream inputStream = new FileInputStream(image);) {
			IOUtils.copy(inputStream, baos);
			final byte[] byteArray = baos.toByteArray();
			byte[] encodedIcon = Base64.getEncoder().encode(byteArray);
			return encodedIcon;
		} catch (IOException e) {
			throw new RuntimeException("Can't to response icon", e);
		}
	}
}
