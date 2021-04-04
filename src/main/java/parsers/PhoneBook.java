package parsers;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.json.JSONArray;
import org.json.JSONObject;
import workWithFiles.fileIO.ReaderFromFile;
import workWithFiles.fileIO.WriterToFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PhoneBook {

    public static void main(String[] args) {
        List<User> users = parseExistedPhoneBook("D:/resultOriginal.json");
//        final String result = writeInRightFormat(users);
        writeToFile(result, "D:/Result.vcs");
    }

    private static List<User> parseExistedPhoneBook(final String path) {
        final List<User> users = new ArrayList<>();
        final String content = readFromFile(path);
        final JSONObject jsonObject = new JSONObject(content);
        final JSONArray jsonArray = jsonObject.getJSONObject("contacts").getJSONArray("list");
        for (final Object o : jsonArray) {
            final JSONObject object = (JSONObject) o;
            final String phone = object.getString("phone_number");
            if (phone == null) {
                continue;
            }
            final String firstName = object.getString("first_name");
            final String lastName = object.getString("last_name");
            users.add(new User(firstName, lastName, phone));
        }
        return users;
    }

    private static String readFromFile(final String path) {
        final ReaderFromFile reader = new ReaderFromFile(path);
        final String content = reader.readAll();
        reader.close();
        return content;
    }

    private static String writeInRightFormat(final List<User> users) {
        return users.stream().map(User::toString).collect(Collectors.joining());
    }

    private static void writeToFile(final String result, final String path) {
        final WriterToFile writer = new WriterToFile(path);
        writer.write(result);
        writer.close();
    }

    @Data
    @AllArgsConstructor
    private static class User {
        private String firstName;
        private String lastName;
        private String phone;

        @Override
        public String toString() {
            final String encodedName = getEncodedName();
            return "BEGIN:VCARD\n" +
                   "VERSION:2.1\n" +
                   "N;CHARSET=UTF-8;ENCODING=QUOTED-PRINTABLE:;" + encodedName + ";;;\n" +
                   "FN;CHARSET=UTF-8;ENCODING=QUOTED-PRINTABLE:" + encodedName + "\n" +
                   "TEL;CELL:" + phone + "\n" +
                   "END:VCARD";
        }

        private String getEncodedName() {
            return firstName + " " + lastName;
        }
    }
}
