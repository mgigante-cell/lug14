package secondo_esercizio;

import java.util.Scanner;

import secondo_esercizio.view.CellulareView;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Cellulare mioTelefono = new Cellulare();
        CellulareView telefonoView = new CellulareView(mioTelefono);

        telefonoView.mostraMenu();
    }
}