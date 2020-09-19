package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;
import seedu.address.testutil.PersonBuilder;

class RemarkCommandTest {

    private Model model;
    private Model expectedModel;
    private final Remark newRemark = new Remark("This is a new test remark");

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_addRemarkUnfilteredList_success() {
        Person personToAddRemark = model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased());
        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_PERSON, newRemark);

        String expectedMessage = String.format(
                RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, personToAddRemark);

        Person personToUpdate = new PersonBuilder(personToAddRemark)
                .withRemark(newRemark.value).build();
        expectedModel.setPerson(personToAddRemark, personToUpdate);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptyRemarkUnfilteredList_success() {
        Person personToAddRemark = model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased());
        Remark newRemark = new Remark("");
        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_PERSON, newRemark);

        String expectedMessage = String.format(
                RemarkCommand.MESSAGE_DELETE_REMARK_SUCCESS, personToAddRemark);

        Person personToUpdate = new PersonBuilder(personToAddRemark).withRemark("").build();
        expectedModel.setPerson(personToAddRemark, personToUpdate);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToAddRemark = model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased());
        Remark newRemark = new Remark("");
        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_PERSON, newRemark);

        String expectedMessage = String.format(
                RemarkCommand.MESSAGE_DELETE_REMARK_SUCCESS, personToAddRemark);

        Person personToUpdate = new PersonBuilder(personToAddRemark).withRemark("").build();
        expectedModel.setPerson(personToAddRemark, personToUpdate);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(
                model.getFilteredPersonList().size() + 1);
        RemarkCommand remarkCommand = new RemarkCommand(outOfBoundIndex, newRemark);

        assertCommandFailure(remarkCommand, model,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        RemarkCommand remarkCommand = new RemarkCommand(outOfBoundIndex, newRemark);

        assertCommandFailure(remarkCommand, model,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    void equals() {
        RemarkCommand remarkFirstCommand = new RemarkCommand(
                INDEX_FIRST_PERSON, newRemark);
        RemarkCommand remarkSecondCommand = new RemarkCommand(
                INDEX_SECOND_PERSON, new Remark(""));

        // same object -> returns true
        assertTrue(remarkFirstCommand.equals(remarkFirstCommand));

        // same values -> returns true
        RemarkCommand remarkFirstCommandCopy = new RemarkCommand(
                INDEX_FIRST_PERSON, new Remark(newRemark.value));
        assertTrue(remarkFirstCommand.equals(remarkFirstCommandCopy));

        // different types -> returns false
        assertFalse(remarkFirstCommand.equals(1));

        // null -> returns false
        assertFalse(remarkFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(remarkFirstCommand.equals(remarkSecondCommand));
    }
}
