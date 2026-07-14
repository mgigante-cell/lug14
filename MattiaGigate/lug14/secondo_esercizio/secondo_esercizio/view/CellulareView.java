package secondo_esercizio.view;

import java.util.Scanner;
import secondo_esercizio.Cellulare;

public class CellulareView {
    private Scanner scanner = new Scanner(System.in);
    private Cellulare mioTelefono;

    public CellulareView(Cellulare telefono) {
        this.mioTelefono = telefono;
    }

    public void mostraMenu() {
        boolean acceso = true;

        while (acceso) {
            System.out.println("\nMENU CELLULARE");
            System.out.println("1. Ricarica credito (5€, 10€, 25€)");
            System.out.println("2. Imposta tariffa (cent/min)");
            System.out.println("3. Effettua una chiamata");
            System.out.println("4. Visualizza credito disponibile");
            System.out.println("5. Visualizza quantità chiamate effettuate");
            System.out.println("6. Azzera chiamate effettuate");
            System.out.println("7. Spegni il cellulare");
            System.out.print("Seleziona un'opzione: ");

            int scelta = leggiIntero();

            switch (scelta) {
                case 1 -> {
                    System.out.print("Inserisci l'importo da ricaricare (5, 10 o 25): ");
                    int importo = leggiIntero();
                    try {
                        mioTelefono.ricarica(importo);
                    } catch (IllegalArgumentException e) {
                        System.out.println("\n[Errore]: " + e.getMessage());
                    }
                }
                case 2 -> {
                    System.out.print("Inserisci la nuova tariffa in centesimi al minuto: ");
                    double tariffa = leggiDouble();
                    try {
                        mioTelefono.impostaTariffa(tariffa);
                    } catch (IllegalArgumentException e) {
                        System.out.println("\n[Errore]: " + e.getMessage());
                    }
                }
                case 3 -> {
                    // Niente più rubrica qui, si digita direttamente il numero
                    System.out.print("Inserisci il numero da chiamare: ");
                    String numero = leggiStringa();
                    
                    System.out.print("Inserisci i minuti di durata desiderati: ");
                    int minuti = leggiIntero();

                    try {
                        mioTelefono.chiama(numero, minuti);
                    } catch (IllegalArgumentException e) {
                        System.out.println("\n[Errore]: " + e.getMessage());
                    }
                }
                case 4 -> mioTelefono.mostraCredito();
                case 5 -> mioTelefono.mostraChiamate();
                case 6 -> mioTelefono.azzeraChiamate();
                case 7 -> {
                    System.out.println("\nSpegnimento in corso... Arrivederci!");
                    acceso = false;
                }
                default -> System.out.println("\nOpzione non valida. Riprova.");
            }
        }
        scanner.close();
    }

    public int leggiIntero() {
        while (!scanner.hasNextInt()) {
            System.out.println("\nDevi inserire un numero intero valido!");
            scanner.nextLine();
            System.out.print("Riprova: ");
        }
        int valore = scanner.nextInt();
        scanner.nextLine();
        return valore;
    }

    public double leggiDouble() {
        while (!scanner.hasNextDouble()) {
            System.out.println("\nDevi inserire un numero decimale valido!");
            scanner.nextLine();
            System.out.print("Riprova: ");
        }
        double valore = scanner.nextDouble();
        scanner.nextLine();
        return valore;
    }

    public String leggiStringa() {
        return scanner.nextLine();
    }
}