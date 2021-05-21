package com.chase.sep.columbus.mentoring.models;

import java.util.*;

/**
 * Class that represents entities that can be paired with exactly 1 partner in pairing situations.
 * This class was created to represent the base case in pairing scenarios.
 *
 * @see Pairable
 *
 * @param <T> - the type of entity with which we are pairing entities of this child class's type
 */
public abstract class SinglePartnerPairable<T extends Pairable<?>> implements Pairable<T>, Comparator<T> {

    private T partner;
    private final PriorityQueue<T> preferences;
    private final String name;
    private Map<String, Integer> topChoices;

    public SinglePartnerPairable(String name) {
        this.name = name;
        this.preferences = new PriorityQueue<>(this);
    }

    protected SinglePartnerPairable(String name, Map<String, Integer> topChoices) {
        this(name);
        this.topChoices = topChoices;
    }

    public Map<String, Integer> getTopChoices() {
        return this.topChoices;
    }

    public void setTopChoices(Map<String, Integer> topChoices) {
        this.topChoices = topChoices;
    }

    public void setPreferences(List<T> elements) {
        this.preferences.addAll(elements);
    }

    public Map<T, Integer> getPreferences() {
        Map<T, Integer> preferencesMap = new LinkedHashMap<>();
        preferences.forEach((obj) -> preferencesMap.put(obj, this.getPreferenceScore(obj)));
        return preferencesMap;
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
        return getPreferenceScore(o1) - getPreferenceScore(o2);
    }

    public int getPreferenceScore(T obj) {
        return topChoices.containsKey(obj.name())
                ? topChoices.get(obj.name())
                : topChoices.size() + 1;
    }
}
