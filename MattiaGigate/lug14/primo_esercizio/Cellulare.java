package primo_esercizio;

import java.util.List;
import java.util.ArrayList;
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
    private double tariffa;

    private List<DettaglioChiamata> registroChiamate = new ArrayList<>();
    private List<Contatto> rubrica = new ArrayList<>();

    // 1. Ricarica
    public void ricarica(int importo) {
        boolean importoValido = Arrays
                .stream(ImportoTariffe.values())
                .anyMatch(t -> t.getImporto() == importo);

        if (!importoValido)
            throw new IllegalArgumentException("Errore: Importo non valido. Taglio non disponibile.");

        creditoDisponibile += importo;
        System.out.println("\n[SMS NOTIFICA]: Ricarica di " + importo + "€ effettuata con successo!");
        System.out.printf("[SMS NOTIFICA]: Credito attuale: %.2f€\n", creditoDisponibile);
    }

    // 2. Imposta tariffa
    public void impostaTariffa(double tariffaCentesimi) {
        if (tariffaCentesimi <= 0)
            throw new IllegalArgumentException("Errore: Tariffa non valida");

        this.tariffa = tariffaCentesimi / 100.0;
        System.out.println(
                "\n[SMS NOTIFICA]: Cambio piano effettuato. Nuova tariffa: " + tariffaCentesimi + " centesimi/minuto.");
    }

    // 3. Chiama
    public void chiama(String numero, int minuti) {
        if (minuti <= 0)
            throw new IllegalArgumentException("Errore: La durata della chiamata deve essere maggiore di 0 minuti.");
        if (numero == null || numero.trim().isEmpty())
            throw new IllegalArgumentException("Errore: Il numero da comporre non può essere vuoto.");

        if (this.tariffa <= 0)
            throw new IllegalArgumentException(
                    "Errore: Impossibile chiamare. Nessuna SIM inserita o piano tariffario mancante.");
        if (this.creditoDisponibile <= 0)
            throw new IllegalArgumentException("Errore: Credito insufficiente per effettuare la chiamata.");

        double costoPrevisto = minuti * tariffa;

        if (creditoDisponibile >= costoPrevisto) {
            creditoDisponibile -= costoPrevisto;
            registroChiamate.add(new DettaglioChiamata(numero, minuti));

            String nomeDisplay = getNomeDaNumero(numero);
            System.out.println("\nChiamata a " + nomeDisplay + " terminata. Durata: " + minuti + " min.");
        } else {
            int minutiEffettivi = (int) (creditoDisponibile / tariffa);
            registroChiamate.add(new DettaglioChiamata(numero, minutiEffettivi));
            creditoDisponibile = 0.0;
            String nomeDisplay = getNomeDaNumero(numero);
            System.out.println("\n[SMS NOTIFICA]: La chiamata a " + nomeDisplay +
                    " è stata interrotta dopo " + minutiEffettivi +
                    " min per esaurimento del credito.");
        }
    }

    // 4. Visualizza credito
    public void mostraCredito() {
        System.out.printf("\nIl tuo credito residuo è di %.2f€\n", creditoDisponibile);
    }

    // 5. Visualizza chiamate effettuate (Avanzata)
    public void mostraChiamate() {
        if (registroChiamate.isEmpty()) {
            System.out.println("\nNessuna chiamata effettuata.");
            return;
        }

        System.out.println("\nDETTAGLIO CHIAMATE EFFETTUATE");
        for (int i = 0; i < registroChiamate.size(); i++) {
            DettaglioChiamata c = registroChiamate.get(i);
            String nomeDisplay = getNomeDaNumero(c.numero);

            if (nomeDisplay.equals(c.numero))
                System.out.println((i + 1) + ". Numero: " + c.numero + " | Durata: " + c.minuti + " min");
            else
                System.out.println(
                        (i + 1) + ". Contatto: " + nomeDisplay + " (" + c.numero + ") | Durata: " + c.minuti + " min");
        }
        System.out.println("Totale chiamate effettuate: " + registroChiamate.size());
    }

    // 6. Azzera chiamate
    public void azzeraChiamate() {
        registroChiamate.clear();
        System.out.println("\n[SMS NOTIFICA]: Il registro delle chiamate è stato azzerato con successo.");
    }

    // rubrica
    public void aggiungiContatto(String nome, String numero) {
        rubrica.add(new Contatto(nome, numero));
        System.out.println("Contatto '" + nome + "' aggiunto con successo.");
    }

    public void rimuoviContatto(int indice) {
        if (indice >= 1 && indice <= rubrica.size()) {
            Contatto rimosso = rubrica.remove(indice - 1);
            System.out.println("Contatto '" + rimosso.nome + "' rimosso con successo.");
        } else
            System.out.println("Errore: Indice non valido.");

    }

    public void mostraRubrica() {
        if (rubrica.isEmpty()) {
            System.out.println("\nLa rubrica è vuota.");
            return;
        }
        System.out.println("\nRUBRICA");
        for (int i = 0; i < rubrica.size(); i++)
            System.out.println((i + 1) + ". " + rubrica.get(i).nome + " - " + rubrica.get(i).numero);
    }

    public String getNumeroDaRubrica(int indice) {
        if (indice >= 1 && indice <= rubrica.size())
            return rubrica.get(indice - 1).numero;

        return null;
    }

    private String getNomeDaNumero(String numero) {
        for (Contatto c : rubrica)
            if (c.numero.equals(numero))
                return c.nome;
            
        return numero;
    }
}