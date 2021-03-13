package com.chase.sep.columbus.mentoring;

import com.chase.sep.columbus.mentoring.hungarian.PreferencesMatrix;
import com.chase.sep.columbus.mentoring.input.MenteeListReader;
import com.chase.sep.columbus.mentoring.input.MentorListReader;
import com.chase.sep.columbus.mentoring.input.MentorPreferenceListReader;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Mentor list: ");
        String mentorFileName = scanner.nextLine();

        System.out.print("Mentee list: ");
        String menteeFileName = scanner.nextLine();

        PreferencesMatrix matrix = new PreferencesMatrix(mentorPreferenceMatrix(mentorFileName, menteeFileName));
        int[][] assignments = matrix.getOptimalAssignments();

        System.out.println("Optimal assignments:");
        for (int[] assignment : assignments) {
            System.out.println(String.format("(%d, %d)", assignment[0], assignment[1]));
        }

//        // print combinations
//        List<Pair<Mentee, Mentor>> pairs = PairingUtil.pair(mentees, mentors);
//        printPairs(pairs, "Mentee-Mentor PAIRS");
//
//        try {
//            // set up print writer
//            String fileName = "/Users/d590964/pairings.csv";
//            FileWriter fileWriter = new FileWriter(fileName);
//            PrintWriter printWriter = new PrintWriter(fileWriter);
//
//            // write the pairs to a CSV file
//            MentorPairWriter writer = new MentorPairWriter(printWriter, pairs);
//            writer.writeToCSV();
//
//            // close the print writer
//            printWriter.close();
//
//            System.out.println("Output written successfully");
//        } catch (IOException exception) {
//            System.err.println(exception.getMessage());
//        }
    }

    private static <S extends Pairable<T>, T extends Pairable<S>> void printPairs(List<Pair<S, T>> pairs, String title) {
        System.out.println(title);
        System.out.println("==================================");
        pairs.forEach(pair -> {
            System.out.println("(" + pair.toString() + ")");
        });
        System.out.println("");
    }

    private static int[][] mentorPreferenceMatrix(String mentorFileName, String menteeFileName) {
        MentorListReader mentorListReader = new MentorListReader(mentorFileName);
        List<Mentor> mentorList = mentorListReader.getData();

        Map<String, Mentor> mentorMap = mentorList
                .stream()
                .collect(
                        Collectors.toMap(
                                SinglePartnerPairable::name,
                                mentor -> mentor
                        )
                );

        MenteeListReader menteeListReader = new MenteeListReader(menteeFileName);
        List<Mentee> mentees = menteeListReader.getData();
        System.out.println(mentees.toString() + "\n\n");

        String[] mentorNames = menteeListReader.getMentorNames();
        if (mentorNames == null) {
            throw new IllegalStateException("No mentor names found");
        }

        System.out.println(String.format("[%s]", String.join(", ", mentorNames)));

        int numOfMentees = mentees.size();
        int[][] matrix = new int[numOfMentees][numOfMentees];

        for (int i = 0; i < matrix.length; i++) {
            int[] preferenceRow = matrix[i];
            Mentee mentee = mentees.get(i);

            int rowIndex = 0;
            for (int j = 0; j < mentorNames.length; j++) {
                Mentor mentor = mentorMap.get(mentorNames[j]);
                if (mentor == null) {
                    throw new IllegalStateException("Cannot find mentor with name " + mentorNames[j]);
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
}
