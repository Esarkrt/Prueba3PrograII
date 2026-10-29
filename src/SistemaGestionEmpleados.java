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
        
        
    }
    
}
