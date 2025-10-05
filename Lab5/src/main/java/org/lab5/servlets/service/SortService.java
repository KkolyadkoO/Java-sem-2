package org.lab5.servlets.service;

import java.util.Arrays;

public class SortService {
    public int[] sortNumbers(String input) {
        if (input == null || input.isBlank()) {
            return new int[0];
        }

        // Разбиваем строку по запятым
        String[] parts = input.split(",");
        int[] numbers = new int[parts.length];

        for (int i = 0; i < parts.length; i++) {
            numbers[i] = Integer.parseInt(parts[i].trim());
        }

        Arrays.sort(numbers);
        return numbers;
    }
}
