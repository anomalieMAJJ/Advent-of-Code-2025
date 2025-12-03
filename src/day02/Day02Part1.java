package day02;

import java.io.BufferedReader;
import java.io.FileReader;

public class Day02Part1 {
    public static void main(String[] args) {
        String input = (args != null && args.length > 0) ? args[0] : "Advent-of-Code-2025/inputs/day02/input.txt";
        System.out.println("[TEMPLATE] Day02 Part1 — input: " + input);
        long total_invalid_IDs = 0L;
        String[] ranges;

        try (BufferedReader reader = new BufferedReader(new FileReader(input))) {
            String line = reader.readLine();
            if (line == null) {
                System.err.println("Fichier d'entrée vide: " + input);
                return;
            }
            ranges = line.split(","); // split par ',' et chaque ranges[i] est de la forme "start-end"
        } catch (java.io.IOException e) {
            System.err.println("Erreur de lecture du fichier: " + e.getMessage());
            return;
        }

        for (String range : ranges) {
            String[] limites = range.split("-"); // split par '-' pour `start` et `end`
            long start = Long.parseLong(limites[0]);
            long end = Long.parseLong(limites[1]);

            for (long id = start; id <= end; id++) {
                String curr_id = Long.toString(id);
                int taille = curr_id.length();
                // On check si le nombre a une longueur paire, sinon il ne peut pas se répéter.
                if (taille % 2 == 0) {
                    String premiere_partie = curr_id.substring(0, taille / 2);
                    String deuxieme_partie = curr_id.substring(taille / 2);
                    // Si les deux parties sont identiques, alors on a un ID invalide.
                    if (premiere_partie.equals(deuxieme_partie)) {
                        total_invalid_IDs += id;
                    }
                }
            }
        }

        System.out.println("Le total des IDs invalides est : " + total_invalid_IDs);
    }
}
