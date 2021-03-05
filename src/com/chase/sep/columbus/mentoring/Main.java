package com.chase.sep.columbus.mentoring;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        // mentors
        Map<String, Integer> preferences = new HashMap<>();
        preferences.put("Anthony", 1);
        preferences.put("Chris", 2);
        preferences.put("Reece", 3);
        Mentor nick = new Mentor("D590964", "Nick LaMantia", 2, preferences);

        preferences = new HashMap<>();
        preferences.put("Madison", 1);
        preferences.put("Connor", 2);
        preferences.put("Anthony", 3);
        Mentor anisha = new Mentor("O718629", "Anisha Rohatgi", 1, preferences);

        preferences = new HashMap<>();
        preferences.put("Brittany", 1);
        preferences.put("Madison", 2);
        preferences.put("Anthony", 3);
        Mentor ricardo = new Mentor("V761556", "Ricardo Carrillo", 1, preferences);

        preferences = new HashMap<>();
        preferences.put("Reece", 1);
        preferences.put("Madison", 2);
        preferences.put("Connor", 3);
        Mentor jacob = new Mentor("W571634", "Jacob Handley", 2, preferences);

        preferences = new HashMap<>();
        preferences.put("Megan", 1);
        preferences.put("Anthony", 2);
        preferences.put("Madison", 3);
        Mentor rebecca = new Mentor("V750738", "Rebecca Hu", 1, preferences);

        preferences = new HashMap<>();
        preferences.put("Megan", 1);
        preferences.put("Reece", 2);
        preferences.put("Chris", 3);
        Mentor bri = new Mentor("O718532", "Brianna Taffe", 2, preferences);

        List<Mentor> mentors = Arrays.asList(nick, jacob, rebecca, anisha, bri, ricardo);

        // mentees
        preferences = new HashMap<>();
        preferences.put("Anisha", 1);
        preferences.put("Rebecca", 2);
        preferences.put("Jacob", 3);
        Mentee madison = new Mentee("E876769", "Madison Stiefel", preferences);

        preferences = new HashMap<>();
        preferences.put("Brianna", 1);
        preferences.put("Nick", 2);
        preferences.put("Ricardo", 3);
        Mentee reece = new Mentee("N729914", "Reece Partridge", preferences);

        preferences = new HashMap<>();
        preferences.put("Ricardo", 1);
        preferences.put("Brianna", 2);
        preferences.put("Rebecca", 3);
        Mentee mikeM = new Mentee("E860256", "Mike Morrill", preferences);

        preferences = new HashMap<>();
        preferences.put("Anisha", 2);
        preferences.put("Brianna", 1);
        preferences.put("Rebecca", 3);
        Mentee mikeY = new Mentee("E891949", "Mike Yandam", preferences);

        preferences = new HashMap<>();
        preferences.put("Anisha", 2);
        preferences.put("Brianna", 1);
        preferences.put("Jacob", 3);
        Mentee megan = new Mentee("O748035", "Megan Vallo", preferences);

        preferences = new HashMap<>();
        preferences.put("Anisha", 1);
        preferences.put("Nick", 2);
        preferences.put("Ricardo", 3);
        Mentee connor = new Mentee("R677687", "Connor Oppy", preferences);

        preferences = new HashMap<>();
        preferences.put("Anisha", 2);
        preferences.put("Nick", 3);
        preferences.put("Jacob", 1);
        Mentee chris = new Mentee("F697741", "Chris Puello", preferences);

        preferences = new HashMap<>();
        preferences.put("Anisha", 1);
        preferences.put("Brianna", 2);
        preferences.put("Rebecca", 3);
        Mentee brittany = new Mentee("V765111", "Brittany Redmond", preferences);

        preferences = new HashMap<>();
        preferences.put("Anisha", 2);
        preferences.put("Rebecca", 3);
        preferences.put("Ricardo", 1);
        Mentee anthony = new Mentee("V780138", "Anthony Fortuna", preferences);

        List<Mentee> mentees = Arrays.asList(anthony, reece, megan, brittany, madison, connor, chris, mikeM, mikeY);

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
