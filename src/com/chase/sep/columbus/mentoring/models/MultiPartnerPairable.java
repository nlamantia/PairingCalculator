package com.chase.sep.columbus.mentoring.models;

import java.util.Collection;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Class that represents entities that can be paired with multiple partners in pairing situations.
 * This class was created to make the stable marriage algorithm work in multi-partner situations.
 *
 * @see SinglePartnerPairable
 *
 * @param <T> - the type of entity with which we are pairing entities of this child class's type
 */
public abstract class MultiPartnerPairable<T extends Pairable<?>> extends SinglePartnerPairable<T> {

    private final PriorityQueue<T> matches;
    protected final int maxPartners;

    public MultiPartnerPairable(String name, int maxPartners) {
        super(name);
        this.maxPartners = maxPartners;
        this.matches = new PriorityQueue<>((o1, o2) -> -1 * this.compare(o1, o2));
    }

    protected MultiPartnerPairable(String name, int maxPartners, Map<String, Integer> topChoices) {
        super(name, topChoices);
        this.maxPartners = maxPartners;
        this.matches = new PriorityQueue<>((o1, o2) -> -1 * this.compare(o1, o2));
    }

    @Override
    public int getMaxPartners() {
        return this.maxPartners;
    }

    @Override
    public boolean prefer(T newObj) {
        if (newObj != null) {
            T currentWeakestMatch = this.matches.peek();
            if (currentWeakestMatch != null) {
                return this.compare(currentWeakestMatch, newObj) > 0;
            }
        }
        return false;
    }

    @Override
    public boolean isFree() {
        return this.matches.size() < this.maxPartners;
    }

    @Override
    public T replaceMatch(T newMatch) {
        T weakestMatch = null;
        if (this.matches.size() == this.maxPartners) {
            weakestMatch = this.matches.poll();
        }
        this.matches.add(newMatch);
        return weakestMatch;
    }

    @Override
    public void separate(T match) {
        this.matches.remove(match);
    }

    @Override
    public Collection<T> matches() {
        return this.matches;
    }
}
