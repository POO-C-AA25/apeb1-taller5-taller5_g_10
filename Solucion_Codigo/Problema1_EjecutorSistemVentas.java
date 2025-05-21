import java.util.ArrayList;
import java.util.Scanner;

public class Problema1_EjecutorSistemVentas {   
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Producto inventario[];
        double monto;
        String productos[] = {
            "Juego de cuchillos profesionales", "Licuadora de alta potencia 1200W", 
            "Procesador de alimentos multifunción", "Batidora de pedestal 6 velocidades", 
            "Horno eléctrico para repostería", "Freidora de aire digital 7 litros", 
            "Sartén antiadherente de titanio 28 cm", "Set de ollas de acero inoxidable 12 piezas", 
            "Refrigeradora No Frost 400L", "Microondas digital con grill", 
            "Cafetera espresso semiautomática", "Plancha eléctrica para cocina saludable", 
            "Extractora de jugos profesional en frío", "Robot de cocina inteligente (tipo Thermomix)"
        };
        
        System.out.println("GRAN AKI SISTEMA DE VENTAS");
        System.out.println("==========================\n");

        inventario = new Producto[productos.length];
        for (int x = 0; x < inventario.length; x++) {
            String nombProducto = productos[x];
            inventario[x] = new Producto(nombProducto, (int)(Math.random() * (1000 - 100 + 1) + 100),
                    (int)(Math.random() * 10 + 1));
        }

        CarritoDeCompras carrito = new CarritoDeCompras();

        char agregar = 'S';
        while (agregar == 'S') {
            int num = (int)(Math.random() * productos.length);
            String productoElegido = productos[num];
            int cantidadElegida = (int)(Math.random() * 9 + 1);
            carrito.agregarProducto(productoElegido, cantidadElegida, inventario);
            do {
                System.out.print("¿Desea añadir otro producto?: (S/N): ");
                agregar = sc.next().charAt(0);
                if (agregar != 'S' && agregar != 'N') {
                    System.err.println("Error. Debe ingresar S para si o N para no.");
                }
            } while(agregar != 'S' && agregar != 'N');            
        }
        
        carrito.mostrarDetalleCompra();
        carrito.calcularTotal();
        System.out.print("Ingrese la cantidad de dinero con la que desea pagar: ");
        monto = sc.nextDouble();
        carrito.realizarPago(monto, inventario);
    }
}


class CarritoDeCompras {
    public ArrayList<Producto> producto = new ArrayList<>();
    public double totalCompra;
    public double montoPagado;
    public boolean cumpleMonto;
    public double descuento = 0.15;

    public CarritoDeCompras() {
        // Constructor vacío
    }

    public CarritoDeCompras(double totalCompra, double montoPagado, boolean cumpleMonto) {
        this.totalCompra = totalCompra;
        this.montoPagado = montoPagado;
        this.cumpleMonto = cumpleMonto;
    }       

    public void agregarProducto(String nombre, int cantidad, Producto[] inventario) {
        for (Producto prod : inventario) {
            if (prod.nombre.equals(nombre)) {
                if (prod.cantidad >= cantidad) {
                    producto.add(new Producto(prod.nombre, prod.precio, cantidad));
                    System.out.println("Producto agregado al carrito: " + nombre + 
                            "\nCantidad: " + cantidad + " unidad(es).");
                } else {
                    System.out.println("No hay suficiente stock para: " + nombre);
                }
            } 
        }
    }

    public void calcularTotal() {
        this.totalCompra = 0;
        for (int i = 0; i < this.producto.size(); i++) {
            Producto prod = this.producto.get(i);
            this.totalCompra += (prod.precio * prod.cantidad); 
        }
        if (this.totalCompra > 1000) {
            double descuentoAplicado = this.totalCompra * this.descuento;
            this.totalCompra -= descuentoAplicado;
            System.out.printf("Porcentaje de Descuento: %.2f%%%n", (this.descuento * 100));
            System.out.printf("Descuento aplicado: %.2f%n", descuentoAplicado);
        }
        System.out.printf("Total a pagar: %.2f%n", this.totalCompra);        
        }
    
    public void verificarCumpleMonto() {
        if(this.montoPagado >= this.totalCompra) {
            this.cumpleMonto = true;
        } else {
            this.cumpleMonto = false;
        }
    }

    public void realizarPago(double monto, Producto[] inventario) {
        this.montoPagado = monto;
        verificarCumpleMonto();
        if (this.cumpleMonto) {
            System.out.println("Pago aceptado. ¡Gracias por su compra!");
            for (int i = 0; i < this.producto.size(); i++) {
                Producto enCarrito = this.producto.get(i);
                for (Producto prod : inventario) {
                    if (prod.nombre.equals(enCarrito.nombre)) {
                        prod.cantidad -= enCarrito.cantidad;
                    }
                }
            }
        } else {
            double faltante = this.totalCompra - this.montoPagado;
            System.out.println("Pago insuficiente. Faltan: $" + faltante);
        }
        for (Producto enCarrito : this.producto) {
                for (Producto prod : inventario) {
                    if (prod.nombre.equals(enCarrito.nombre)) {
                        prod.cantidad -= enCarrito.cantidad;
                        break;
                    }
                }
            }
    }

    public void mostrarDetalleCompra() {
        System.out.println("\n--- Detalle de la Compra ---");
        for (int i = 0; i < producto.size(); i++) {
            Producto prod = producto.get(i);
            System.out.println("- " + prod.nombre + "\n     Cantidad: " + prod.cantidad + " unidad(es)" + " (" + prod.precio + "$ c/u)");
        }
    }
}

class Producto {

    public String nombre;
    public double precio;
    public int cantidad;

    public Producto(String nombre, double precio, int cantidad) {
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Producto{" + "nombre=" + nombre + 
                ", precio=" + precio + 
                ", cantidad=" + cantidad + '}';
    }    
}