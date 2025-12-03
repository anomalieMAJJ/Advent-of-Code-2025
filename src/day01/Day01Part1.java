package day01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Day01Part1 {
    public static void main(String[] args) {
        // Use inputs/day01/input.txt by default, or accept a path as first argument
        String input = (args != null && args.length > 0) ? args[0] : "inputs/day01/input.txt";
        int current_position = 50;
        int hit_zero = 0;

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
                    current_position = (current_position - rotation + 100) % 100;
                } else if (direction == 'R') {
                    current_position = (current_position + rotation) % 100;
                } else {
                    System.err.println("Direction invalide : " + direction);
                    continue;
                }

                if (current_position == 0) {
                    hit_zero++;
                }
            }
        } catch (IOException e) {
            System.err.println("Erreur à la lecture du fichier : " + e.getMessage());
        }
        System.out.println("Le mot de passe est : " + hit_zero);
    }
}
