package com.chase.sep.columbus.mentoring.models;

import java.util.Collection;
import java.util.List;

/**
 * Represents an entity that can be paired with other entities using either the
 * stable marriage algorithm or the hungarian algorithm.
 *
 * This interface defines
 * methods that are needed for the stable marriage algorithm to function and allows
 * implementing classes the ability to implement them however they want. Ultimately,
 * the stable marriage algorithm doesn't care how these operations are implemented,
 * thus this is represented as an interface with no default implementations.
 *
 * @param <T> - the type with which the implementing entity shall be paired up
 */
public interface Pairable<T extends Pairable<?>> {

    /**
     * This {@code Pairable}'s name
     *
     * @return - the name of this {@code Pairable}
     */
    String name();

    /**
     * Used to determine whether the {@code Pairable} prefers the new
     * one over an old one
     *
     * @param newObj - potential new object
     * @return true if {@code newObj} is preferred over {@code oldObj}
     */
    boolean prefer(T newObj);

    /**
     * Helps determine whether the {@code Pairable} is able to be
     * paired still
     *
     * @return true if the {@code Pairable} is free and false otherwise
     */
    boolean isFree();

    /**
     * Set the full priority queue of preferences for this {@code Pairable}
     *
     * @param elements - the list of elements to add to the priority queue
     */
    void setPreferences(List<T> elements);

    /**
     * Gets the {@code Pairable}'s current top choice
     *
     * @return - the current top choice based on the preferences
     */
    T topChoice();

    /**
     * Replaces the old match with the new object
     *
     * @param newMatch - new match
     * @return - old match
     */
    T replaceMatch(T newMatch);

    /**
     * Removes the match
     *
     * @param match - the existing match
     */
    void separate(T match);

    /**
     * Return all the matches associated to this element
     *
     * @return a list of all the matches
     */
    Collection<T> matches();

    /**
     * Returns the maximum number of partners this object can be paired with
     *
     * @return - {@code int} max number of partners
     */
    default int getMaxPartners() {
        return 1;
    }

}
