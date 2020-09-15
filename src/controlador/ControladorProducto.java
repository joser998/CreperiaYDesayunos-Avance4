package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.Date;
import javax.swing.JOptionPane;
import modelo.ConsultasProducto;
import modelo.ProductoCategoria;
import vista.VistaProducto;

public class ControladorProducto implements ActionListener {
	private VistaProducto vista;
	private ProductoCategoria producto;
	private ConsultasProducto modelo;

	public ControladorProducto(VistaProducto vista, ProductoCategoria producto, ConsultasProducto modelo) {
		this.vista = vista;
		this.producto = producto;
		this.modelo = modelo;

		vista.btnRegistrar.addActionListener(this);
		vista.btnBuscar.addActionListener(this);
		vista.btnLimpiar.addActionListener(this);
		vista.btnModificar.addActionListener(this);
		vista.btnEliminar.addActionListener(this);

		/* Categoria */
		vista.btnGuardarCategoria.addActionListener(this);
		vista.btnBuscarPorCodigoCategoria.addActionListener(this);
		vista.btnModificarCategoria.addActionListener(this);
		vista.btnEliminarPorCategoria.addActionListener(this);
		
		/* Ventas */
		vista.btnComprobarDisponibilidad.addActionListener(this);
		vista.btnVender.addActionListener(this);
	}

