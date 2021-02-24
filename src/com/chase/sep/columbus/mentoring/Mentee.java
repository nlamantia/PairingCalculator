package com.chase.sep.columbus.mentoring;

import java.util.Map;

public class Mentee extends SinglePartnerPairable<Mentor> {

    public Mentee(String name, Map<String, Integer> topChoices) {
        super(name, topChoices);
    }

}
