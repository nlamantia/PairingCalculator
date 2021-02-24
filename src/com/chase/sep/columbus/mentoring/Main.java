package com.chase.sep.columbus.mentoring;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Map<String, Integer> preferences = new HashMap<>();
        preferences.put("Reece", 1);
        preferences.put("Connor", 2);
        Mentor nick = new Mentor("Nick", 2, preferences);

        preferences = new HashMap<>();
        preferences.put("Madison", 1);
        preferences.put("Connor", 2);
        Mentor anisha = new Mentor("Anisha", 1, preferences);

        List<Mentor> mentors = Arrays.asList(nick, anisha);

        preferences = new HashMap<>();
        preferences.put("Anisha", 1);
        preferences.put("Nick", 2);
        Mentee madison = new Mentee("Madison", preferences);

        preferences = new HashMap<>();
        preferences.put("Nick", 1);
        preferences.put("Anisha", 2);
        Mentee reece = new Mentee("Reece", preferences);

        preferences = new HashMap<>();
        preferences.put("Anisha", 1);
        preferences.put("Nick", 2);
        Mentee connor = new Mentee("Connor", preferences);

        List<Mentee> mentees = Arrays.asList(madison, reece, connor);

        List<Pair<Mentor, Mentee>> pairs = PairingUtil.pair(mentors, mentees);

        System.out.println("PAIRS");
        System.out.println("==================================");
        pairs.forEach(pair -> {
            System.out.println(pair.toString());
        });

    }
}
