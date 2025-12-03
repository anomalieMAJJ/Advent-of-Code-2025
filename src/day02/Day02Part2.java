package day02;

import java.io.BufferedReader;
import java.io.FileReader;

public class Day02Part2 {
    public static void main(String[] args) {
        System.out.println("cwd=" + System.getProperty("user.dir"));
        String input = (args != null && args.length > 0) ? args[0] : "Advent-of-Code-2025/inputs/day02/input.txt";
        System.out.println("[TEMPLATE] Day02 Part2 — input: " + input);
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
            String[] limites = range.split("-");
            long start = Long.parseLong(limites[0]);
            long end = Long.parseLong(limites[1]);

            for (long id = start; id <= end; id++) {
                String idStr = Long.toString(id);
                int longueur = idStr.length(); // nombre de chiffres de l'ID

                boolean est_invalide = false;
                // essayer chaque taille de bloc possible (1 à longueur/2, car longueur/2 est la
                // plus grande taille de bloc possible pour un pattern répété minimalement 2
                // fois)
                for (int tailleBloc = 1; tailleBloc <= longueur / 2 && !est_invalide; tailleBloc++) {

                    if (longueur % tailleBloc != 0) // si la longueur n'est pas divisible par la taille du bloc
                        continue; // impossible d'avoir un pattern répété pour cette taille

                    int repetitions = longueur / tailleBloc; // nombre de répétitions du bloc

                    if (repetitions < 2) // moins de deux répétitions => pas un pattern répété
                        continue; // on passe à la taille de bloc suivante

                    String bloc = idStr.substring(0, tailleBloc); // extraire le 1er bloc (1ers caractères)
                    boolean match = true; // on suppose que toutes les répétitions correspondent

                    // pour chaque répétition qui suit, on compare la sous-chaîne à la position
                    // indiceRep*tailleBloc avec le 1er bloc
                    for (int indiceRep = 1; indiceRep < repetitions; indiceRep++) {

                        // si une répétitione ne correspond pas au bloc initial = pas de pattern répété
                        if (!idStr.regionMatches(indiceRep * tailleBloc, bloc, 0, tailleBloc)) {
                            match = false;
                            break;
                        }
                    }
                    // si un pattern se répète = ID invalide
                    if (match)
                        est_invalide = true;
                }

                // Si invalide, on ajoute sa valeur au total des IDs invalides
                if (est_invalide) {
                    total_invalid_IDs += id;
                }
            }
        }

        System.out.println("Le total des IDs invalides est : " + total_invalid_IDs);
    }
}
