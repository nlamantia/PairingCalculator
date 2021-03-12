package com.chase.sep.columbus.mentoring.input;

import com.chase.sep.columbus.mentoring.Mentor;

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
