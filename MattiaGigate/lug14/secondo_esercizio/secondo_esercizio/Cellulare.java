package secondo_esercizio;

import java.util.Arrays;

public class Cellulare {

    public enum ImportoTariffe {
        TARIFFA_5(5),
        TARIFFA_10(10),
        TARIFFA_25(25);

        private final int importo;

        ImportoTariffe(int importo) {
            this.importo = importo;
        }

        public int getImporto() {
            return importo;
        }
    }

    private double creditoDisponibile;
    private int chiamateEffettuate; 
    private double tariffa;

    // 1. Ricarica
    public void ricarica(int importo) {
        boolean importoValido = Arrays
                .stream(ImportoTariffe.values())
                .anyMatch(t -> t.getImporto() == importo);

        if (!importoValido)
            throw new IllegalArgumentException("Errore: Importo non valido. Tagli disponibili: 5, 10, 25.");

        creditoDisponibile += importo;
        System.out.println("\n[SMS NOTIFICA]: Ricarica di " + importo + "€ effettuata con successo!");
        System.out.printf("[SMS NOTIFICA]: Credito attuale: %.2f€\n", creditoDisponibile);
    }

    // 2. Imposta tariffa
    public void impostaTariffa(double tariffaCentesimi) {
        if (tariffaCentesimi <= 0)
            throw new IllegalArgumentException("Errore: Tariffa non valida");

        this.tariffa = tariffaCentesimi / 100.0;
        System.out.println("\n[SMS NOTIFICA]: Cambio piano effettuato. Nuova tariffa: " + tariffaCentesimi + " cent/min.");
    }

    // 3. Chiama
    public void chiama(String numero, int minuti) {
        if (minuti <= 0)
            throw new IllegalArgumentException("Errore: La durata deve essere maggiore di 0 minuti.");
        if (numero == null || numero.trim().isEmpty())
            throw new IllegalArgumentException("Errore: Il numero da comporre non può essere vuoto.");
        if (this.tariffa <= 0)
            throw new IllegalArgumentException("Errore: Nessuna SIM inserita o piano tariffario mancante.");
        if (this.creditoDisponibile <= 0)
            throw new IllegalArgumentException("Errore: Credito insufficiente per effettuare la chiamata.");

        double costoPrevisto = minuti * tariffa;
        chiamateEffettuate++;

        if (creditoDisponibile >= costoPrevisto) {
            // Credito sufficiente
            creditoDisponibile -= costoPrevisto;
            System.out.println("\nChiamata al " + numero + " terminata. Durata: " + minuti + " min.");
        } else {
            // Credito insufficiente per tutta la durata
            int minutiEffettivi = (int) (creditoDisponibile / tariffa);
            creditoDisponibile = 0.0;
            System.out.println("\n[SMS NOTIFICA]: La chiamata al " + numero +
                    " è stata interrotta dopo " + minutiEffettivi +
                    " min per esaurimento del credito.");
        }
    }

    // 4. Visualizza credito
    public void mostraCredito() {
        System.out.printf("\nIl tuo credito residuo è di %.2f€\n", creditoDisponibile);
    }

    // 5. Visualizza quantità di chiamate effettuate
    public void mostraChiamate() {
        System.out.println("\nHai effettuato un totale di " + chiamateEffettuate + " chiamate.");
    }

    // 6. Azzera chiamate
    public void azzeraChiamate() {
        chiamateEffettuate = 0;
        System.out.println("\n[SMS NOTIFICA]: Il contatore delle chiamate è stato azzerato con successo.");
    }
}