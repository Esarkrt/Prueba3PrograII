import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
public class SistemaGestionEmpleados extends JFrame {
    private ArrayList<Empleado> listaEmpleados;
    private JTextField txtCodigo, txtNombre, txtSalarioBase, txtExtra1, txtExtra2;
    private JComboBox<String> cmbTipo;
    private JTable tablaEmpleados;
    private DefaultTableModel modeloTabla;
    private JLabel lbl1, lbl2;
    
    public SistemaGestionEmpleados(){
        listaEmpleados=new ArrayList<>();
        setTitle("Sistema de Gestión de Empleados ");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        
        JPanel panelFormulario = new JPanel(new GridLayout(7, 2, 5, 5));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Informacion del Empleado"));

        panelFormulario.add(new JLabel("Tipo de Empleado:"));
        cmbTipo = new JComboBox<>(new String[]{"Administrativo", "Por Horas"});
        panelFormulario.add(cmbTipo);

        panelFormulario.add(new JLabel("Código:"));
        txtCodigo = new JTextField();
        panelFormulario.add(txtCodigo);

        panelFormulario.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Salario Base:"));
        txtSalarioBase = new JTextField();
        panelFormulario.add(txtSalarioBase);
        
        lbl1 = new JLabel("Bonificación:");
        panelFormulario.add(lbl1);
        txtExtra1 = new JTextField();
        panelFormulario.add(txtExtra1);

        lbl2 = new JLabel("Descuento (%):");
        panelFormulario.add(lbl2);
        txtExtra2 = new JTextField();
        panelFormulario.add(txtExtra2);
        cmbTipo.addActionListener(e -> actualizarEtiquetasFormulario());

        add(panelFormulario, BorderLayout.WEST);
        modeloTabla = new DefaultTableModel(new String[]{"Código", "Nombre", "Tipo", "Salario Calculado", "Categoría"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaEmpleados = new JTable(modeloTabla);
        add(new JScrollPane(tablaEmpleados), BorderLayout.CENTER);
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 8));
        
        JButton btnRegistrar = new JButton("Registrar");
        JButton btnBuscar = new JButton("Buscar");
        JButton btnClonar = new JButton("Clonar empleado");
        JButton btnComparar = new JButton("Comparar");
        JButton btnMaxSalario = new JButton("Salario Máximo");
        JButton btnPromedio = new JButton("Promedio");
        JButton btnLimpiar = new JButton("Limpiar");
        
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnClonar);
        panelBotones.add(btnComparar);
        panelBotones.add(btnMaxSalario);
        panelBotones.add(btnPromedio);
        panelBotones.add(btnLimpiar);
        
