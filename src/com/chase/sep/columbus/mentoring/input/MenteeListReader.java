package com.chase.sep.columbus.mentoring.input;

import com.chase.sep.columbus.mentoring.models.Mentee;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Parses a list of mentees and their mentor preferences. We are under the assumptions that
 * mentees ALWAYS have preferences of some sort. If there becomes a need for them to not have
 * any preferences, we can make sure that every mentor is labeled with the same number.
 */
public class MenteeListReader extends CSVReader<Mentee> {

    private String[] mentorNames;

    public MenteeListReader(String fileName) {
        super(fileName);
    }

    public String[] getMentorNames() {
        return mentorNames;
    }

    @Override
    protected Mentee parseRow(String[] row, int rowNum) {
        if (rowNum > 0) {
            String[] rowSubset = Arrays.copyOfRange(row, 10, row.length);
            final int preferencesOffset = 2;

            if (rowNum > 1) {
                String name = rowSubset[0];
                String sid = rowSubset[1];
                Mentee mentee = new Mentee(name, sid);

                Map<String, Integer> preferencesMap = new HashMap<>();
                for (int i = preferencesOffset; i < rowSubset.length; i++) {
                    if (!rowSubset[i].isEmpty()) {
                        try {
                            int preference = Integer.parseInt(rowSubset[i]);
                            String menteeName = mentorNames[i - preferencesOffset];
                            preferencesMap.put(menteeName, preference);
                        } catch (Exception e) {
                            if (!(e instanceof NumberFormatException)) {
                                throw e;
                            }
                        }
                    }
                }

                mentee.setTopChoices(preferencesMap);
                return mentee;
            } else {
                mentorNames = new String[rowSubset.length - preferencesOffset];
                System.arraycopy(rowSubset, preferencesOffset, mentorNames, 0, mentorNames.length);
            }
        }
        return null;
    }
}
