package com.chase.sep.columbus.mentoring;

import java.util.*;

public abstract class SinglePartnerPairable<T extends Pairable<?>> implements Pairable<T>, Comparator<T> {

    private T partner;
    private final PriorityQueue<T> preferences;
    private final String name;
    private final Map<String, Integer> topChoices;

    protected SinglePartnerPairable(String name, Map<String, Integer> topChoices) {
        this.name = name;
        this.preferences = new PriorityQueue<>(this);
        this.topChoices = topChoices;
    }

    public T getPartner() {
        return partner;
    }

    public void setPreferences(List<T> elements) {
        this.preferences.addAll(elements);
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public T topChoice() {
        return this.preferences.poll();
    }

    @Override
    public boolean prefer(T newObj) {
        return this.partner == null || this.compare(this.partner, newObj) > 0;
    }

    @Override
    public boolean isFree() {
        return this.partner == null;
    }

    @Override
    public T replaceMatch(T newMatch) {
        T originalMatch = this.partner;
        this.partner = newMatch;
        return originalMatch;
    }

    @Override
    public void separate(T match) {
        if (this.partner == match) {
            this.partner = null;
        }
    }

    @Override
    public Collection<T> matches() {
        return Collections.singletonList(this.partner);
    }

    @Override
    public int compare(T o1, T o2) {
        int o1Score = topChoices.containsKey(o1.name())
                ? topChoices.get(o1.name())
                : topChoices.size() + 1;

        int o2Score = topChoices.containsKey(o2.name())
                ? topChoices.get(o2.name())
                : topChoices.size() + 1;

        return o1Score - o2Score;
    }
}
