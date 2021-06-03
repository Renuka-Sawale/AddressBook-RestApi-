import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class AddressBookServiceTest {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 3000;
    }

    public AddressBookPersonData[] getAddressBookDataList() {
        Response response = RestAssured.get("/addressBook");
        System.out.println("Address Book contact entries from JSONServer:\n" + response.asString());
        AddressBookPersonData[] arrayOfContacts = new Gson().fromJson(response.asString(), AddressBookPersonData[].class);
        return arrayOfContacts;
    }

    @Test
    public void givenAddressBookContactInJsonServer_WhenRetrieved_ShouldMatchTheResult() {
        AddressBookPersonData[] arrayOfContacts = getAddressBookDataList();
        AddressBookService addressBookService;
        addressBookService = new AddressBookService(Arrays.asList(arrayOfContacts));
        long entries = addressBookService.countEntries();
        Assertions.assertEquals(2, entries);
    }
}
