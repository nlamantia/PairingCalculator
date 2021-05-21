package com.chase.sep.columbus.mentoring.hungarian;

import com.chase.sep.columbus.mentoring.models.Pair;
import com.chase.sep.columbus.mentoring.models.Pairable;

import java.util.List;

/**
 * This class represents a hungarian matrix that is used to create 2-D integer arrays
 * that can be optimized to find the best combination of {@code S}s and {@code T}s.
 *
 * @param <S> - type of {@code list1}
 * @param <T> - type of {@code list2}
 */
public abstract class HungarianMatrix<S extends Pairable<T>, T extends Pairable<S>> {

    private final List<S> list1;
    private final List<T> list2;

    public HungarianMatrix(List<S> list1, List<T> list2) {
        this.list1 = list1;
        this.list2 = list2;
    }

    /**
     * Given two lists of associated {@code Pairable}s, this method will return a square integer
     * matrix, where the number in each cell assigns a cost/preference value to the combination
     * between this particular element in {@code list1} and {@code list2}. The {@code PreferencesMatrix}
     * class will be in charge of choosing the optimal combinations given this matrix.
     *
     * @param list1 - first list of {@code Pairable}s
     * @param list2 - second list of {@code Pairable}s
     * @return - preferences matrix to be optimized
     */
    protected abstract int[][] buildMatrix(List<S> list1, List<T> list2);

    /**
     * Given an array of optimal assignments, this method will make the associations from integers to
     * the actual items in the list
     *
     * @param list1 - first list of {@code Pairable}s
     * @param list2 - second list of {@code Pairable}s
     * @param assignments - an array of integer pairs representing the indices in the list that form optimal assignments
     * @return - a list of {@code Pair}s of {@code <S>}s and {@code <T>}s based on the optimal assignments
     */
    protected abstract List<Pair<S, T>> createPairsFromAssignments(List<S> list1, List<T> list2, int[][] assignments);

    public List<Pair<S, T>> getPairs() {
        PreferencesMatrix preferencesMatrix = new PreferencesMatrix(buildMatrix(list1, list2));
        return createPairsFromAssignments(list1, list2, preferencesMatrix.getOptimalAssignments());
    }

}
