package listCreators.avtoMarkets;

import htmlConnector.HtmlExecutor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;

public class AvByAbwBy {

    private static final String AV_URL = "https://av.by/company/page/";
    private static final String AV_ELEMENT_CLASS = "dealer-item";
    private static final String AV_LINK_CLASS = "dealer-item-heading";
    private static final String AV_ADDRESS_CLASS = "dealer-item-address";

    private static final String ABW_URL = "https://kompanii.abw.by/tip_avtohaus";
    private static final String ABW_ELEMENT_CLASS = "CompanyListingItem";
    private static final String ABW_ADDRESS_CLASS = "CompanyListingItem__address";
    private static final String ABW_LINK_CLASS = "CompanyListingItem__link";

    public static void main(String[] args) {
        final Set<Company> companies = getCompaniesFromABW();
        companies.addAll(getCompaniesFromAV());
        companies.forEach(System.out::println);
    }

    private static Set<Company> getCompaniesFromAV() {
        final Set<Company> companies = new TreeSet<>();

        int i = 0;
        while (companies.addAll(getCompaniesFromAV(++i)));

        return companies;
    }

    private static Set<Company> getCompaniesFromABW() {
        return getCompaniesFromUrl(ABW_URL, ABW_ELEMENT_CLASS, ABW_ADDRESS_CLASS, ABW_LINK_CLASS, e -> e.childNode(2));
    }

    private static Set<Company> getCompaniesFromAV(final int i) {
        return getCompaniesFromUrl(AV_URL + i, AV_ELEMENT_CLASS, AV_ADDRESS_CLASS, AV_LINK_CLASS,
                                   e -> e.childNode(1).childNode(0).childNode(0));
    }

    private static Set<Company> getCompaniesFromUrl(final String url,
                                                    final String listElementClass,
                                                    final String addressClass,
                                                    final String linkClass,
                                                    final Function<Element, Node> nodeFinder) {
        final Set<Company> companies = new TreeSet<>();
        final Elements companyListingItems = getListElements(url, listElementClass);

        for (final Element element : companyListingItems) {
            final String address = getAddress(element, addressClass);
            if (address.contains("Минск")) {
                final String title = getTitle(element, linkClass, nodeFinder);
                final Company company = new Company(title, getAddressWithoutCity(address));
                companies.add(company);
            }
        }
        return companies;
    }

    private static Elements getListElements(final String address, final String className) {
        final HtmlExecutor htmlExecutor = new HtmlExecutor();
        final String content = htmlExecutor.contentGetExecutor(address);
        Document html = Jsoup.parse(content);
        return html.getElementsByClass(className);
    }

    private static String getAddress(final Element element, final String className) {
        final String addressElement = element.getElementsByClass(className).get(0).text();
        return addressElement.trim().replaceAll("\\([^)]*\\)", "");
    }

    private static String getTitle(final Element element,
                                   final String className,
                                   final Function<Element, Node> nodeFinder) {
        final Element link = element.getElementsByClass(className).get(0);
        final Node node = nodeFinder.apply(link);
        return node.toString().trim()
            .replaceAll("&quot;", "\"")
            .replaceAll("&raquo;", "\"")
            .replaceAll("&laquo;", "\"")
            .replaceAll("&amp;", "&");
    }

    private static String getAddressWithoutCity(final String address) {
        return address
            .replaceAll("г. Минск, ", "")
            .replaceAll("г. Минск,", "")
            .replaceAll("г.Минск, ", "")
            .replaceAll("г.Минск,", "");
    }
}
