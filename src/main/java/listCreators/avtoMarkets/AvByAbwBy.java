package listCreators.avtoMarkets;

import htmlConnector.HtmlExecutor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class AvByAbwBy {

    public static void main(String[] args) {
        final List<Company> companies = getCompaniesFromABW();
        companies.addAll(getCompaniesFromAV());
    }

    private static List<Company> getCompaniesFromAV() {
        final List<Company> companies = new ArrayList<>();



        return companies ;
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
        final String content = htmlExecutor.contentGetExecutor("https://kompanii.abw.by/tip_avtohaus");
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
