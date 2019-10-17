package listCreators.avtoMarkets;

import htmlConnector.HtmlExecutor;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class AvByAbwBy {

    private static final String ABW_ADDRESS = "https://kompanii.abw.by/tip_avtohaus";
    private static final String AV_ADDRESS = "https://av.by/company/page/";

    public static void main2(String[] args) {
        final List<Company> companies = getCompaniesFromABW();
        companies.addAll(getCompaniesFromAV());
    }

    public static void main(String[] args) {
        getCompaniesFromAV();
    }

    private static List<Company> getCompaniesFromAV() {
        final List<Company> companies = new ArrayList<>();

        final HtmlExecutor htmlExecutor = new HtmlExecutor();
        String content = " ";
        for (int i = 1;  StringUtils.isNotEmpty(content); i++) {
            content = htmlExecutor.contentGetExecutor(AV_ADDRESS + i);
            System.out.println("content = " + content);
        }

        return companies;
    }

    private static List<Company> getCompaniesFromABW() {
        final List<Company> companies = new ArrayList<>();
        final Elements companyListingItem = getListElements();

        for (final Element element : companyListingItem) {
            final String address = getAddress(element);
            if (address.contains("Минск")) {
                final String title = getTitle(element);
                final Company company = new Company(title, getAddressWithoutCity(address));
                companies.add(company);
            }
        }
        return companies;
    }

    private static String getAddressWithoutCity(final String address) {
        return address
            .replaceAll("г. Минск, ", "")
            .replaceAll("г. Минск,", "")
            .replaceAll("г.Минск, ", "")
            .replaceAll("г.Минск,", "");
    }

    private static Elements getListElements() {
        final HtmlExecutor htmlExecutor = new HtmlExecutor();
        final String content = htmlExecutor.contentGetExecutor(ABW_ADDRESS);
        Document html = Jsoup.parse(content);
        return html.getElementsByClass("CompanyListingItem");
    }

    private static String getTitle(final Element element) {
        final Elements link = element.getElementsByClass("CompanyListingItem__link");
        return link.get(0).childNode(2).toString().trim();
    }

    private static String getAddress(final Element element) {
        final String addressElement = element.getElementsByClass("CompanyListingItem__address").get(0).text();
        return addressElement.trim().replaceAll("\\([^)]*\\)", "");
    }
}
