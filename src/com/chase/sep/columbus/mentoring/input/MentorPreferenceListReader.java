package com.chase.sep.columbus.mentoring.input;

import com.chase.sep.columbus.mentoring.models.Mentor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Mentor list CSV reader that takes into account mentor preferences.
 *
 * This class would be used in cases where we use the speed dating event approach (e.g. 2021 early starts).
 * Ideally, we get into the habit of doing speed dating events all the time for pairing, but in time crunches,
 * it might be necessary to do one-way preferences. That is why there are two different classes for these
 * two different scenarios.
 *
 * @see MentorListReader
 */
public class MentorPreferenceListReader extends MentorListReader {

    private String[] menteeNames;

    public MentorPreferenceListReader(String fileName) {
        super(fileName);
    }

    @Override
    protected Mentor parseRow(String[] row, int rowNum) {
        if (rowNum > 0) {
            String[] rowSubset = Arrays.copyOfRange(row, 10, row.length);
            if (rowNum > 1) {
                Mentor mentor = super.parseRow(rowSubset, rowNum);

                Map<String, Integer> preferencesMap = new HashMap<>();
                for (int i = 3; i < rowSubset.length; i++) {
                    if (!rowSubset[i].isEmpty()) {
                        try {
                            int preference = Integer.parseInt(rowSubset[i]);
                            String menteeName = menteeNames[i - 3];
                            preferencesMap.put(menteeName, preference);
                        } catch (Exception e) {
                            if (!(e instanceof NumberFormatException)) {
                                throw e;
                            }
                        }
                    }
                }

                mentor.setTopChoices(preferencesMap);
                return mentor;
            } else {
                menteeNames = new String[rowSubset.length - 3];
                System.arraycopy(rowSubset, 3, menteeNames, 0, menteeNames.length);
            }
        }
        return null;
    }
}
