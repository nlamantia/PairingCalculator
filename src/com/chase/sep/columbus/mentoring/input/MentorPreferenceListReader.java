package com.chase.sep.columbus.mentoring.input;

import com.chase.sep.columbus.mentoring.Mentor;
import com.chase.sep.columbus.mentoring.input.MentorListReader;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
