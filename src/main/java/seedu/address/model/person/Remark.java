package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's remarks in the address book.
 * Guarantees: immutable; is always valid
 */
public class Remark {


    public static final String MESSAGE_CONSTRAINTS =
            "Remarks should only contain alphanumeric characters and spaces.";
    public static final String VALIDATION_REGEX = "[a-z0-9]*";

    public final String value;

    /**
     * Constructs a {@code Remark}.
     *
     * @param value A valid remark.
     */
    public Remark(String value) {
        requireNonNull(value);
        this.value = value;
    }

    /**
     * Returns true if a given string is a valid remark.
     */
    public static boolean isValidRemark(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark) // instanceof handles nulls
                && value.equals(((Remark) other).value); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
