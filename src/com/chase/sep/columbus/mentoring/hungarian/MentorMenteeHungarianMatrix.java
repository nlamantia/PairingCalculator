package com.chase.sep.columbus.mentoring.hungarian;

import com.chase.sep.columbus.mentoring.models.Mentee;
import com.chase.sep.columbus.mentoring.models.Mentor;
import com.chase.sep.columbus.mentoring.models.Pair;
import com.chase.sep.columbus.mentoring.models.SinglePartnerPairable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Builds a preferences hungarian matrix for mentor mentee pairs.
 *
 * @see HungarianMatrix
 */
public class MentorMenteeHungarianMatrix extends HungarianMatrix<Mentee, Mentor> {

    private final String[] mentorNames;
    private final Map<String, Mentor> mentorMap;

    public MentorMenteeHungarianMatrix(List<Mentee> mentees, List<Mentor> mentors, String[] mentorNames) {
        super(mentees, mentors);
        this.mentorNames = mentorNames;
        this.mentorMap = mentors
                .stream()
                .collect(
                        Collectors.toMap(
                                SinglePartnerPairable::name,
                                mentor -> mentor
                        )
                );
    }

    @Override
    protected int[][] buildMatrix(List<Mentee> mentees, List<Mentor> mentors) {
        int numOfMentees = mentees.size();
        int[][] matrix = new int[numOfMentees][numOfMentees];

        for (int i = 0; i < matrix.length; i++) {
            int[] preferenceRow = matrix[i];
            Mentee mentee = mentees.get(i);

            int rowIndex = 0;
            for (String name : mentorNames) {
                Mentor mentor = mentorMap.get(name);
                if (mentor == null) {
                    throw new IllegalStateException("Cannot find mentor with name " + name);
                }

                int score = mentee.getPreferenceScore(mentor);
                for (int k = 0; k < mentor.getMaxPartners(); k++) {
                    preferenceRow[rowIndex] = score;
                    rowIndex++;
                }

            }
        }

        return matrix;
    }

    @Override
    protected List<Pair<Mentee, Mentor>> createPairsFromAssignments(
            List<Mentee> mentees,
            List<Mentor> mentors,
            int[][] assignments
    ) {
        List<Pair<Mentee, Mentor>> pairs = new ArrayList<>();
        for (int[] assignment : assignments) {
            int mentorIndex = assignment[0];
            int menteeIndex = assignment[1];

            int counter = 0;
            Mentor matchedMentor = null;
            for (String mentorName : mentorNames) {
                Mentor mentor = mentorMap.get(mentorName);
                for (int i = 0; i < mentor.getMaxPartners(); i++) {
                    if (counter == mentorIndex) {
                        matchedMentor = mentor;
                        break;
                    } else {
                        counter++;
                    }
                }

                if (matchedMentor != null) {
                    pairs.add(new Pair<>(mentees.get(menteeIndex), Collections.singleton(matchedMentor)));
                    break;
                }
            }
        }
        return pairs;
    }
}
