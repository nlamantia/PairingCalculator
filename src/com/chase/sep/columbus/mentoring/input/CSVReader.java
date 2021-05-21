package com.chase.sep.columbus.mentoring.input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that parses Java objects out of a CSV file
 *
 * @param <T> - the type of object we are instantiating based on the information from a CSV file
 */
public abstract class CSVReader<T> {

    private final List<T> data;

    protected CSVReader(String fileName) {
        this.data = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            int i = 0;
            String line;
            while ((line = reader.readLine()) != null) {
                T obj = parseRow(line.split(","), i);
                if (obj != null) {
                    this.data.add(obj);
                }
                i++;
            }
        } catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    /**
     * Parses a {@code T} out of a CSV row
     *
     * @param row - array of values that were separated by commas in a row in a CSV file
     * @param index - which row is being parsed
     * @return - an instance of {@code T}
     */
    protected abstract T parseRow(String[] row, int index);

    public List<T> getData() {
        return data;
    }
}
