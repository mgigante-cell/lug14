package primo_esercizio.view;

public class SlotMachineView {

    private CellulareView telefonoView;

    public SlotMachineView(CellulareView telefonoView) {
        this.telefonoView = telefonoView;
    }

    public void avviaSlotMachine() {
        System.out.println("\nBENVENUTO ALLA SLOT MACHINE");
        int partiteGiocate = 0;
        int vittorie = 0;
        boolean inGioco = true;

        while (inGioco) {
            System.out.println("\n1. Gioca");
            System.out.println("2. Esci");
            System.out.print("Scelta: ");

            int sc = telefonoView.leggiIntero();

            if (sc == 1) {
                partiteGiocate++;
                int n1 = (int) (Math.random() * 5) + 1;
                int n2 = (int) (Math.random() * 5) + 1;
                int n3 = (int) (Math.random() * 5) + 1;

                System.out.println("\nEstrazione: [ " + n1 + " | " + n2 + " | " + n3 + " ]");

                if (n1 == n2 && n2 == n3) {
                    System.out.println("HAI VINTO! Tris di " + n1 + "!");
                    vittorie++;
                } else
                    System.out.println("Peccato, hai perso. Ritenta!");
            } else if (sc == 2) {
                System.out.println("\nUscita dall'app Slot Machine...");
                if (partiteGiocate > 0)
                    System.out.println("Statistiche: Hai vinto " + vittorie + " volte su " + partiteGiocate
                            + " partite giocate.");
                inGioco = false;
            } else
                System.out.println("Opzione non valida.");
        }
    }
}