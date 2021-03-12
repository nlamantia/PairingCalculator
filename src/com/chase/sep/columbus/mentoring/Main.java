package com.chase.sep.columbus.mentoring;

import com.chase.sep.columbus.mentoring.input.MenteeListReader;
import com.chase.sep.columbus.mentoring.input.MentorListReader;
import com.chase.sep.columbus.mentoring.input.MentorPreferenceListReader;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Mentor list: ");
        String mentorFileName = scanner.nextLine();

        MentorListReader mentorListReader = new MentorPreferenceListReader(mentorFileName);
        List<Mentor> mentors = mentorListReader.getData();
        System.out.println(mentors.toString());

        System.out.print("Mentee list: ");
        String menteeFileName = scanner.nextLine();

        MenteeListReader menteeListReader = new MenteeListReader(menteeFileName);
        List<Mentee> mentees = menteeListReader.getData();

        // print combinations
        List<Pair<Mentee, Mentor>> pairs = PairingUtil.pair(mentees, mentors);
        printPairs(pairs, "Mentee-Mentor PAIRS");

        try {
            // set up print writer
            String fileName = "/Users/d590964/pairings.csv";
            FileWriter fileWriter = new FileWriter(fileName);
            PrintWriter printWriter = new PrintWriter(fileWriter);

            // write the pairs to a CSV file
            MentorPairWriter writer = new MentorPairWriter(printWriter, pairs);
            writer.writeToCSV();

            // close the print writer
            printWriter.close();

            System.out.println("Output written successfully");
        } catch (IOException exception) {
            System.err.println(exception.getMessage());
        }
    }

    private static <S extends Pairable<T>, T extends Pairable<S>> void printPairs(List<Pair<S, T>> pairs, String title) {
        System.out.println(title);
        System.out.println("==================================");
        pairs.forEach(pair -> {
            System.out.println("(" + pair.toString() + ")");
        });
        System.out.println("");
    }
}
