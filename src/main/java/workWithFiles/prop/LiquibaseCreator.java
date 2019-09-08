package workWithFiles.prop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LiquibaseCreator {

    private static final List<Integer> PERSON_IDS = Arrays.asList(11, 20, 122, 222, 10011, 10014, 10021);
    private static final List<Integer> COMPANY_IDS =
        Arrays.asList(13, 14, 15, 16, 17, 18, 19, 25, 26, 27, 28, 29, 110, 10012, 10013, 10022);
    private static final Integer BASIC_ID = 112;
    private static final List<Integer> ALL_WITHOUT_BASIC_IDS = collectWithout();
    private static final List<Integer> ALL_PERSONS_IDS = collectAllPersons();
    private static final List<Integer> ALL_IDS = collectAll();
    private static final String FEATURE = "{FEATURE}";
    private static final String TITLE = "{TITLE}";
    private static final String ID = "{ID}";
    private static final String TICK = "{TICK}";
    private static final String COUNT = "{COUNT}";
    private static final String GROUP = "{GROUP}";
    private static final String FEATURE04 = "        <insert tableName=\"membership_plan_feature\">\n" +
                                            "            <column name=\"feature\">" + FEATURE + "</column>\n" +
                                            "            <column name=\"features_group\">" + GROUP + "</column>\n" +
                                            "            <column name=\"title\">" + TITLE + "</column>\n" +
                                            "            <column name=\"membership_plan_condition_id\">" + ID +
                                            "</column>\n" +
                                            "            <column name=\"addition_symbol_key\">" + TICK + "</column>\n" +
                                            "            <column name=\"addition_symbol_count\">" + COUNT +
                                            "</column>\n" +
                                            "        </insert>\n";
    private static final String FEATURE_ANOTHER = "        <insert tableName=\"membership_plan_feature\">\n" +
                                                  "            <column name=\"feature\">" + FEATURE + "</column>\n" +
                                                  "            <column name=\"features_group\">" + GROUP +
                                                  "</column>\n" +
                                                  "            <column name=\"membership_plan_condition_id\">" + ID +
                                                  "</column>\n" +
                                                  "            <column name=\"addition_symbol_key\">" + TICK +
                                                  "</column>\n" +
                                                  "            <column name=\"addition_symbol_count\">" + COUNT +
                                                  "</column>\n" +
                                                  "        </insert>\n";

    private static List<Integer> collectAllPersons() {
        final ArrayList<Integer> ids = new ArrayList<>(PERSON_IDS);
        ids.add(BASIC_ID);
        return ids;
    }

    private static List<Integer> collectAll() {
        final List<Integer> ids = new ArrayList<>(ALL_WITHOUT_BASIC_IDS);
        ids.add(BASIC_ID);
        return ids;
    }

    private static List<Integer> collectWithout() {
        final ArrayList<Integer> ids = new ArrayList<>(PERSON_IDS);
        ids.addAll(COMPANY_IDS);
        return ids;
    }

    public static void main(String[] args) {
        final StringBuilder result = new StringBuilder();
        for (final Integer id : ALL_IDS) {
            group04(result, id);
            group05(result, id);
            group06(result, id);
            group07(result, id);
            group08(result, id);
            group09(result, id);
            group10(result, id);
            group11(result, id);

            result.append("\n");
        }

        System.out.println(result.toString());
    }

    private static void group04(final StringBuilder result, final Integer id) {
        final String group = "GROUP04";
        if (id.equals(BASIC_ID)) {
            result.append(getReplaced04(id, "INDIVIDUAL BASIC PROFILE - limited features", group,
                                        "BASIC PROFILE FEATURES", "TICK", "1"));
        } else if (PERSON_IDS.contains(id)) {
            result.append(getReplaced04(id, "INDIVIDUAL PREMIUM PROFILE - unlimited features", group,
                                        "PREMIUM PROFILE FEATURES", "TICK", "1"));
        } else {
            result.append(getReplaced04(id, "COMPANY PROFILE", group,
                                        "COMPANY PROFILE FEATURES", "TICK", "1"));
            result.append(getReplaced04(id,
                                        "<![CDATA[<b>1 ADMIN PROFILE - INDIVIDUAL PREMIUM PROFILE - unlimited features</b>]]>",
                                        group, "COMPANY PROFILE FEATURES", "TICK", "1"));
            result.append(getReplaced04(id, "<![CDATA[<b>3 COMPANY USER PREMIUM PROFILES</b>]]>",
                                        group, "COMPANY PROFILE FEATURES", "TICK", "1"));
            result.append(getReplaced04(id, "<![CDATA[<b>ADDITIONAL COMPANY USER PREMIUM PROFILES 50% OFF*</b>]]>",
                                        group, "COMPANY PROFILE FEATURES", "TICK", "1"));
        }
    }

    private static void group05(final StringBuilder result, final Integer id) {
        final String group = "GROUP05";
        if (COMPANY_IDS.contains(id)) {
            result.append(getReplaced(id, "<![CDATA[<b>Unlimited</b> Company TEAM USER PROFILES]]>", group,
                                      "TICK", "1"));
        }
        result.append(getReplaced(id, "<![CDATA[<b>Unlimited</b> Member & Companies Connections]]>", group,
                                  "TICK", "1"));
        result.append(getReplaced(id, "<![CDATA[<b>Unlimited</b> Following & Followers]]>", group,
                                  "TICK", "1"));
        result.append(getReplaced(id, "<![CDATA[<b>Unlimited</b> Member & Companies Searches]]>", group,
                                  "TICK", "1"));

        if (ALL_PERSONS_IDS.contains(id)) {
            result.append(getReplaced(id, "<![CDATA[Individual Newsfeed (In Development)]]>", group,
                                      "TICK", "1"));
            result.append(getReplaced(id, "<![CDATA[Travel Industry Media Newsfeed (In Development)]]>", group,
                                      "TICK", "1"));
            result.append(getReplaced(id, "Placement in Search Results", group, "TICK", "1"));
            result.append(getReplaced(id, "<![CDATA[<b>Unlimited</b> Instant Messaging]]>", group, "TICK", "1"));
        } else {
            result.append(getReplaced(id, "<![CDATA[Placement & Company Logo Shown in Search Results]]>", group,
                                      "TICK", "1"));
            result.append(getReplaced(id, "<![CDATA[Company Newsfeed (In Development)]]>", group,
                                      "TICK", "1"));
            result.append(getReplaced(id, "<![CDATA[<b>Unlimited</b> Instant & GROUP Messaging]]>", group,
                                      "TICK", "1"));
        }
        if (ALL_WITHOUT_BASIC_IDS.contains(id)) {
            result.append(getReplaced(id, "<![CDATA[<b>Advanced Search by Location or Categories</b>]]>", group,
                                      "TICK", "1"));
        }
    }

    private static void group06(final StringBuilder result, final Integer id) {
        final String group = "GROUP06";
        if (BASIC_ID.equals(id)) {
            result.append(getReplaced(id, "<![CDATA[Access to Fam trips - <b>only view</b>]]>", group,
                                      "TICK", "1"));
        } else {
            result.append(getReplaced(id, "<![CDATA[Access to Fam trips]]>", group, "TICK", "1"));
        }

        if (PERSON_IDS.contains(id)) {
            result.append(getReplaced(id, "<![CDATA[Apply to FAM Trips]]>", group, "TICK", "1"));
        } else if (COMPANY_IDS.contains(id)) {
            result.append(getReplaced(id, "<![CDATA[<b>Unlimited</b> FAM Trips Posting]]>", group,
                                      "TICK", "1"));
            result.append(getReplaced(id, "<![CDATA[<b>Unlimited</b> FAM Trips Application Notifications]]>",
                                      group, "TICK", "1"));
        }
    }

    private static void group07(final StringBuilder result, final Integer id) {
        final String group = "GROUP07";
        result.append(getReplaced(id, "<![CDATA[Access to Travel Industry Rates]]>", group, "TICK", "1"));

        if (COMPANY_IDS.contains(id)) {
            result.append(getReplaced(id, "<![CDATA[<b>Unlimited</b> Travel Industry Rates Posting]]>", group,
                                      "TICK", "1"));
        }
    }

    private static void group08(final StringBuilder result, final Integer id) {
        final String group = "GROUP08";
        if (BASIC_ID.equals(id)) {
            result.append(getReplaced(id, "<![CDATA[Access to Marketplace - <b>only view</b>]]>", group,
                                      "TICK", "1"));
        } else {
            result.append(getReplaced(id, "<![CDATA[Access to Marketplace]]>", group, "TICK", "1"));
            result.append(getReplaced(id, "<![CDATA[<b>Unlimited</b> Jobs Posting]]>", group, "TICK", "1"));
            result.append(getReplaced(id, "<![CDATA[<b>Unlimited</b> B2B Ads Posting]]>", group, "TICK", "1"));
            result.append(getReplaced(id, "<![CDATA[<b>Unlimited</b> Publishing Marketplace Ads]]>", group,
                                      "TICK", "1"));
            result.append(getReplaced(id, "<![CDATA[<b>Unlimited</b> Meetups Posting]]>", group, "TICK", "1"));
            result.append(getReplaced(id, "<![CDATA[<b>Unlimited</b> Tourism Events Posting]]>", group,
                                      "TICK", "1"));
            result.append(getReplaced(id, "<![CDATA[<b>Unlimited</b> Webinar Posting]]>", group, "TICK", "1"));
            result.append(getReplaced(id, "<![CDATA[<b>Unlimited</b> Education Programs Posting]]>", group,
                                      "TICK", "1"));
            result.append(getReplaced(id, "<![CDATA[<b>Unlimited</b> Exchange Program Posting]]>", group,
                                      "TICK", "1"));
        }
    }

    private static void group09(final StringBuilder result, final Integer id) {
        final String group = "GROUP09";
        result.append(getReplaced(id, "<![CDATA[Access to Community Forum]]>", group, "TICK", "1"));
    }

    private static void group10(final StringBuilder result, final Integer id) {
        final String group = "GROUP10";

        if (COMPANY_IDS.contains(id)) {
            result.append(getReplaced(id, "<![CDATA[Affiliate Program (on request)]]>", group, "TICK", "1"));
        }
        result.append(getReplaced(id, "<![CDATA[Referral Program]]>", group, "TICK", "1"));
        result.append(getReplaced(id, "<![CDATA[Email/Chat Support]]>", group, "TICK", "1"));
    }

    private static void group11(final StringBuilder result, final Integer id) {
        final String group = "GROUP11";
        if (COMPANY_IDS.contains(id)) {
            result.append(
                getReplaced(id, "<![CDATA[<b>50% discount for each additional Premium Profile (first year)</b>]]>",
                            group, "STAR", "1"));
        }
    }

    private static String getReplaced04(final Integer id, final String feature, final String group, final String title,
                                        final String tick, final String count) {
        return FEATURE04.replace(FEATURE, feature)
            .replace(GROUP, group)
            .replace(TITLE, title)
            .replace(ID, id + "")
            .replace(TICK, tick)
            .replace(COUNT, count);
    }

    private static String getReplaced(final Integer id, final String feature, final String group,
                                      final String tick, final String count) {
        return FEATURE_ANOTHER.replace(FEATURE, feature)
            .replace(GROUP, group)
            .replace(ID, id + "")
            .replace(TICK, tick)
            .replace(COUNT, count);
    }
}
