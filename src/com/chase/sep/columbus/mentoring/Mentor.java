package com.chase.sep.columbus.mentoring;

import java.util.Map;

public class Mentor extends MultiPartnerPairable<Mentee> {

    private final String sid;

    public Mentor(String sid, String name, int maxPartners) {
        super(name, maxPartners);
        this.sid = sid;
    }

    public Mentor(String sid, String name, int maxPartners, Map<String, Integer> topChoices) {
        super(name, maxPartners, topChoices);
        this.sid = sid;
    }

    public String getSid() {
        return sid;
    }

    public String getFirstName() {
        String[] nameParts = this.name().split(" ");
        if (nameParts.length > 0) {
            String firstName = nameParts[0];
            for (int i = 1; i < nameParts.length - 1; i++) {
                firstName = firstName + " " + nameParts[i];
            }
            return firstName;
        }
        return "";
    }

    public String getLastName() {
        String[] nameParts = this.name().split(" ");
        if (nameParts.length > 0) {
            return nameParts[nameParts.length - 1];
        }
        return "";
    }

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName();
    }
}
