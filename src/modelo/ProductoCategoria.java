package modelo;

public class ProductoCategoria {
	/* Atributos Tabla Producto */
	private int idProducto;
	private String platillo;
	private String codigo;
	private String precio;
	private String cantidad;
	private String ingredientes;
	private int codigoCategoria;
	/* ************************* */
	
	
	
	/* Getters and Setters Tabla Producto */
	public int getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(int idProducto) {
		this.idProducto = idProducto;
	}
	public String getPlatillo() {
		return platillo;
	}
	public void setPlatillo(String platillo) {
		this.platillo = platillo;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getPrecio() {
		return precio;
	}
	public void setPrecio(String precio) {
		this.precio = precio;
	}
	public String getCantidad() {
		return cantidad;
	}
	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}
	public String getIngredientes() {
		return ingredientes;
	}
	public void setIngredientes(String ingredientes) {
		this.ingredientes = ingredientes;
	}
	public int getCodigoCategoria() {
		return codigoCategoria;
	}
	public void setCodigoCategoria(int codigoCategoria) {
		this.codigoCategoria = codigoCategoria;
	}
	/* ****************************************************** */
	
	
	
	/* Atributos Tabla Categoria */
	private int idCategoria;
	private String categoria;
	private String descripcion;
	private String codigoTablaCategoria;
	/* ************************* */
	
	
	/* Getters and Setters Tabla Categoria */
	public int getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getCodigoTablaCategoria() {
		return codigoTablaCategoria;
	}
	public void setCodigoTablaCategoria(String codigoTablaCategoria) {
		this.codigoTablaCategoria = codigoTablaCategoria;
	}
	/* ******************************************* */
	
	
	
	
	
	/* Atributos Tabla Ventas */
	private String platilloVenta;
	private String codigoVenta;
	private String pagoVenta;
	private String cantidadVenta;
	private String idCategoriaVenta;


	/* Getters and Setters Tabla Ventas */
	public String getPlatilloVenta() {
		return platilloVenta;
	}
	public void setPlatilloVenta(String platilloVenta) {
		this.platilloVenta = platilloVenta;
	}
	public String getCodigoVenta() {
		return codigoVenta;
	}
	public void setCodigoVenta(String codigoVenta) {
		this.codigoVenta = codigoVenta;
	}
	public String getPagoVenta() {
		return pagoVenta;
	}
	public void setPagoVenta(String pagoVenta) {
		this.pagoVenta = pagoVenta;
	}
	public String getCantidadVenta() {
		return cantidadVenta;
	}
	public void setCantidadVenta(String cantidadVenta) {
		this.cantidadVenta = cantidadVenta;
	}
	public String getIdCategoriaVenta() {
		return idCategoriaVenta;
	}
	public void setIdCategoriaVenta(String idCategoriaVenta) {
		this.idCategoriaVenta = idCategoriaVenta;
	}
	/* *************************************************** */
	
	
}