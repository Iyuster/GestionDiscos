package com.mycompany.gestiondiscos;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;

class Disco {
    private String titulo;
    private String artista;
    private int anio;

    public Disco(String titulo, String artista, int anio) {
        this.titulo = titulo;
        this.artista = artista;
        this.anio = anio;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getArtista() {
        return artista;
    }

    public int getAnio() {
        return anio;
    }

    @Override
    public String toString() {
        return "Título: " + titulo + ", Artista: " + artista + ", Año: " + anio;
    }
}

public class GestionDiscos {
    private ArrayList<Disco> discos;

    public GestionDiscos() {
        discos = new ArrayList<>();
        cargarDiscos();  // Cargar discos desde archivo al iniciar el programa
    }

    public void agregarDisco(String titulo, String artista, int anio) {
        // Validaciones
        if (anio <= 0) {
            throw new IllegalArgumentException("El año debe ser positivo.");
        }
        if (titulo.isEmpty() || artista.isEmpty()) {
            throw new IllegalArgumentException("El título y el artista no pueden estar vacíos.");
        }

        Disco nuevoDisco = new Disco(titulo, artista, anio);
        discos.add(nuevoDisco);
        System.out.println("Disco agregado: " + nuevoDisco);
        guardarDiscos();  // Guardar discos en el archivo después de agregar uno nuevo
    }

    public void mostrarDiscos() {
        if (discos.isEmpty()) {
            System.out.println("La lista de discos está vacía.");
        } else {
            System.out.println("Lista de discos:");
            for (Disco disco : discos) {
                System.out.println(disco);
            }
        }
    }

    public void eliminarDisco(String titulo) {
        boolean encontrado = false;
        for (Disco disco : discos) {
            if (disco.getTitulo().equalsIgnoreCase(titulo)) {
                discos.remove(disco);
                System.out.println("Disco eliminado: " + disco);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            System.out.println("Disco no encontrado.");
        }
        guardarDiscos();  // Guardar los discos después de una eliminación
    }

    // Guardar discos en un archivo de texto
    public void guardarDiscos() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("discos.txt"))) {
            for (Disco disco : discos) {
                writer.write(disco.getTitulo() + "," + disco.getArtista() + "," + disco.getAnio());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al guardar los discos.");
        }
    }

    // Cargar discos desde un archivo de texto
    public void cargarDiscos() {
        try (BufferedReader reader = new BufferedReader(new FileReader("discos.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] datos = line.split(",");
                discos.add(new Disco(datos[0], datos[1], Integer.parseInt(datos[2])));
            }
        } catch (IOException e) {
            System.out.println("Error al cargar los discos.");
        }
    }

    public static void main(String[] args) {
        GestionDiscos gestionDiscos = new GestionDiscos();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Agregar disco");
            System.out.println("2. Mostrar discos");
            System.out.println("3. Eliminar disco");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion;
            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea después del número
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                scanner.nextLine(); // Limpiar el buffer
                continue;
            }

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el título del disco: ");
                    String titulo = scanner.nextLine();
                    System.out.print("Ingrese el artista del disco: ");
                    String artista = scanner.nextLine();
                    System.out.print("Ingrese el año del disco: ");
                    int anio;
                    try {
                        anio = scanner.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println("Por favor, ingrese un año válido.");
                        scanner.nextLine(); // Limpiar el buffer
                        continue;
                    }
                    try {
                        gestionDiscos.agregarDisco(titulo, artista, anio);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 2:
                    gestionDiscos.mostrarDiscos();
                    break;
                case 3:
                    System.out.print("Ingrese el título del disco a eliminar: ");
                    String tituloEliminar = scanner.nextLine();
                    gestionDiscos.eliminarDisco(tituloEliminar);
                    break;
                case 4:
                    System.out.println("Saliendo del programa.");
                    System.exit(0);
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }
}
