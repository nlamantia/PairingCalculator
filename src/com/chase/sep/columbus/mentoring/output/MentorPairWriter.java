package com.chase.sep.columbus.mentoring.output;

import com.chase.sep.columbus.mentoring.models.Mentee;
import com.chase.sep.columbus.mentoring.models.Mentor;
import com.chase.sep.columbus.mentoring.models.Pair;
import com.chase.sep.columbus.mentoring.models.Pairable;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Writes a CSV file with all the mentor-mentee pairs in the format Shamoy wants
 */
public class MentorPairWriter {

    private final List<Pair<Mentee, Mentor>> pairs;
    private final PrintWriter writer;

    public MentorPairWriter(PrintWriter writer, List<Pair<Mentee, Mentor>> pairs) {
        this.pairs = pairs;
        this.writer = writer;
    }

    public void writeToCSV() throws IOException {
        // write header row
        writer.print("Mentee SID,Mentee Last Name,Mentee First Name,");
        writer.print("Mentor SID,Mentor Last Name,Mentor First Name\n");

        // write pairs
        for (Pair<Mentee, Mentor> pair : pairs) {
            List<Pairable<?>> pairables = pair.getPairables()
                    .stream()
                    .sorted((o1, o2) -> {
                        if (o1 instanceof Mentee && o2 instanceof Mentor) {
                            return -1;
                        } else if (o1 instanceof Mentor && o2 instanceof Mentee) {
                            return 1;
                        }
                        return 0;
                    })
                    .collect(Collectors.toList());

            for (Pairable<?> pairable : pairables) {
                if (pairable instanceof Mentee) {
                    Mentee mentee = (Mentee) pairable;
                    String menteeStr = String.format(
                            "%s,%s,%s",
                            mentee.getSid().toUpperCase(),
                            mentee.getLastName(),
                            mentee.getFirstName()
                    );
                    writer.print(menteeStr);
                } else if (pairable instanceof Mentor) {
                    Mentor mentee = (Mentor) pairable;
                    String mentorStr = String.format(
                            ",%s,%s,%s",
                            mentee.getSid().toUpperCase(),
                            mentee.getLastName(),
                            mentee.getFirstName()
                    );
                    writer.print(mentorStr);
                }
            }
            writer.print("\n");
        }
    }
}
