package listCreators.avtoMarkets;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Comparator;

@Data
@AllArgsConstructor
class Company implements Comparable<Company> {

    private String name;

    private String address;

    @Override
    public int compareTo(final Company c) {
        return Comparator.comparing(Company::getName).thenComparing(Company::getAddress).compare(this, c);
    }

    @Override
    public String toString() {
        return address + "\t Минск\t \t \t" + name;
    }
}
