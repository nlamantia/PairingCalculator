package com.chase.sep.columbus.mentoring.input;

import com.chase.sep.columbus.mentoring.models.Mentor;

/**
 * Mentor list CSV reader class for when mentors DO NOT have preferences.
 *
 * This class would be used if we were using the profile slide approach (e.g. 2020 ETP pairings)
 */
public class MentorListReader extends CSVReader<Mentor> {

    public MentorListReader(String fileName) {
        super(fileName);
    }

    @Override
    protected Mentor parseRow(String[] row, int index) {
        if (index > 0) {
            final String sid = row[1];
            final String name = row[0];

            int maxMentees;
            try {
                maxMentees = Integer.parseInt(row[2]);
            } catch (NumberFormatException e) {
                System.err.println(e.getLocalizedMessage());
                maxMentees = 1;
            }

            return new Mentor(sid, name, maxMentees);
        }
        return null;
    }
}
