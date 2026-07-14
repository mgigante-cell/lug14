package primo_esercizio.view;

import primo_esercizio.Cellulare;

public class RubricaView {
    
    private CellulareView telefonoView; 
    private Cellulare mioTelefono;      

    public RubricaView(CellulareView telefonoView, Cellulare telefono) {
        this.telefonoView = telefonoView;
        this.mioTelefono = telefono;
    }

    public void mostraMenu() {
        System.out.println("\nGESTIONE RUBRICA");
        System.out.println("1. Visualizza rubrica");
        System.out.println("2. Aggiungi nuovo contatto");
        System.out.println("3. Rimuovi contatto");
        System.out.print("Scelta: ");

        int scelta = telefonoView.leggiIntero();

        switch (scelta) {
            case 1 -> mioTelefono.mostraRubrica();
            case 2 -> {
                System.out.print("Nome: ");
                String nome = telefonoView.leggiStringa(); 
                System.out.print("Numero: ");
                String num = telefonoView.leggiStringa();
                mioTelefono.aggiungiContatto(nome, num);
            }
            case 3 -> {
                mioTelefono.mostraRubrica();
                System.out.print("Inserisci l'indice del contatto da rimuovere: ");
                int indice = telefonoView.leggiIntero();
                mioTelefono.rimuoviContatto(indice);
            }
            default -> System.out.println("\nOpzione non valida.");
        }
    }
}