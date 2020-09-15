package principal;

import controlador.ControladorProducto;
import modelo.ConsultasProducto;
import modelo.ProductoCategoria;
import vista.VistaProducto;

public class MainClass {

	public static void main(String[] args) {
		
		VistaProducto vista = new VistaProducto ();
		ProductoCategoria producto = new ProductoCategoria();
		ConsultasProducto modelo = new ConsultasProducto ();

		ControladorProducto controlador = new ControladorProducto(vista, producto, modelo);
		
		controlador.iniciar();
	    vista.setVisible(true);
	}
}