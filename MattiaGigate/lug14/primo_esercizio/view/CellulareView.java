package primo_esercizio.view;

import java.util.Scanner;
import primo_esercizio.Cellulare;

public class CellulareView {
    private Scanner scanner = new Scanner(System.in);
    private Cellulare mioTelefono;
    private RubricaView rubricaView;

    private SlotMachineView slotMachineView;

    public CellulareView(Cellulare telefono) {
        this.mioTelefono = telefono;
        this.rubricaView = new RubricaView(this, telefono);

        this.slotMachineView = new SlotMachineView(this);
    }

    public void mostraMenu() {
        boolean acceso = true;

        while (acceso) {
            System.out.println("\nMENU CELLULARE");
            System.out.println("1. Ricarica credito (5€, 10€, 25€)");
            System.out.println("2. Imposta tariffa (cent/min)");
            System.out.println("3. Effettua una chiamata");
            System.out.println("4. Visualizza credito disponibile");
            System.out.println("5. Visualizza chiamate effettuate");
            System.out.println("6. Azzera chiamate effettuate");
            System.out.println("7. Gestione Rubrica");
            System.out.println("8. Slot Machine");
            System.out.println("9. Spegni il cellulare");
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
                    System.out.println("\n1. Scegli contatto dalla rubrica");
                    System.out.println("2. Digita numero manualmente");
                    System.out.print("Scelta: ");
                    int modalita = leggiIntero();

                    String numero = "";
                    if (modalita == 1) {
                        mioTelefono.mostraRubrica();
                        System.out.print("Inserisci l'indice del contatto da chiamare: ");
                        int indice = leggiIntero();
                        numero = mioTelefono.getNumeroDaRubrica(indice);
                        if (numero == null) {
                            System.out.println("\nIndice non valido o rubrica vuota.");
                            continue;
                        }
                    } else if (modalita == 2) {
                        System.out.print("Inserisci il numero da chiamare: ");
                        numero = leggiStringa();
                    } else {
                        System.out.println("\nOpzione non valida.");
                        continue;
                    }

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
                case 7 -> rubricaView.mostraMenu();

                case 8 -> slotMachineView.avviaSlotMachine();
                case 9 -> {
                    System.out.println("\nSpegnimento in corso...");
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