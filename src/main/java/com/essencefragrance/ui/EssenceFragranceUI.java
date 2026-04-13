package com.essencefragrance.ui;

import javax.swing.*;
import java.awt.*;

public class EssenceFragranceUI extends JFrame {
    private JMenuBar menuBar;
    private JMenu menuArchivo, menuInventario, menuVentas, menuConfig, menuReportes, menuAyuda;
    private JMenuItem itemSalir, itemConsultar, itemAgregar, itemModificar, itemListar, itemAcercaDe, itemNuevaVenta,
            itemHistorialVentas, itemDescuentos, itemObsequios, itemTopProductos, itemResumenVentas;
    private ImageIcon icono, imagenFondo;
    private JPanel panelPrincipal;
    private CardLayout cardLayout;

    public EssenceFragranceUI() {
        initUI();
        addEventListeners();
    }

    private void initUI() {
        configureWindow();
        initializeComponents();
        configureLayout();
    }

    private void configureWindow() {
        setTitle("Essence Fragrance");
        setSize(1100, 670);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        icono = cargarImagen("/images/icon.png");
        if (icono != null) {
            setIconImage(icono.getImage());
        }
    }

    private void initializeComponents() {
        imagenFondo = cargarImagen("/images/logo_essence.jpg");
        Color colorLogo = new Color(234, 248, 225);

        cardLayout = new CardLayout();
        panelPrincipal = new JPanel(cardLayout);
        panelPrincipal.setBackground(colorLogo);
        setContentPane(panelPrincipal);

        // --- PANTALLA 1: EL HOME (Con tu logo centrado) ---
        JPanel panelHome = new JPanel(new GridBagLayout());
        panelHome.setBackground(colorLogo);

        JLabel lblLogo = crearLabelFondo();
        if (lblLogo != null) {
            lblLogo.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
            panelHome.add(lblLogo);
        }

        // --- PANTALLA 2: CONSULTA ---
        ConsultarPerfumeUI consultarPerfumeUI = new ConsultarPerfumeUI(cardLayout, panelPrincipal);

        // Agregamos las "cartas" al mazo con un nombre clave
        panelPrincipal.add(panelHome, "HOME");
        panelPrincipal.add(consultarPerfumeUI, "CONSULTA");

        // Fuentes
        Font fontMenu = new Font("Segoe UI", Font.PLAIN, 12);
        Font fontMenuBold = new Font("Segoe UI", Font.BOLD, 13);

        // Barra de menú
        menuBar = new JMenuBar();
        menuBar.setBackground(Color.WHITE);
        menuBar.setMargin(new Insets(5, 10, 5, 10));
        menuBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(200, 200, 200)));

        menuArchivo = new JMenu("Archivo");
        menuInventario = new JMenu("Inventario");
        menuVentas = new JMenu("Ventas");
        menuConfig = new JMenu("Configuración");
        menuReportes = new JMenu("Reportes");
        menuAyuda = new JMenu("Ayuda");

        menuArchivo.setFont(fontMenuBold);
        menuInventario.setFont(fontMenuBold);
        menuVentas.setFont(fontMenuBold);
        menuConfig.setFont(fontMenuBold);
        menuReportes.setFont(fontMenuBold);
        menuAyuda.setFont(fontMenuBold);

        itemSalir = new JMenuItem("Salir");
        itemConsultar = new JMenuItem("Consultar Perfume");
        itemAgregar = new JMenuItem("Agregar Perfume");
        itemModificar = new JMenuItem("Modificar Perfume");
        itemListar = new JMenuItem("Listar Perfume");
        itemNuevaVenta = new JMenuItem("Nueva Venta");
        itemHistorialVentas = new JMenuItem("Historial de Ventas");
        itemDescuentos = new JMenuItem("Configuración Descuentos");
        itemObsequios = new JMenuItem("Configuración Obsequios");
        itemResumenVentas = new JMenuItem("Resumen Ventas");
        itemTopProductos = new JMenuItem("Top Productos");
        itemAcercaDe = new JMenuItem("Acerca de");

        itemSalir.setFont(fontMenu);
        itemConsultar.setFont(fontMenu);
        itemAgregar.setFont(fontMenu);
        itemModificar.setFont(fontMenu);
        itemListar.setFont(fontMenu);
        itemNuevaVenta.setFont(fontMenu);
        itemHistorialVentas.setFont(fontMenu);
        itemDescuentos.setFont(fontMenu);
        itemObsequios.setFont(fontMenu);
        itemResumenVentas.setFont(fontMenu);
        itemTopProductos.setFont(fontMenu);
        itemAcercaDe.setFont(fontMenu);
    }

    private void configureLayout() {
        menuArchivo.add(itemSalir);
        menuInventario.add(itemConsultar);
        menuInventario.add(itemAgregar);
        menuInventario.add(itemModificar);
        menuInventario.add(itemListar);
        menuVentas.add(itemNuevaVenta);
        menuVentas.add(itemHistorialVentas);
        menuConfig.add(itemDescuentos);
        menuConfig.add(itemObsequios);
        menuReportes.add(itemTopProductos);
        menuReportes.add(itemResumenVentas);
        menuAyuda.add(itemAcercaDe);

        menuBar.add(menuArchivo);
        menuBar.add(menuInventario);
        menuBar.add(menuVentas);
        menuBar.add(menuConfig);
        menuBar.add(menuReportes);
        menuBar.add(menuAyuda);

        JMenu[] menus = {menuArchivo, menuInventario, menuVentas, menuConfig, menuReportes, menuAyuda};

        for (JMenu m : menus) {
            m.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
            for (Component c : m.getMenuComponents()) {
                if (c instanceof JMenuItem) {
                    JMenuItem item = (JMenuItem) c;
                    String textoOriginal = item.getText();

                    item.setText("");
                    item.setLayout(new BorderLayout());

                    JLabel labelFalsa = new JLabel(textoOriginal);
                    labelFalsa.setFont(item.getFont());
                    labelFalsa.setBorder(BorderFactory.createEmptyBorder(7,7,7,7));

                    int anchoTexto = labelFalsa.getPreferredSize().width + 35;
                    item.setPreferredSize(new Dimension(anchoTexto, 25));

                    item.add(labelFalsa, BorderLayout.WEST);
                }
            }
        }

        setJMenuBar(menuBar);
    }

    private JLabel crearLabelFondo() {
        if (imagenFondo == null || imagenFondo.getIconWidth() <= 0) {
            return null;
        }

        JLabel lbl = new JLabel(imagenFondo);

        return lbl;
    }

    private ImageIcon cargarImagen(String ruta) {
        java.net.URL imgURL = getClass().getResource(ruta);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("ERROR: No se encontró el recurso en la ruta: " + ruta);
            return null;
        }
    }

    private void addEventListeners() {
        itemConsultar.addActionListener(e -> cardLayout.show(panelPrincipal, "CONSULTA"));

        itemSalir.addActionListener(e -> {
            int resp = JOptionPane.showConfirmDialog(this, "¿Desea cerrar Essence Fragrance?", "Salir", JOptionPane.YES_NO_OPTION);
            if(resp == JOptionPane.YES_OPTION) System.exit(0);
        });
    }

}
