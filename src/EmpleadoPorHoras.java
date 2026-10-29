
public class EmpleadoPorHoras extends Empleado{
    private int horasTrabajadas;
    private double tarifaPorHora;

    public EmpleadoPorHoras(int horasTrabajadas, double tarifaPorHora, String nombre, String codigo, double salarioBase) {
        super(nombre, codigo, salarioBase);
        setHorasTrabajadas(horasTrabajadas);
        setTarifaPorHora(tarifaPorHora);
    }
    public int getHorasTrabajadas() {
        return horasTrabajadas;
    }

    public void setHorasTrabajadas(int horasTrabajadas) {
        if (horasTrabajadas < 0) {
            throw new IllegalArgumentException("Las horas trabajadas no pueden ser negativas.");
        }
        this.horasTrabajadas = horasTrabajadas;
    }

    public double getTarifaPorHora() {
        return tarifaPorHora;
    }

    public void setTarifaPorHora(double tarifaPorHora) {
        if (tarifaPorHora < 0) {
            throw new IllegalArgumentException("La tarifa por hora no puede ser negativa.");
        }
        this.tarifaPorHora = tarifaPorHora;
    }
    @Override
    public double calcularSalario() {
        return salarioBase+(horasTrabajadas*tarifaPorHora);
    }
    
}
