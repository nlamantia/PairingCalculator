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
//        Scanner scanner = new Scanner(System.in);
//
//        System.out.print("Mentor list: ");
//        String mentorFileName = scanner.nextLine();
//
//        System.out.print("Mentee list: ");
//        String menteeFileName = scanner.nextLine();

        PreferencesMatrix matrix = new PreferencesMatrix(new int[][]{
                new int[]{1, 1, 2, 3, 4, 4},
                new int[]{3, 3, 1, 2, 4, 4},
                new int[]{2, 2, 1, 3, 4, 4},
                new int[]{3, 3, 2, 4, 1, 1},
                new int[]{2, 2, 1, 4, 3, 3},
                new int[]{2, 2, 4, 1, 3, 3}
        });
        System.out.println("Raw matrix:");
        System.out.println(matrix.toString());

        matrix.optimize();
        System.out.println("Optimized matrix:");
        System.out.println(matrix.toString());

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

        for (String name : mentorNames) {
            System.out.println(name);
        }

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
