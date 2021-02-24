package com.chase.sep.columbus.mentoring;

import java.util.Collection;
import java.util.Map;
import java.util.PriorityQueue;

public abstract class MultiPartnerPairable<T extends Pairable<?>> extends SinglePartnerPairable<T> {

    private final PriorityQueue<T> matches;
    protected final int maxPartners;

    protected MultiPartnerPairable(String name, int maxPartners, Map<String, Integer> topChoices) {
        super(name, topChoices);
        this.maxPartners = maxPartners;
        this.matches = new PriorityQueue<>((o1, o2) -> -1 * this.compare(o1, o2));
    }

    @Override
    public T getPartner() {
        throw new UnsupportedOperationException("Cannot get single partner for multi-partner Pairable");
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
