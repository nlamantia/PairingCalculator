package com.chase.sep.columbus.mentoring;

import java.util.Map;

public class Mentor extends MultiPartnerPairable<Mentee> {

    public Mentor(String name, int maxPartners, Map<String, Integer> topChoices) {
        super(name, maxPartners, topChoices);
    }

}
