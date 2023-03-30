package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class VenueTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Venue(null));
    }

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        String invalidAddress = "";
        assertThrows(IllegalArgumentException.class, () -> new Venue(invalidAddress));
    }

    @Test
    public void isValidVenue() {
        // null address
        assertThrows(NullPointerException.class, () -> Venue.isValidVenue(null));

        // invalid addresses
        assertFalse(Venue.isValidVenue("")); // empty string
        assertFalse(Venue.isValidVenue(" ")); // spaces only

        // valid addresses
        assertTrue(Venue.isValidVenue("Blk 456, Den Road, #01-355"));
        assertTrue(Venue.isValidVenue("-")); // one character
        assertTrue(Venue.isValidVenue("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }
}
