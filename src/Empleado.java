
public abstract class Empleado implements Cloneable {
    protected String nombre;
    protected String codigo;
    protected double salarioBase;

    public Empleado(String nombre, String codigo, double salarioBase) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.salarioBase = salarioBase;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código no puede estar vacío.");
        }
        this.codigo = codigo.trim();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(double salarioBase) {
        if (salarioBase < 0) {
            throw new IllegalArgumentException("El salario base no puede ser negativo.");
        }
        this.salarioBase = salarioBase;
    }
    public abstract double calcularSalario();

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone(); 
    }
    public String obtenerCategoria(){
        double salario=calcularSalario();
        if (salario<10000){
            return "Rango I";
        }
        else if (salario<=25000){
            return "Rango II";
        }
        else{
            return "Rango III";
        }
        
    }
    
    
    public String mostrarInformacion(){
        return "Codigo: "+codigo+"  Nombre: "+nombre+"  Salario :"+calcularSalario();
                
    }
}
