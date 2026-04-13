package com.essencefragrance.ui;

import com.essencefragrance.service.ConsultarPerfumeService;
import com.essencefragrance.service.ConsultarPerfumeService.DetallePerfume;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ConsultarPerfumeUI extends JPanel {
    // 1. Atributos de Navegación
    private CardLayout cardLayout;
    private JPanel contenedorPrincipal;

    // 2. Atributos de Componentes
    private JLabel lblTitulo, lblNombre, lblFamilia, lblRangoPrecio, lblVistaPrevia, lblImagen;
    private JTextField txtNombre, txtPrecioMin, txtPrecioMax;
    private JComboBox<String> cbFamilia;
    private JButton btnBuscar, btnModificar, btnEliminar, btnExportar, btnCerrar;
    private JTable tablaPerfumes;
    private DefaultTableModel modeloTabla;
    private JScrollPane scrollTabla;
    private JTextArea txtDescripcion;

    private final ConsultarPerfumeService consultarPerfumeService;
    // Colores de la paleta Essence
    private final Color COLOR_FONDO = new Color(234, 248, 225);
    private final Color COLOR_BORDE = new Color(200, 200, 200);

    public ConsultarPerfumeUI(CardLayout cardLayout, JPanel contenedorPrincipal) {
        this.cardLayout = cardLayout;
        this.contenedorPrincipal = contenedorPrincipal;
        this.consultarPerfumeService = new ConsultarPerfumeService();

        initUI();
        addEventListeners();
        cargarDatosTabla();
    }

    private void initUI() {
        configurePanel();
        initializeComponents();
        configureLayout();
    }

    private void configurePanel() {
        setLayout(new BorderLayout(15, 15));
        setBackground(COLOR_FONDO);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    private void initializeComponents() {
        lblTitulo = new JLabel("CONSULTA DE PERFUMES");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(44, 62, 80)); // Gris oscuro azulado para elegancia
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 5, 10, 0));

        lblNombre = new JLabel("Nombre:");
        lblFamilia = new JLabel("Familia:");
        lblRangoPrecio = new JLabel("Rango Precio:");
        lblVistaPrevia = new JLabel("Vista Previa:");

        lblImagen = new JLabel("[ SIN IMAGEN ]", SwingConstants.CENTER);
        lblImagen.setPreferredSize(new Dimension(200, 180));
        lblImagen.setBorder(BorderFactory.createDashedBorder(Color.LIGHT_GRAY));

        txtNombre = new JTextField(12);
        txtPrecioMin = new JTextField(6);
        txtPrecioMax = new JTextField(6);

        cbFamilia = new JComboBox<>(new String[]{"Todas", "Cítrica", "Floral", "Amaderada", "Oriental"});

        // --- TABLA (Centro) ---

        txtDescripcion = new JTextArea(8, 15);
        txtDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setEditable(false);
        txtDescripcion.setText("Seleccione un perfume de la tabla para ver sus detalles.");

        btnBuscar = new JButton("BUSCAR");
        btnModificar = new JButton("MODIFICAR");
        btnEliminar = new JButton("ELIMINAR");
        btnExportar = new JButton("EXPORTAR");
        btnCerrar = new JButton("CERRAR");

        estilizarBoton(btnBuscar, new Color(44, 62, 80));
        estilizarBoton(btnModificar, new Color(70, 130, 180));
        estilizarBoton(btnEliminar, new Color(220, 53, 69));
        estilizarBoton(btnExportar, new Color(40, 167, 69));
        estilizarBoton(btnCerrar, new Color(108, 117, 125));

        modeloTabla = new DefaultTableModel(new String[]{"CÓDIGO", "NOMBRE", "MARCA", "STOCK", "PRECIO"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaPerfumes = new JTable(modeloTabla) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    // Blanco y un verde muy pálido para el efecto cebra
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(240, 248, 235));
                }
                return c;
            }
        };

        tablaPerfumes.setRowHeight(35);
        tablaPerfumes.setShowGrid(false);
        tablaPerfumes.setSelectionBackground(new Color(165, 200, 160));
        tablaPerfumes.setSelectionForeground(Color.BLACK);
        tablaPerfumes.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        tablaPerfumes.getTableHeader().setOpaque(false);
        tablaPerfumes.getTableHeader().setBackground(new Color(60, 80, 60));
        tablaPerfumes.getTableHeader().setForeground(Color.WHITE);
        tablaPerfumes.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tablaPerfumes.getTableHeader().setPreferredSize(new Dimension(0, 40));

        // Centrado total
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        ((DefaultTableCellRenderer) tablaPerfumes.getTableHeader().getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < tablaPerfumes.getColumnCount(); i++) {
            tablaPerfumes.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        scrollTabla = new JScrollPane(tablaPerfumes);
        scrollTabla.getViewport().setBackground(Color.WHITE);
        scrollTabla.setBorder(BorderFactory.createLineBorder(COLOR_BORDE));
    }

    private void configureLayout() {
        // PANEL PRINCIPAL
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 12));
        panelPrincipal.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel panelCabecera = new JPanel(new BorderLayout(0, 10));
        panelCabecera.setOpaque(false);

        panelCabecera.add(lblTitulo, BorderLayout.NORTH);

        // PANEL Filtros
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panelFiltros.setBackground(Color.WHITE);
        panelFiltros.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(COLOR_BORDE), "FILTROS AVANZADOS",
                TitledBorder.LEFT, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 12)));

        panelFiltros.add(lblNombre);
        panelFiltros.add(txtNombre);
        panelFiltros.add(lblFamilia);
        panelFiltros.add(cbFamilia);
        panelFiltros.add(lblRangoPrecio);
        panelFiltros.add(txtPrecioMin);
        panelFiltros.add(new JLabel("-"));
        panelFiltros.add(txtPrecioMax);
        panelFiltros.add(btnBuscar);

        panelCabecera.add(panelFiltros, BorderLayout.CENTER);

        // PANEL ESTE: Vista Previa Lateral
        JPanel panelLateral = new JPanel(new BorderLayout(5, 10));
        panelLateral.setPreferredSize(new Dimension(280, 0));
        panelLateral.setBackground(Color.WHITE);
        panelLateral.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COLOR_BORDE),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        panelLateral.add(new JLabel("VISTA PREVIA", SwingConstants.CENTER), BorderLayout.NORTH);
        panelLateral.add(lblImagen, BorderLayout.CENTER);

        JScrollPane scrollDesc = new JScrollPane(txtDescripcion);
        scrollDesc.setBorder(BorderFactory.createTitledBorder("Descripción"));
        panelLateral.add(scrollDesc, BorderLayout.SOUTH);

        // PANEL CENTRAL
        JPanel panelCentral = new JPanel(new BorderLayout(15, 0));
        panelCentral.setOpaque(false);

        panelCentral.add(scrollTabla, BorderLayout.CENTER);
        panelCentral.add(panelLateral, BorderLayout.EAST);

        // PANEL SUR: Botones con BorderLayout para separarlos
        JPanel panelAcciones = new JPanel(new BorderLayout());
        panelAcciones.setOpaque(false);

        JPanel pIzquierda = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        pIzquierda.setOpaque(false);
        pIzquierda.add(btnModificar);
        pIzquierda.add(btnEliminar);
        pIzquierda.add(btnExportar);

        panelAcciones.add(pIzquierda, BorderLayout.WEST);
        panelAcciones.add(btnCerrar, BorderLayout.EAST);

        // AGREGAR TODO AL PANEL PRINCIPAL
        add(panelCabecera, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);
        add(panelAcciones, BorderLayout.SOUTH);
    }

    private void addEventListeners() {
        btnCerrar.addActionListener(e -> volverHome());
        btnBuscar.addActionListener(e -> buscarFiltros());
        tablaPerfumes.getSelectionModel().addListSelectionListener(e -> seleccionarFilas(e));
    }

    private void estilizarBoton(JButton boton, Color colorFondo) {
        boton.setBackground(colorFondo);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setOpaque(true);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        boton.setPreferredSize(new Dimension(120, 35));
    }

    private void volverHome() {
        txtNombre.setText("");
        txtPrecioMin.setText("");
        txtPrecioMax.setText("");
        modeloTabla.setRowCount(0);
        lblImagen.setText("[ SIN IMAGEN ]");
        txtDescripcion.setText("Seleccione un perfume de la tabla para ver sus detalles.");

        cardLayout.show(contenedorPrincipal, "HOME");
    }

    private void buscarFiltros() {
        try {
            String nombre = txtNombre.getText().trim();
            double precioMin = txtPrecioMin.getText().isEmpty() ? 0 : Double.parseDouble(txtPrecioMin.getText().trim());
            double precioMax = txtPrecioMax.getText().isEmpty() ? Double.MAX_VALUE : Double.parseDouble(txtPrecioMax.getText().trim());

            // Aquí llamarías a tu servicio real de consulta con filtros
            List<Object[]> resultados = consultarPerfumeService.filtrar(nombre, precioMin, precioMax);
            // Luego actualizarías el modelo de la tabla con esos resultados
            modeloTabla.setRowCount(0); // Limpiamos la tabla antes de cargar nuevos datos
            for (Object[] perfume : resultados) {
                modeloTabla.addRow(perfume);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese valores numéricos válidos para el rango de precio.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void seleccionarFilas(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting() && tablaPerfumes.getSelectedRow() != -1) {
            int fila = tablaPerfumes.getSelectedRow();
            String nombre = modeloTabla.getValueAt(fila, 1).toString();
            String precio = modeloTabla.getValueAt(fila, 4).toString();

            // Actualizamos la descripción lateral
            txtDescripcion.setText("Producto: " + nombre + "\n" +
                    "Precio Sugerido: " + precio + "\n" +
                    "Estado: En Stock\n\n" +
                    "Notas: Fragancia premium de larga duración.");

            lblImagen.setText("Imagen de " + nombre);
        }
    }

    private void cargarDatosTabla() {
        modeloTabla.setRowCount(0);
        List<DetallePerfume> lista = consultarPerfumeService.obtenerListaBase();

        for (DetallePerfume item : lista) {
            modeloTabla.addRow(new Object[] {
                    item.getCodigo(),
                    item.getNombre(),
                    item.getMarca(),
                    item.getStock(),
                    "$" + String.format("%.2f", item.getPrecio())
            });
        }
    }
}