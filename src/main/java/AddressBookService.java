import java.util.ArrayList;
import java.util.List;

public class AddressBookService {

    private List<AddressBookPersonData> addressBookPersonDataList;

    public AddressBookService(List<AddressBookPersonData> addressBookPersonDataList) {
        this.addressBookPersonDataList = new ArrayList<>(addressBookPersonDataList);
    }

    public long countEntries() {
        return addressBookPersonDataList.size();
    }

    public void addAddressBook(AddressBookPersonData addressBookpersonData) {
        this.addressBookPersonDataList.add(addressBookpersonData);
    }
}
