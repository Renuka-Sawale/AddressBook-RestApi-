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

    public void UpdateAddressBook(String firstName, String phoneNo) {
        AddressBookPersonData addressBookPersonData = this.getAddressBookPersonData(firstName);
        if (addressBookPersonData != null){
            addressBookPersonData.phoneNo = phoneNo;
        }
    }

    public AddressBookPersonData getAddressBookPersonData(String firstName) {
        return this.addressBookPersonDataList.stream()
                .filter(contactData -> contactData.firstName.equals(firstName))
                .findFirst()
                .orElse(null);
    }

    public boolean deletePersonContact(String firstName) {
        AddressBookPersonData addressBookPersonData = this.getAddressBookPersonData(firstName);
        return addressBookPersonDataList.remove(addressBookPersonData);
    }
}
