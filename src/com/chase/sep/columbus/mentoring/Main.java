package com.chase.sep.columbus.mentoring;

import com.chase.sep.columbus.mentoring.hungarian.MentorMenteeHungarianMatrix;
import com.chase.sep.columbus.mentoring.input.MenteeListReader;
import com.chase.sep.columbus.mentoring.input.MentorListReader;
import com.chase.sep.columbus.mentoring.input.MentorPreferenceListReader;
import com.chase.sep.columbus.mentoring.models.Mentee;
import com.chase.sep.columbus.mentoring.models.Mentor;
import com.chase.sep.columbus.mentoring.models.Pair;
import com.chase.sep.columbus.mentoring.models.Pairable;
import com.chase.sep.columbus.mentoring.output.MentorPairWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Mentee list: ");
        String menteeFileName = scanner.nextLine();
        MenteeListReader menteeListReader = new MenteeListReader(menteeFileName);

        System.out.print("Mentor list: ");
        String mentorFileName = scanner.nextLine();

        System.out.print("Including mentor preferences? (y/n) ");
        boolean includeMentorPreferences = scanner.next().toLowerCase().charAt(0) == 'y';
        scanner.nextLine(); // consume rest of line

        System.out.print("Output file: ");
        String fileName = scanner.nextLine();
        System.out.println();

        MentorListReader mentorListReader = includeMentorPreferences
                ? new MentorPreferenceListReader(mentorFileName)
                : new MentorListReader(mentorFileName);

        List<Mentor> mentors = mentorListReader.getData();
        List<Mentee> mentees = menteeListReader.getData();
        List<Pair<Mentee, Mentor>> pairs;

        if (includeMentorPreferences) {
            pairs = PairingUtil.stableMarriage(mentees, mentors);
        } else {
            String[] mentorNames = menteeListReader.getMentorNames();
            if (mentorNames == null) {
                throw new IllegalStateException("No mentor names found");
            }

            pairs = PairingUtil.hungarian(
                    mentees,
                    mentors,
                    (menteeList, mentorList) -> new MentorMenteeHungarianMatrix(menteeList, mentorList, mentorNames)
            );
        }

        // print output to console
        printPairs(pairs);
        printTopChoiceStats(pairs);

        // write output to file
        try {
            // set up print writer
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

    private static void printTopChoiceStats(List<Pair<Mentee, Mentor>> pairs) {
        final int maxNumberOfTopChoices = 5;
        int gotFirstChoice = 0, gotSecondChoice = 0, gotThirdChoice = 0, gotOtherChoice = 0;
        for (Pair<Mentee, Mentor> pair : pairs) {
            List<Pairable<?>> pairables = pair.getPairables();
            Mentee mentee = (Mentee) pairables.get(0);

            if (mentee.getTopChoices().size() <= maxNumberOfTopChoices) {
                Mentor mentor = (Mentor) pairables.get(1);

                switch (mentee.getPreferenceScore(mentor)) {
                    case 1:
                        gotFirstChoice++;
                        break;
                    case 2:
                        gotSecondChoice++;
                        break;
                    case 3:
                        gotThirdChoice++;
                        break;
                    default:
                        gotOtherChoice++;
                }
            }
        }

        System.out.println("Preference Stats");
        System.out.println("==================================");
        System.out.println("First choice: " + ((float) gotFirstChoice / pairs.size() * 100) + "%");
        System.out.println("Second choice: " + ((float) gotSecondChoice / pairs.size() * 100) + "%");
        System.out.println("Third choice: " + ((float) gotThirdChoice / pairs.size() * 100) + "%");
        System.out.println("Other: " + ((float) gotOtherChoice / pairs.size() * 100) + "%\n");
    }

    private static <S extends Pairable<T>, T extends Pairable<S>> void printPairs(List<Pair<S, T>> pairs) {
        System.out.println("Mentee-Mentor PAIRS");
        System.out.println("==================================");
        pairs.forEach(pair -> {
            System.out.println("(" + pair.toString() + ")");
        });
        System.out.println("");
    }
}
