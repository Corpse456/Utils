package htmlConnector;

import lombok.Data;

import java.util.List;

@Data
public class GoogleResult {

    public List<Item> items;

    @Data
    public class Item {
        public String title;

        public String link;
    }
}
