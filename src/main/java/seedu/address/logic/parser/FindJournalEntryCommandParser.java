package seedu.address.logic.parser;

import seedu.address.logic.commands.FindContactCommand;
import seedu.address.logic.commands.FindJournalEntryCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.journal.Date;
import seedu.address.model.journal.Description;
import seedu.address.model.journal.Entry;
import seedu.address.model.tag.Tag;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;

public class FindJournalEntryCommandParser implements Parser<FindJournalEntryCommand> {
    public FindJournalEntryCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args,
                PREFIX_NAME,
                PREFIX_ADDRESS,
                PREFIX_PHONE,
                PREFIX_EMAIL,
                PREFIX_TAG,
                PREFIX_DATE_AND_TIME,
                PREFIX_DESCRIPTION,
                PREFIX_CONTACT
        );

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            FindContactCommand.MESSAGE_USAGE)
            );
        }
        Predicate<Entry> entryPredicate = entry -> true;
        if (!arePrefixesEmpty(argMultimap, PREFIX_ADDRESS, PREFIX_EMAIL, PREFIX_PHONE)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindJournalEntryCommand.MESSAGE_USAGE));
        }
        if (arePrefixesPresent(argMultimap, PREFIX_NAME)) {
            assert argMultimap.getValue(PREFIX_NAME).isPresent();
            String titleKeyWord =
                    argMultimap.getValue(PREFIX_NAME).get().trim().toLowerCase();
            entryPredicate =
                    entryPredicate.and(entry -> entry.getTitle().title
                            .toLowerCase()
                            .contains(titleKeyWord));
        }
        if (arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION)) {
            assert argMultimap.getValue(PREFIX_DESCRIPTION).isPresent();
            String descriptionKeyWord =
                    argMultimap.getValue(PREFIX_DESCRIPTION).get().trim().toLowerCase();
            entryPredicate =
                    entryPredicate.and(entry -> {
                        if (entry.getDescription().equals(Description.EMPTY_DESCRIPTION)) {
                            return false;
                        } else {
                            return entry.getDescription().description
                                    .toLowerCase()
                                    .contains(descriptionKeyWord);
                        }
                    });
        }
        if (arePrefixesPresent(argMultimap, PREFIX_DATE_AND_TIME)) {
            assert argMultimap.getValue(PREFIX_DATE_AND_TIME).isPresent();
            Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE_AND_TIME).orElse(null));
            entryPredicate = entryPredicate.and(entry -> entry.getDate().equals(date));
        }
        List<String> names = argMultimap.getAllValues(PREFIX_CONTACT);
        entryPredicate = entryPredicate.and(
                entry -> names.stream().allMatch(
                        name -> entry
                                .getContactList().stream()
                                .anyMatch(
                                        person -> person.getName().fullName
                                                .toLowerCase()
                                                .contains(name.toLowerCase()))));
        Set<Tag> tagList1 = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        entryPredicate = entryPredicate.and(entry -> entry.getTags().containsAll(tagList1));
        return new FindJournalEntryCommand(entryPredicate);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if none of the prefixes contains present {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesEmpty(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isEmpty());
    }
}