        add(panelBotones, BorderLayout.SOUTH);
        btnRegistrar.addActionListener(e -> registrarEmpleado());
        btnBuscar.addActionListener(e -> buscarEmpleado());
        btnClonar.addActionListener(e -> clonarEmpleadoSeleccionado());
        btnComparar.addActionListener(e -> comparar());
        btnMaxSalario.addActionListener(e -> mostrarSalarioMaximo());
        btnPromedio.addActionListener(e -> calcularPromedioSalarial());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
    }
    
    private void registrarEmpleado() {
        try {
            String codigo = txtCodigo.getText().trim();
            String nombre = txtNombre.getText().trim();

            if (codigo.isEmpty() || nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El de código y nombre no pueden estar vacíos.", "Error de Validación", JOptionPane.ERROR_MESSAGE);
                return;
            }for (Empleado emp : listaEmpleados) {
                if (emp.getCodigo().equalsIgnoreCase(codigo)) {
                    JOptionPane.showMessageDialog(this, "Ya existe un empleado registrado con el código: " + codigo, "Código exisstente", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            double salarioBase = Double.parseDouble(txtSalarioBase.getText().trim());
            double extra1 = Double.parseDouble(txtExtra1.getText().trim());
            double extra2 = Double.parseDouble(txtExtra2.getText().trim());

            Empleado nuevoEmpleado;
            if (cmbTipo.getSelectedIndex() == 0) {
                nuevoEmpleado = new EmpleadoAdministrativo(extra1, extra2, nombre, codigo, salarioBase);
            } else {
                int horas = (int) extra1;
                nuevoEmpleado = new EmpleadoPorHoras(horas, extra2, nombre, codigo, salarioBase); 
            }
            listaEmpleados.add(nuevoEmpleado);
            actualizarTabla();
            limpiarFormulario();
            JOptionPane.showMessageDialog(this, "Empleado registrado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor verifique que los campos numéricos tengan un formato válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        for (Empleado emp : listaEmpleados) {
            String tipo = (emp instanceof EmpleadoAdministrativo) ? "Administrativo" : "Por Horas";
            Object[] fila = {
                emp.getCodigo(),
                emp.getNombre(),
                tipo,
                String.format("%.2f", emp.calcularSalario()),
                emp.obtenerCategoria()
            };
            modeloTabla.addRow(fila);
        }
    }
    private void buscarEmpleado() {
        String codigoBuscado = JOptionPane.showInputDialog(this, "Ingrese el código del empleado a buscar:");
        if (codigoBuscado == null || codigoBuscado.trim().isEmpty()) return;
        for (Empleado emp : listaEmpleados) {
            if (emp.getCodigo().equalsIgnoreCase(codigoBuscado.trim())) {
                JOptionPane.showMessageDialog(this, "Empleado Encontrado:\n" + emp.mostrarInformacion() + "\nCategoría: " + emp.obtenerCategoria(), "Resultado de Búsqueda", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "No se encontró ningún empleado con el código: " + codigoBuscado, "Búsqueda Fallida", JOptionPane.WARNING_MESSAGE);
    }
    private void clonarEmpleadoSeleccionado() {
        int filaSeleccionada = tablaEmpleados.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un empleado de la tabla para clonar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Empleado original = listaEmpleados.get(filaSeleccionada);
        Empleado clon = original.clone(); 
        String nuevoCodigo = JOptionPane.showInputDialog(this, "El clon requiere un código único. Ingrese nuevo código:", original.getCodigo() + "-CLONE");
        if (nuevoCodigo == null || nuevoCodigo.trim().isEmpty()) return;

        for (Empleado emp : listaEmpleados) {
            if (emp.getCodigo().equalsIgnoreCase(nuevoCodigo.trim())) {
                JOptionPane.showMessageDialog(this, "El código ingresado ya está en uso.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        clon.setCodigo(nuevoCodigo);
        listaEmpleados.add(clon);
        actualizarTabla();
        JOptionPane.showMessageDialog(this, "Empleado clonado y registrado." + nuevoCodigo, "Clonación Exitosa", JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    private void comparar() {
        int[] seleccionadas = tablaEmpleados.getSelectedRows();
        if (seleccionadas.length < 2) {
            JOptionPane.showMessageDialog(this, "Seleccione exactamente dos filas en la tabla (manteniendo Ctrl o Shift) para comparar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Empleado e1 = listaEmpleados.get(seleccionadas[0]);
        Empleado e2 = listaEmpleados.get(seleccionadas[1]);

        boolean mismaReferencia = (e1 == e2);
        boolean mismoCodigo = e1.getCodigo().equalsIgnoreCase(e2.getCodigo());
        boolean mismoSalario = e1.calcularSalario() == e2.calcularSalario();

        String resultado = String.format(
            "Comparación\n" +
            "- ¿Son la misma referencia (e1 == e2)?: %b\n" +
            "- ¿Tienen el mismo código?: %b\n" +
            "- ¿Tienen el mismo salario calculado?: %b",
            mismaReferencia, mismoCodigo, mismoSalario
        );

        JOptionPane.showMessageDialog(this, resultado, "Resultado de Comparación", JOptionPane.INFORMATION_MESSAGE);
    }
    private void mostrarSalarioMaximo() {
        if (listaEmpleados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay registros en el sistema.", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Empleado maxEmp = listaEmpleados.get(0);
        for (Empleado emp : listaEmpleados) {
            if (emp.calcularSalario() > maxEmp.calcularSalario()) {
                maxEmp = emp;
            }
        }

        JOptionPane.showMessageDialog(this, "Empleado con mayor salario:\n" + maxEmp.mostrarInformacion(), "Salario Máximo", JOptionPane.INFORMATION_MESSAGE);
    }
    private void calcularPromedioSalarial() {
        if (listaEmpleados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay registros para calcular el promedio.", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        double suma = 0;
        for (Empleado emp : listaEmpleados) {
            suma += emp.calcularSalario();
        }
        double promedio = suma / listaEmpleados.size();

        JOptionPane.showMessageDialog(this, String.format("El salario promedio de los registros es: %.2f", promedio), "Promedio Salarial", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void limpiarFormulario() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtSalarioBase.setText("");
        txtExtra1.setText("");
        txtExtra2.setText("");
        cmbTipo.setSelectedIndex(0);
        txtCodigo.requestFocus();
    }
    
    
    
    private void actualizarEtiquetasFormulario() {
        if (cmbTipo.getSelectedIndex() == 0) {
            lbl1.setText("Bonificación:");
            lbl2.setText("Descuento (%):");
        } else {
            lbl1.setText("Horas Trabajadas:");
            lbl2.setText("Tarifa por Hora:");
        }
    }
    public static void main(String[]args){
        SwingUtilities.invokeLater(() -> {
            new SistemaGestionEmpleados().setVisible(true);
        });
    }
}
