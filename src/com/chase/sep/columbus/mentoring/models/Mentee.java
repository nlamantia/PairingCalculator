package com.chase.sep.columbus.mentoring.models;

import java.util.Map;

/**
 * POJO to represent a Mentee.
 *
 * This class assumes that every mentee has exactly one mentor.
 * If there becomes a need for mentees to have multiple mentors, we would need to extend
 * the {@code MultiPartnerPairable} class
 *
 * @see SinglePartnerPairable
 * @see MultiPartnerPairable
 */
public class Mentee extends SinglePartnerPairable<Mentor> {

    private final String sid;

    public Mentee(String name, String sid) {
        super(name);
        this.sid = sid;
    }

    public Mentee(String sid, String name, Map<String, Integer> topChoices) {
        super(name, topChoices);
        this.sid = sid;
    }

    public String getSid() {
        return sid;
    }

    public String getFirstName() {
        String[] nameParts = this.name().split(" ");
        String firstName = nameParts[0];
        for (int i = 1; i < nameParts.length - 1; i++) {
            firstName = firstName + " " + nameParts[i];
        }
        return firstName;
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
