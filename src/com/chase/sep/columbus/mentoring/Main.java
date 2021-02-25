package com.chase.sep.columbus.mentoring;

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
        Mentor nick = new Mentor("Nick", 2, preferences);

        preferences = new HashMap<>();
        preferences.put("Madison", 1);
        preferences.put("Connor", 2);
        preferences.put("Anthony", 3);
        Mentor anisha = new Mentor("Anisha", 1, preferences);

        preferences = new HashMap<>();
        preferences.put("Brittany", 1);
        preferences.put("Madison", 2);
        preferences.put("Anthony", 3);
        Mentor ricardo = new Mentor("Ricardo", 1, preferences);

        preferences = new HashMap<>();
        preferences.put("Reece", 1);
        preferences.put("Madison", 2);
        preferences.put("Connor", 3);
        Mentor jacob = new Mentor("Jacob", 2, preferences);

        preferences = new HashMap<>();
        preferences.put("Megan", 1);
        preferences.put("Anthony", 2);
        preferences.put("Madison", 3);
        Mentor rebecca = new Mentor("Rebecca", 1, preferences);

        preferences = new HashMap<>();
        preferences.put("Megan", 1);
        preferences.put("Reece", 2);
        preferences.put("Chris", 3);
        Mentor bri = new Mentor("Brianna", 2, preferences);

        List<Mentor> mentors = Arrays.asList(nick, jacob, rebecca, anisha, bri, ricardo);

        // mentees
        preferences = new HashMap<>();
        preferences.put("Anisha", 1);
        preferences.put("Rebecca", 2);
        preferences.put("Jacob", 3);
        Mentee madison = new Mentee("Madison", preferences);

        preferences = new HashMap<>();
        preferences.put("Brianna", 1);
        preferences.put("Nick", 2);
        preferences.put("Ricardo", 3);
        Mentee reece = new Mentee("Reece", preferences);

        preferences = new HashMap<>();
        preferences.put("Ricardo", 1);
        preferences.put("Brianna", 2);
        preferences.put("Rebecca", 3);
        Mentee mikeM = new Mentee("Mike M", preferences);

        preferences = new HashMap<>();
        preferences.put("Anisha", 2);
        preferences.put("Brianna", 1);
        preferences.put("Rebecca", 3);
        Mentee mikeY = new Mentee("Mike Y", preferences);

        preferences = new HashMap<>();
        preferences.put("Anisha", 2);
        preferences.put("Brianna", 1);
        preferences.put("Jacob", 3);
        Mentee megan = new Mentee("Megan", preferences);

        preferences = new HashMap<>();
        preferences.put("Anisha", 1);
        preferences.put("Nick", 2);
        preferences.put("Ricardo", 3);
        Mentee connor = new Mentee("Connor", preferences);

        preferences = new HashMap<>();
        preferences.put("Anisha", 2);
        preferences.put("Nick", 3);
        preferences.put("Jacob", 1);
        Mentee chris = new Mentee("Chris", preferences);

        preferences = new HashMap<>();
        preferences.put("Anisha", 1);
        preferences.put("Brianna", 2);
        preferences.put("Rebecca", 3);
        Mentee brittany = new Mentee("Brittany", preferences);

        preferences = new HashMap<>();
        preferences.put("Anisha", 2);
        preferences.put("Rebecca", 3);
        preferences.put("Ricardo", 1);
        Mentee anthony = new Mentee("Anthony", preferences);

        List<Mentee> mentees = Arrays.asList(anthony, reece, megan, brittany, madison, connor, chris, mikeM, mikeY);

        // print combinations
        List<Pair<Mentee, Mentor>> pairs = PairingUtil.pair(mentees, mentors);
        printPairs(pairs, "Mentee-Mentor PAIRS");

        List<Pair<Mentor, Mentee>> pairsFlipped = PairingUtil.pair(mentors, mentees);
        printPairs(pairsFlipped, "Mentor-Mentee PAIRS");

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
