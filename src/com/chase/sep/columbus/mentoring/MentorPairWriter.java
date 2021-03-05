package com.chase.sep.columbus.mentoring;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

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

            // TODO: clean up
            for (Pairable<?> pairable : pairables) {
                if (pairable instanceof Mentee) {
                    Mentee mentee = (Mentee) pairable;
                    String menteeStr = String.format(
                            "%s,%s,%s",
                            mentee.getSid(),
                            mentee.getLastName(),
                            mentee.getFirstName()
                    );
                    writer.print(menteeStr);
                } else if (pairable instanceof Mentor) {
                    Mentor mentee = (Mentor) pairable;
                    String mentorStr = String.format(
                            ",%s,%s,%s",
                            mentee.getSid(),
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
