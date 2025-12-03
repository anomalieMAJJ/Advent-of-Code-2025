package day01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day01Part2 {
    public static void main(String[] args) {
        String input = (args != null && args.length > 0) ? args[0] : "inputs/day01/input.txt";
        int current_position = 50;
        int clicks_for_zero_hit = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(input))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank())
                    continue;
                char direction = line.charAt(0);
                int rotation;
                try {
                    rotation = Integer.parseInt(line.substring(1).trim());
                } catch (NumberFormatException nfe) {
                    System.err.println("Ligne ignorée (format invalide) : " + line);
                    continue;
                }

                if (direction == 'L') {
                    for (int i = 0; i < rotation; i++) {
                        current_position = (current_position - 1 + 100) % 100;
                        if (current_position == 0) {
                            clicks_for_zero_hit++;
                        }
                    }
                } else if (direction == 'R') {
                    for (int i = 0; i < rotation; i++) {
                        current_position = (current_position + 1) % 100;
                        if (current_position == 0) {
                            clicks_for_zero_hit++;
                        }
                    }
                } else {
                    System.err.println("Direction invalide : " + direction);
                    continue;
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur à la lecture du fichier : " + e.getMessage());
        }
        System.out.println("Le mot de passe est : " + clicks_for_zero_hit);
    }
}
