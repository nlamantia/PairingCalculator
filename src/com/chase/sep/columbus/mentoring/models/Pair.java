package com.chase.sep.columbus.mentoring.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Simple POJO representing a pair of an entity of type {@code S} and an entity of type {@code T}
 *
 * NOTE: {@code S} and {@code T} can be the same, even though there's no example of this currently
 *
 * @param <S> - type of one entity in the pair
 * @param <T> - type of the other entity in the pair
 */
public class Pair<S extends Pairable<T>, T extends Pairable<S>> {

    private final List<Pairable<?>> pairables;

    public Pair(S p1, Collection<T> pairables) {
        this.pairables = new ArrayList<>();
        this.pairables.add(p1);
        this.pairables.addAll(pairables);
    }

    public List<Pairable<?>> getPairables() {
        return pairables;
    }

    @Override
    public String toString() {
        return this.pairables.stream()
                .map(Pairable::name)
                .collect(Collectors.joining(", "));
    }
}
