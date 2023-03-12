package seedu.address.model.person;

import seedu.address.model.person.Phone;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class Teacher {

    public static final String MESSAGE_CONSTRAINTS =
            "Name of teachers should only contain alphabets";

    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs a {@code Teacher}.
     *
     * @param teacher A teacher.
     */
    public Teacher(String teacher) {
        requireNonNull(teacher);
        checkArgument(isValidTeacher(teacher), MESSAGE_CONSTRAINTS);
        value = teacher;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidTeacher(String test) {
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
