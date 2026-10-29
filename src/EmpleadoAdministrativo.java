
public class EmpleadoAdministrativo extends Empleado {
    private double bonificacion;
    private double descuento;

    public EmpleadoAdministrativo(double bonificacion, double descuento, String nombre, String codigo, double salarioBase) {
        super(nombre, codigo, salarioBase);
        setBonificacion(bonificacion);
        setDescuento(descuento);
    }

    @Override
    public double calcularSalario() {
        return (salarioBase+bonificacion)*(1-descuento/100);
    }
    public double getBonificacion() {
        return bonificacion;
    }

    public void setBonificacion(double bonificacion) {
        if (bonificacion < 0) {
            throw new IllegalArgumentException("La bonificación no puede ser negativa.");
        }
        this.bonificacion = bonificacion;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        if (descuento < 0 || descuento > 100) {
            throw new IllegalArgumentException("El descuento debe estar entre 0 y 100%.");
        }
        this.descuento = descuento;
    }
}
