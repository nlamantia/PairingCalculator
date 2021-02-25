package com.chase.sep.columbus.mentoring;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Pair<S extends Pairable<T>, T extends Pairable<S>> {

    private final List<Pairable<?>> pairables;

    public Pair(S p1, Collection<T> pairables) {
        this.pairables = new ArrayList<>();
        this.pairables.add(p1);
        this.pairables.addAll(pairables);
    }

    @Override
    public String toString() {
        return this.pairables.stream()
                .map(Pairable::name)
                .collect(Collectors.joining(", "));
    }

    public static <S extends Pairable<T>, T extends Pairable<S>> List<Pair<T, S>> invert(List<Pair<S, T>> list) {
        // TODO
        return Collections.emptyList();
    }
}
