package com.chase.sep.columbus.mentoring.models;

import java.util.Map;

/**
 * POJO representing mentors.
 *
 * In most cases, mentors have more than 1 mentee, so this class extends {@code MultiPartnerPairable}.
 * Even if the mentor has no more than 1 mentee, this class will still suffice. However, if we have a case
 * where all mentors have exactly 1 mentee, then we can either stick with this class and make sure everyone's
 * {@code maxPartners} is 1 or just create a brand new class that extends {@code SinglePartnerPairable}
 *
 * @see SinglePartnerPairable
 * @see MultiPartnerPairable
 */
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
