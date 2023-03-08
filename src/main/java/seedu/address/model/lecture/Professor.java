package seedu.address.model.lecture;

import seedu.address.model.person.Phone;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Professor {

    public static final String MESSAGE_CONSTRAINTS =
            "Phone numbers should only contain numbers, and it should be at least 3 digits long";
    public static final String VALIDATION_REGEX = "\\d{3,}";
    public final String value;

    /**
     * Constructs a {@code Phone}.
     *
     * @param professor A valid phone number.
     */
    public Professor(String professor) {
        requireNonNull(professor);
        checkArgument(isValidProfessor(professor), MESSAGE_CONSTRAINTS);
        value = professor;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidProfessor(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Phone // instanceof handles nulls
                && value.equals(((Phone) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