	public void iniciar() {
		vista.setTitle("CREPERIA LUISITO GUADALAJARA");
		vista.setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(ActionEvent botonAcciones) {

		if (botonAcciones.getSource() == vista.btnRegistrar) {

			if (String.valueOf(vista.txtPlatillo.getText()).compareTo("") == 0
					|| String.valueOf(vista.txtCodigo.getText()).compareTo("") == 0
					|| String.valueOf(vista.txtPrecio.getText()).compareTo("") == 0
					|| String.valueOf(vista.comboBoxCantidad.getSelectedItem().toString()).compareTo("") == 0
					|| String.valueOf(vista.textAreaIngredientes.getText()).compareTo("") == 0
					|| String.valueOf(vista.txtidCategoria.getText()).compareTo("") == 0) {

				JOptionPane.showMessageDialog(null, "Debes Completar Todos Los Campos", "Warning",
						JOptionPane.WARNING_MESSAGE);

			} else {

				// Primero busca si ya hay algun registro igual
				producto.setCodigo(vista.txtCodigo.getText());

				if (modelo.buscar(producto) == true) {
					JOptionPane.showMessageDialog(null, "Este Codigo Ya Existe", "Warning",
							JOptionPane.WARNING_MESSAGE);
					limpiarCajas();
				}

				if (modelo.buscar(producto) != true) {

					producto.setIdCategoria(Integer.parseInt(vista.txtidCategoria.getText()));

					if (modelo.buscarCategoriaPorId(producto) != true) {
						JOptionPane.showMessageDialog(null,
								"id Categoria debe corresponder con un idCategoria de Tabla Categoria Para Poder Pertenecer",
								"Warning", JOptionPane.WARNING_MESSAGE);
						limpiarCajas();
					}

					if (modelo.buscarCategoriaPorId(producto) == true) {

						producto.setPlatillo(vista.txtPlatillo.getText());
						producto.setCodigo(vista.txtCodigo.getText());
						producto.setPrecio(vista.txtPrecio.getText());
						producto.setCantidad(vista.comboBoxCantidad.getSelectedItem().toString());
						producto.setIngredientes(vista.textAreaIngredientes.getText());
						producto.setCodigoCategoria(Integer.parseInt(vista.txtidCategoria.getText()));

						if (modelo.insertar(producto) == true) {
							JOptionPane.showMessageDialog(null, "Registro Insertado Correctamente.");
							limpiarCajas();
							vista.lblPerfilPlatillo.setVisible(true);
							vista.btnLimpiar.setVisible(true);
							vista.textAreaPerfilPlatillo.setVisible(true);
							vista.textAreaPerfilPlatillo.setText("PLATILLO: " + producto.getPlatillo()
									+ "\n\nREGISTRO: " + producto.getCodigo() + "\n\nPRECIO: " + producto.getPrecio()
									+ "\n\nCANTIDAD: " + producto.getCantidad() + "\n\nIDCATEGORIA: "
									+ producto.getCodigoCategoria() + "\n\nINGREDIENTES: "
									+ producto.getIngredientes());

						} else {
							JOptionPane.showMessageDialog(null, "Error al insertar registro");
							limpiarCajas();
						}
					}
				}
			}
		}

		
		
		if(botonAcciones.getSource() == vista.btnComprobarDisponibilidad) {
			
			if(String.valueOf(vista.txtCodigoVentas.getText()).compareTo("") == 0) {
				JOptionPane.showMessageDialog(null, "Debes Indicar Un Registro.");
			}else {
				
				producto.setCodigo(vista.txtCodigoVentas.getText());
				
				if (modelo.buscar(producto) == true) {
					JOptionPane.showMessageDialog(null, "Platillo Encontrado.");
					limpiarCajas();
					vista.textAreaPerfilPlatillo.setVisible(true);
					vista.lblPerfilPlatillo.setVisible(true);
					vista.btnVender.setVisible(true);
					vista.btnLimpiar.setVisible(true);
					
					//Mostrar el perfil encontrado etiquetas
					vista.textAreaPerfilPlatillo.setText("IdPRODUCTO: " + String.valueOf(producto.getIdProducto())
					+ "\n\nPLATILLO: " + producto.getPlatillo() + "\n\nREGISTRO: " + producto.getCodigo()
					+ "\n\nPRECIO: " + producto.getPrecio() + "\n\nCANTIDAD: " + producto.getCantidad()
					+ "\n\nIdCATEGORIA: " + producto.getCodigoCategoria() + "\n\nINGREDIENTES: \n"
					+ producto.getIngredientes());
				
					
					
					
					
				}else {
					JOptionPane.showMessageDialog(null, "No Se Encontro Ese Platillo.");
					limpiarCajas();
				}
			}
		}
		
		
		
		
		
		
		//Boton Vender
		if(botonAcciones.getSource() == vista.btnVender) {
			boolean flag = false;
			
			//Precio Producto que viene DB
			float precioDataBase = Float.parseFloat(producto.getPrecio());
			//Cantidad Producto que viene DB
			float cantidadDataBase = Float.parseFloat(producto.getCantidad());
			
			do {
			flag = false;
			String reciboCantPlatillos = JOptionPane.showInputDialog("¿Cuantos Platillos Necesitas?");
			//Conversion a flotante de la cantidad de platillos
			int cantidadPlatillos = Integer.parseInt(reciboCantPlatillos); 
			
			if(cantidadPlatillos > cantidadDataBase || cantidadPlatillos < 1) {
				JOptionPane.showMessageDialog(null, "Debe Ser Una Cantidad Valida");
				flag = true;
			}else {
				String reciboPago = JOptionPane.showInputDialog("El Costo Platillo Es: "+precioDataBase
						+"\nA Pagar Es: "+(precioDataBase * cantidadPlatillos)
							+"\nRecibo: ");
				
				float pago = Float.parseFloat(reciboPago); //Aqui tenemos la variable del pago tal cual del usuario
				
				
				if(pago < (precioDataBase * cantidadPlatillos)) {
					JOptionPane.showMessageDialog(null,
							"El Pago Debe Ser: "+(precioDataBase * cantidadPlatillos),"Warning", JOptionPane.WARNING_MESSAGE);
							flag = true; 
				}
				
				
				//Si el usuario paga de mas o es exacta la paga
				if(pago > (precioDataBase * cantidadPlatillos) || pago == (precioDataBase * cantidadPlatillos)) {
					float cambio = (precioDataBase * cantidadPlatillos) - pago;
					cambio = cambio * -1;
					JOptionPane.showMessageDialog(null, "Cambio: "+ cambio);
					JOptionPane.showMessageDialog(null, "Venta Completa!");
					float pagoGuardar = (precioDataBase * cantidadPlatillos);	
					
					
					String pagoDefinitivo = String.valueOf(pagoGuardar);
					String cantidadPlatillosDefinitiva = String.valueOf(cantidadPlatillos);
					
					//Variables que nos retornar a las nuevas cajas para poder tomar sus valores
					vista.txtidProductoVentas.setText(String.valueOf(producto.getIdProducto()));
					vista.txtPlatilloVentas.setText(producto.getPlatillo());
					vista.txtCodigoVentasValor.setText(producto.getCodigo());
					vista.txtPagoVentas.setText(pagoDefinitivo);
					vista.txtCantidadaVentas.setText(cantidadPlatillosDefinitiva);
					vista.txtidCategoriaVentas.setText(String.valueOf(producto.getCodigoCategoria()));
						
						
					//Intento insertar ********************************************************
					producto.setPlatilloVenta(vista.txtPlatilloVentas.getText());	
					producto.setCodigoVenta(vista.txtCodigoVentasValor.getText());
					producto.setPagoVenta(vista.txtPagoVentas.getText());		
					producto.setCantidadVenta(vista.txtCantidadaVentas.getText());		
					producto.setIdCategoriaVenta(vista.txtidCategoriaVentas.getText());		
					// ***************************************************************************		
					
					
					/* resCantidadDB es lo que sobra realmente en flotante para la primer tabla producto*/
					float resCantidadDB = cantidadPlatillos - cantidadDataBase;
					//Si se venden todos los productos se hace operacion para eliminarse el producto de tabla producto
					if(resCantidadDB == 0) {
						JOptionPane.showMessageDialog(null, resCantidadDB); //si el res es 0 da 0 correcto // se debe eliminar este registro pues da 0
						//Ya que es 0 en la base de datos de producto debe borrar ese registro
						producto.setCodigo(vista.txtCodigoVentasValor.getText());
						
						if (modelo.buscar(producto) == true) {
							//Se buscar el producto que se agoto en ventas para posteriormente eliminarse
							JOptionPane.showMessageDialog(null, "Producto a eliminar encontrado...");
							vista.txtidProductoVentas.setText(String.valueOf(producto.getIdProducto()));
							producto.setIdProducto(Integer.parseInt(vista.txtidProductoVentas.getText()));
						
							if (modelo.eliminar(producto) == true) {
								//Este registro que se elimino fue por que se agoto por ventas
								JOptionPane.showMessageDialog(null, "Registro Eliminado Correctamente.");
							}
						}
					}
					
					
					
					
					//Se debe de hacer la resta en cantidad del producto vendido
					if(resCantidadDB != 0) {
						resCantidadDB = resCantidadDB *-1;
						JOptionPane.showMessageDialog(null, "Cantidad restante en DB Producto: "+resCantidadDB);
						
						
						producto.setCodigo(vista.txtCodigoVentasValor.getText());
						
						if (modelo.buscar(producto) == true) {
							JOptionPane.showMessageDialog(null, "Producto a restar encontrado...");
							
							
							//Activamos los txt de la tabla producto para tomar su valor tambien, ya que estos no los tenemos en las nuevas cajas
							vista.textAreaIngredientes.setText(producto.getIngredientes());
							vista.txtPrecio.setText(producto.getPrecio());
							vista.txtidCategoria.setText(String.valueOf(producto.getCodigoCategoria()));
							
							//Convierto el float a string para asignarlo en una caja de texto
							String resCantidadDBString = String.valueOf(resCantidadDB);
							vista.txtCantidadRestante.setText(resCantidadDBString);
							
							//Le damos todas las variables que son las nuevas variables para modificar
							producto.setIdProducto(Integer.parseInt(vista.txtidProductoVentas.getText()));
							producto.setPlatillo(vista.txtPlatilloVentas.getText());
							producto.setCodigo(vista.txtCodigoVentasValor.getText());
							producto.setPrecio(vista.txtPrecio.getText());//** tomando valor viejo
							producto.setCantidad(vista.txtCantidadRestante.getText()); //nueva cantidad establecida
							producto.setIngredientes(vista.textAreaIngredientes.getText()); //** tomando valor viejo
							
							
							if (modelo.modificar(producto) == true) {
								JOptionPane.showMessageDialog(null, "Producto Modificado Correctamente");
							}
						}
					}
					
					
					
					
					if(modelo.insertarVentas(producto) == true) {
						JOptionPane.showMessageDialog(null, "Registro Insertado Correctamente.");
						
					}else{
		                JOptionPane.showMessageDialog(null, "Error al insertar registro");
		            }
						
						
						
						
						
						
						
						
						
					
				}
			}
			}while(flag != false);
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		// Buscar Platillo
		if (botonAcciones.getSource() == vista.btnBuscar) {

			if (String.valueOf(vista.txtCodigo.getText()).compareTo("") == 0) {
				JOptionPane.showMessageDialog(null, "Debes Indicar El Codigo Del Platillo");
			}else{

				producto.setCodigo(vista.txtCodigo.getText());

				if (modelo.buscar(producto) == true) { // ***********************************
					JOptionPane.showMessageDialog(null, "Codigo Encontrado.");
					vista.lblPerfilPlatillo.setVisible(true);
					vista.textAreaPerfilPlatillo.setVisible(true);
					vista.btnLimpiar.setVisible(true);
					vista.btnModificar.setVisible(true);
					vista.textAreaIngredientes.setVisible(true);
					vista.lblIngredientes.setVisible(true);
					vista.txtidCategoria.setVisible(true);
					vista.txtidCategoria.setEditable(false);
					vista.lblidCategoria.setVisible(true);

					vista.textAreaPerfilPlatillo.setText("IdPRODUCTO: " + String.valueOf(producto.getIdProducto())
							+ "\n\nPLATILLO: " + producto.getPlatillo() + "\n\nREGISTRO: " + producto.getCodigo()
							+ "\n\nPRECIO: " + producto.getPrecio() + "\n\nCANTIDAD: " + producto.getCantidad()
							+ "\n\nIdCATEGORIA: " + producto.getCodigoCategoria() + "\n\nINGREDIENTES: \n"
							+ producto.getIngredientes());

					vista.lblPlatillo.setVisible(true);
					vista.txtPlatillo.setVisible(true);
					vista.lblPrecio.setVisible(true);
					vista.txtPrecio.setVisible(true);
					vista.lblCantidad.setVisible(true);
					vista.comboBoxCantidad.setVisible(true);
					vista.btnBuscar.setVisible(false);

					vista.txtPlatillo.setText(producto.getPlatillo());
					vista.txtCodigo.setText(producto.getCodigo());
					vista.txtPrecio.setText(producto.getPrecio());
					vista.comboBoxCantidad.setSelectedIndex(0);
					vista.txtidProducto.setText(String.valueOf(producto.getIdProducto()));
					vista.textAreaIngredientes.setText(producto.getIngredientes());
					vista.txtidCategoria.setText(String.valueOf(producto.getCodigoCategoria()));

				} else {
					JOptionPane.showMessageDialog(null, "No existe ese Platillo.");
					limpiarCajas();
				}
			}
		}

		// Modificar
		if (botonAcciones.getSource() == vista.btnModificar) {

			if (String.valueOf(vista.txtPlatillo.getText()).compareTo("") == 0
					|| String.valueOf(vista.txtCodigo.getText()).compareTo("") == 0
					|| String.valueOf(vista.txtPrecio.getText()).compareTo("") == 0
					|| String.valueOf(vista.comboBoxCantidad.getSelectedItem().toString()).compareTo("") == 0
					|| String.valueOf(vista.textAreaIngredientes.getText()).compareTo("") == 0) {
				// poner mensaje el usuario debe de llenar todos los campos de texto
				JOptionPane.showMessageDialog(null, "Debes Completar Todos Los Campos", "Warning",
						JOptionPane.WARNING_MESSAGE);

			} else {

				producto.setIdProducto(Integer.parseInt(vista.txtidProducto.getText()));
				producto.setPlatillo(vista.txtPlatillo.getText());
				producto.setCodigo(vista.txtCodigo.getText());
				producto.setPrecio(vista.txtPrecio.getText());
				producto.setCantidad(vista.comboBoxCantidad.getSelectedItem().toString());
				producto.setIngredientes(vista.textAreaIngredientes.getText());

				if (modelo.modificar(producto) == true) {
					JOptionPane.showMessageDialog(null, "Registro Modificado Correctamente...");
					limpiarCajas();

					vista.textAreaPerfilPlatillo.setText("idProducto: " + String.valueOf(producto.getIdProducto())
							+ "\n\nPLATILLO: " + producto.getPlatillo() + "\n\nREGISTRO: " + producto.getCodigo()
							+ "\n\nPRECIO: " + producto.getPrecio() + "\n\nCANTIDAD: " + producto.getCantidad()
							+ "\n\nINGREDIENTES: \n" + producto.getIngredientes() + "\n\nIdCATEGORIA: \n"
							+ producto.getCodigoCategoria());

				} else {
					JOptionPane.showMessageDialog(null, "No es posible modificar este Registro");
					limpiarCajas();
				}

			}

		}

		if (botonAcciones.getSource() == vista.btnEliminar) {

			if (String.valueOf(vista.txtCodigo.getText()).compareTo("") == 0) {
				JOptionPane.showMessageDialog(null, "Indica el Codigo a Eliminar", "Warning",
						JOptionPane.WARNING_MESSAGE);
			} else {

				producto.setCodigo(vista.txtCodigo.getText());

				if (modelo.buscar(producto) == true) {
					// Registro encontrado
					// tomas el id de idProducto
					// JOptionPane.showMessageDialog(null, "Registro encontrado");

					int response = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres Eliminar este Registro?",
							"Question", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

					// Condicion del JOptionPane
					if (response == JOptionPane.YES_OPTION) {

						vista.txtidProducto.setText(String.valueOf(producto.getIdProducto()));
						producto.setIdProducto(Integer.parseInt(vista.txtidProducto.getText()));

						if (modelo.eliminar(producto) == true) {
							JOptionPane.showMessageDialog(null, "Registro Eliminado Correctamente.");
							limpiarCajas();
						}
					}

					if (response == JOptionPane.NO_OPTION) {
						limpiarCajas();
					}

				} else {
					JOptionPane.showMessageDialog(null, "Registro No Encontrado.");
					limpiarCajas();
				}

			}

		}

		/* Seccion Categoria */
		if (botonAcciones.getSource() == vista.btnGuardarCategoria) {
			if (String.valueOf(vista.txtCategoria.getText()).compareTo("") == 0
					|| String.valueOf(vista.textAreaDescripcionCategoria.getText()).compareTo("") == 0
					|| String.valueOf(vista.txtRegistroCodigoCategoria.getText()).compareTo("") == 0) {
				JOptionPane.showMessageDialog(null, "Debes Completar Todos Los Campos", "Warning",
						JOptionPane.WARNING_MESSAGE);
			} else {

				producto.setCodigoTablaCategoria(vista.txtRegistroCodigoCategoria.getText());

				if (modelo.buscarCategoria(producto) == true) {
					JOptionPane.showMessageDialog(null, "Este Codigo Ya Existe", "Warning",
							JOptionPane.WARNING_MESSAGE);
					limpiarCajas();
				}

				if (modelo.buscarCategoria(producto) != true) {

					producto.setCategoria(vista.txtCategoria.getText());
					producto.setDescripcion(vista.textAreaDescripcionCategoria.getText());
					producto.setCodigoTablaCategoria(vista.txtRegistroCodigoCategoria.getText());

					if (modelo.insertarCategoria(producto) == true) {
						JOptionPane.showMessageDialog(null, "Registro Insertado Correctamente.");
						limpiarCajas();
						vista.lblPerfilPlatillo.setVisible(true);
						vista.textAreaPerfilPlatillo.setVisible(true);
						vista.btnLimpiar.setVisible(true);

						vista.textAreaPerfilPlatillo.setText("CATEGORIA: " + producto.getCategoria() + "\n\nCODIGO: "
								+ producto.getCodigoTablaCategoria() + "\n\nDESCRIPCION: \n\n"
								+ producto.getDescripcion());
					} else {
						JOptionPane.showMessageDialog(null, "Error al insertar registro");
						limpiarCajas();
					}
				}
			}
		}

		if (botonAcciones.getSource() == vista.btnBuscarPorCodigoCategoria) {
			if (String.valueOf(vista.txtRegistroCodigoCategoria.getText()).compareTo("") == 0) {
				JOptionPane.showMessageDialog(null, "Indica El Codigo A Buscar", "Warning",
						JOptionPane.WARNING_MESSAGE);
			} else {

				producto.setCodigoTablaCategoria(vista.txtRegistroCodigoCategoria.getText());

				if (modelo.buscarCategoria(producto) == true) {
					JOptionPane.showMessageDialog(null, "Codigo Encontrado.");

					vista.lblCategoriaNombre.setVisible(true);
					vista.txtCategoria.setVisible(true);
					vista.lblDescripcion.setVisible(true);
					vista.textAreaDescripcionCategoria.setVisible(true);
					vista.textAreaPerfilPlatillo.setVisible(true);
					vista.btnLimpiar.setVisible(true);

					vista.btnModificarCategoria.setVisible(true);

					vista.textAreaPerfilPlatillo.setText("IdCATEGORIA: " + producto.getIdCategoria()
							+ "\n\nNOMBRE CATEGORIA: " + producto.getCategoria() + "\n\nCODIGO: " + producto.getCodigo()
							+ "\n\nDESCRIPCION: \n\n" + producto.getDescripcion());

					vista.txtCategoria.setText(producto.getCategoria());
					vista.textAreaDescripcionCategoria.setText(producto.getDescripcion());
					vista.txtRegistroCodigoCategoria.setText("");
					vista.txtidCategoriaCategoria.setText(String.valueOf(producto.getIdCategoria()));

				} else {
					JOptionPane.showMessageDialog(null, "No existe esa Categoria.");
					limpiarCajas();
				}

			}

		}

		if (botonAcciones.getSource() == vista.btnModificarCategoria) {
			if (String.valueOf(vista.txtCategoria.getText()).compareTo("") == 0
					|| String.valueOf(vista.txtRegistroCodigoCategoria.getText()).compareTo("") == 0
					|| String.valueOf(vista.textAreaDescripcionCategoria.getText()).compareTo("") == 0) {
				JOptionPane.showMessageDialog(null, "Debes Llenar Todos Los Campos", "Warning",
						JOptionPane.WARNING_MESSAGE);
			} else {
				producto.setIdCategoria(Integer.parseInt(vista.txtidCategoriaCategoria.getText()));
				producto.setCategoria(vista.txtCategoria.getText());
				producto.setCodigoTablaCategoria(vista.txtRegistroCodigoCategoria.getText());
				producto.setDescripcion(vista.textAreaDescripcionCategoria.getText());

				if (modelo.modificarCategoria(producto) == true) {
					JOptionPane.showMessageDialog(null, "Registro Modificado Correctamente...");
					limpiarCajas();

					vista.textAreaPerfilPlatillo.setText("IdCATEGORIA: " + String.valueOf(producto.getIdCategoria())
							+ "\n\nCATEGORIA: " + producto.getCategoria() + "\n\nCODIGO: "
							+ producto.getCodigoTablaCategoria() + "\n\nDESCRIPCION: \n\n" + producto.getDescripcion());
				} else {
					JOptionPane.showMessageDialog(null, "No es posible modificar este Registro");
					limpiarCajas();
				}

			}
		}

		if (botonAcciones.getSource() == vista.btnEliminarPorCategoria) {
			if (String.valueOf(vista.txtCodigoBorrarPorCategoria.getText()).compareTo("") == 0) {
				JOptionPane.showMessageDialog(null, "Indica el id de la Categoria a Eliminar", "Warning",
						JOptionPane.WARNING_MESSAGE);
			} else {
				producto.setCodigoTablaCategoria(vista.txtCodigoBorrarPorCategoria.getText());

				if (modelo.buscarCategoria(producto) == true) {
					int response = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres Eliminar este Registro?",
							"Question", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

					if (response == JOptionPane.YES_OPTION) {
						vista.txtidCategoriaCategoria.setText(String.valueOf(producto.getIdCategoria()));
						producto.setIdCategoria(Integer.parseInt(vista.txtidCategoriaCategoria.getText()));

						if (modelo.eliminarCategoria(producto) == true) {
							JOptionPane.showMessageDialog(null, "Registro Eliminado Correctamente.");
							limpiarCajas();
						}

					}
					if (response == JOptionPane.NO_OPTION) {
						limpiarCajas();
					}

				} else {
					JOptionPane.showMessageDialog(null, "Registro No Encontrado.");
					limpiarCajas();
				}
			}
		}

		if (botonAcciones.getSource() == vista.btnLimpiar) {
			limpiarCajas();
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void limpiarCajas() {
		vista.txtPlatillo.setText("");
		vista.txtCodigo.setText("");
		vista.txtPrecio.setText("");
		vista.comboBoxCantidad.setSelectedIndex(0);
		vista.textAreaIngredientes.setText("");
		vista.textAreaPerfilPlatillo.setText("");
		vista.txtCategoria.setText("");
		vista.txtidCategoria.setText("");
		vista.textAreaDescripcionCategoria.setText("");
		vista.txtRegistroCodigoCategoria.setText("");
		vista.txtCodigoBorrarPorCategoria.setText("");
		vista.txtCodigoVentas.setText("");
	}
}