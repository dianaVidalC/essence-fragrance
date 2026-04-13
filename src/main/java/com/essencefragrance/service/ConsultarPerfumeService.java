package com.essencefragrance.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ConsultarPerfumeService {
    private static final List<DetallePerfume> LISTA_BASE =  createBaseItems();

    public List<DetallePerfume> obtenerListaBase() {
        return new ArrayList<>(LISTA_BASE);
    }

    public static List<Object[]> filtrar(String nombre, double min, double max) {
        return LISTA_BASE.stream().filter(p -> {
            String n = p.getNombre().toLowerCase();
            String m = p.getMarca().toLowerCase();
            String busqueda = nombre.toLowerCase();
            double precio = p.getPrecio();

            boolean coincideNombre = n.contains(busqueda) || m.contains(busqueda);
            boolean coincidePrecio = precio >= min && precio <= max;

            return coincideNombre && coincidePrecio;
        })
        .map(p -> new Object[]{
                p.getCodigo(),
                p.getNombre(),
                p.getMarca(),
                p.getStock(),
                "$" + String.format("%.2f", p.getPrecio())
        })
        .collect(Collectors.toList());
    }

    private static List<DetallePerfume> createBaseItems() {
        List<DetallePerfume> lista = new ArrayList<>();

        lista.add(new DetallePerfume("P001", "Chanel No. 5", "Chanel", 15, 120.00));
        lista.add(new DetallePerfume("P002", "Sauvage", "Dior", 8, 95.00));
        lista.add(new DetallePerfume("P003", "Light Blue", "Dolce & Gabbana", 20, 80.00));
        lista.add(new DetallePerfume("P004", "Acqua di Gio", "Giorgio Armani", 12, 110.00));
        lista.add(new DetallePerfume("P005", "Black Opium", "YSL", 5, 105.00));

        return Collections.unmodifiableList(lista);
    }

    public static class DetallePerfume {
        private String codigo;
        private String nombre;
        private String marca;
        private int stock;
        private double precio;

        public DetallePerfume(String codigo, String nombre, String marca, int stock, double precio) {
            this.codigo = codigo;
            this.nombre = nombre;
            this.marca = marca;
            this.stock = stock;
            this.precio = precio;
        }

        public String getCodigo() {
            return codigo;
        }
        public String getNombre() {
            return nombre;
        }

        public String getMarca() {
            return marca;
        }

        public int getStock() {
            return stock;
        }

        public double getPrecio() {
            return precio;
        }
    }
}
