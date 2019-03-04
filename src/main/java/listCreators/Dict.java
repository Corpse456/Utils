package listCreators;

import java.util.Arrays;
import java.util.List;

public class Dict {

    public static final String template =
        "            <update tableName=\"location\">\n" +
        "                <column name=\"letter_code\" value=\"VALUE_CODE\" type=\"varchar(10)\"/>\n" +
        "                <where>name = 'NAME_CODE'</where>\n" +
        "            </update>\n";


    public static final List<String> LOCATIONS = Arrays.asList(
        "Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla",
        "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahrain",
        "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia",
        "Bosnia and Herzegovina", "Botswana", "Brazil", "British Virgin Islands", "Brunei", "Bulgaria",
        "Burkina Faso", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands",
        "Central African Republic", "Chad", "Chile", "China", "Christmas Island", "Cocos(Keeling) Islands",
        "Colombia", "Comoros", "Congo", "Cook Islands", "Costa Rica", "Cote d\"Ivoire", "Croatia", "Cuba", "Cyprus",
        "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "Ecuador", "Egypt",
        "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Falkland Islands", "Faroe Islands",
        "Fiji", "Finland", "France", "French Polynesia", "Gabon", "Georgia", "Germany", "Ghana", "Gibraltar",
        "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guernsey", "Guinea",
        "Guinea - Bissau", "Guyana", "Haiti", "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia",
        "Iran", "Iraq", "Ireland", "Isle of Man", "Israel", "Italy", "Jamaica", "Japan", "Jersey", "Jordan",
        "Kazakhstan", "Kenya", "Kiribati", "Kosovo", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho",
        "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Macau", "Macedonia", "Madagascar",
        "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Martinique", "Mauritania",
        "Mauritius", "Mayotte", "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro", "Montserrat",
        "Morocco", "Mozambique", "Myanmar", "Nagorno - Karabakh", "Namibia", "Nauru", "Nepal", "Netherlands",
        "Netherlands Antilles", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue",
        "Norfolk Island", "North Korea", "Northern Mariana", "Norway", "Oman", "Pakistan", "Palau", "Palestine",
        "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn Islands", "Poland", "Portugal",
        "Puerto Rico", "Qatar", "Romania", "Russia", "Rwanda", "Saint Barthelemy", "Saint Helena",
        "Saint Kitts and Nevis", "Saint Lucia", "Saint Martin", "Saint Pierre and Miquelon",
        "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia",
        "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands",
        "Somalia", "Somaliland", "South Africa", "South Korea", "South Ossetia", "Spain", "Sri Lanka", "Sudan",
        "Suriname", "Svalbard", "Swaziland", "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania",
        "Thailand", "The Bahamas", "The Gambia", "Timor - Leste", "Togo", "Tokelau", "Tonga",
        "Transnistria Pridnestrovie", "Trinidad and Tobago", "Tristan da Cunha", "Tunisia", "Turkey",
        "Turkish Republic of Northern Cyprus", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Uganda",
        "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "Uruguay", "US Virgin Islands",
        "Uzbekistan", "Vanuatu", "Vatican City", "Venezuela", "Vietnam", "Wallis and Futuna", "Western Sahara", "Yemen",
        "Zambia", "Zimbabwe");
}
