import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
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

    public Response addContactToJsonServer(AddressBookPersonData addressBookPersonData) {
        String contactJson = new Gson().toJson(addressBookPersonData);
        RequestSpecification request = RestAssured.given();
        request.header("Content-Type","application/json");
        request.body(contactJson);
        return request.post("/addressBook");
    }

    @Test
    public void givenNewContacts_WhenAdded_ShouldMatch201ResponseAndCount() {
        AddressBookService addressBookService;
        AddressBookPersonData[] arrayOfContacts = getAddressBookDataList();
        addressBookService = new AddressBookService(Arrays.asList(arrayOfContacts));

        AddressBookPersonData[] arrayofaddressBook = {
                new AddressBookPersonData(0, "Piyush", "Sharma", "Marunji", "Pune", "Maharastra", "5678", "8978563423", "Piyush11@gmail.com"),
                new AddressBookPersonData(0, "Pragati", "Shinde", "Rskp", "Panji", "Goa", "5789", "8978908786", "Pragti14@gmail.com"),
                new AddressBookPersonData(0, "Pratish", "Diana", "Rskpl", "Latur", "Maharastra", "5989", "8978934213", "Pratish19@gmail.com"),
        };
        for (AddressBookPersonData addressBookpersonData : arrayofaddressBook) {
            Response response = addContactToJsonServer(addressBookpersonData);
            int statusCode = response.getStatusCode();
            Assertions.assertEquals(201, statusCode);

            addressBookpersonData = new Gson().fromJson(response.asString(), AddressBookPersonData.class);
            addressBookService.addAddressBook(addressBookpersonData);
        }
        long entries = addressBookService.countEntries();
        Assertions.assertEquals(5, entries);
    }
}
