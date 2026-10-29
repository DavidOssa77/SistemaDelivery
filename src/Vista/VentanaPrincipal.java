package Vista;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Modelo.Cliente;
import Modelo.EPedidoEnCurso;
import Modelo.ESinPedido;
import Modelo.Estado;
import Modelo.Pago;
import Modelo.Pedido;
import Modelo.Repartidor;
import Modelo.Restaurante;
import Modelo.Tipo;

public class VentanaPrincipal extends JFrame {
    //clientes
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField txtDocumento;
	private JTextField txtTelefono;
	private JTextField txtDireccion;
	private Restaurante restaurante = new Restaurante("sRestaurant", "Calle 10");
	private DefaultTableModel modeloClientes;
	private JTable tablaClientes;
	private JComboBox<String> cbTipoDoc;
	
	// repartidores
	private JTextField txtNombreRep;
	private JTextField txtDocumentoRep;
	private JComboBox<String> cbTipoDocRep;
	private JComboBox<String> cbVehiculo;
	private JTable tablaRepartidores;
	private DefaultTableModel modeloRepartidores;

	// pedidos
	private JComboBox<String> cbClientePedido;
	private JTextField txtPrecio;
	private JTextField txtDireccionPedido;
	private JComboBox<String> cbTipoPago;
	private JComboBox<String> cbEstado;
	private JTable tablaPedidos;
	private DefaultTableModel modeloPedidos;
	private int contadorPedidos = 1;

	// pagos
	private JTable tablaPagos;
	private DefaultTableModel modeloPagos;
	private JLabel lblTotal;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal frame = new VentanaPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaPrincipal() {
		setTitle("Sistema Delivery");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 780, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		//clientes
		JPanel panel = new JPanel();
		tabbedPane.addTab("Clientes", null, panel, null);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nombre:");
		lblNewLabel.setBounds(20, 20, 100, 20); 
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Tipo doc:");
		lblNewLabel_1.setBounds(20, 50, 100, 20);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Documento:");
		lblNewLabel_2.setBounds(20, 80, 100, 20);
		panel.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Teléfono:");
		lblNewLabel_3.setBounds(20, 110, 100, 20);
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Dirección:");
		lblNewLabel_4.setBounds(20, 140, 100, 20);
		panel.add(lblNewLabel_4);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(130, 20, 180, 20);
		panel.add(txtNombre);
		txtNombre.setColumns(10);
		
		cbTipoDoc = new JComboBox<String>();
		cbTipoDoc.setModel(new DefaultComboBoxModel<String>(new String[] {"CC", "TI", "CE", "Pasaporte"}));
		cbTipoDoc.setBounds(130, 50, 180, 20);
		panel.add(cbTipoDoc);
		
		txtDocumento = new JTextField();
		txtDocumento.setBounds(130, 80, 180, 20);
		panel.add(txtDocumento);
		txtDocumento.setColumns(10);
		
		txtTelefono = new JTextField();
		txtTelefono.setBounds(130, 110, 180, 20);
		panel.add(txtTelefono);
		txtTelefono.setColumns(10);
		
		txtDireccion = new JTextField();
		txtDireccion.setBounds(130, 140, 180, 20);
		panel.add(txtDireccion);
		txtDireccion.setColumns(10);
		
		JButton btnAgregarCliente = new JButton("Agregar Cliente");
		btnAgregarCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    // leo lo que escribio el usuario en cada campo
			    String nombre = txtNombre.getText().trim();
			    String tipoDoc = (String) cbTipoDoc.getSelectedItem();
			    String documento = txtDocumento.getText().trim();
			    String telefono = txtTelefono.getText().trim();
			    String direccion = txtDireccion.getText().trim();

			    // si falta algun dato aviso y no sigo
			    if (nombre.isEmpty() || documento.isEmpty() || telefono.isEmpty() || direccion.isEmpty()) {
			        JOptionPane.showMessageDialog(null, "Llena todos los campos");
			    } else {
			        // el restaurante crea el cliente, la ventana solo le pasa los datos
			        restaurante.addCliente(nombre, tipoDoc, documento, telefono, direccion);
			        actualizarTablaClientes();
			        actualizarComboClientes();
			        limpiarCamposCliente();
			        JOptionPane.showMessageDialog(null, "Cliente agregado");
			    }
			}
		});
		btnAgregarCliente.setBounds(130, 175, 180, 25);
		panel.add(btnAgregarCliente);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(340, 20, 400, 390);
		panel.add(scrollPane);
		
		// columnas de la tabla, empieza sin filas
		modeloClientes = new DefaultTableModel(new Object[][] {},
		        new String[] {"Nombre", "Tipo doc", "Documento", "Telefono", "Direccion"});
		tablaClientes = new JTable(modeloClientes);
		tablaClientes.setDefaultEditor(Object.class, null);     
		scrollPane.setViewportView(tablaClientes);
		
		//repartidores
		JPanel panelRepartidores = new JPanel();
		tabbedPane.addTab("Repartidores", null, panelRepartidores, null);
		panelRepartidores.setLayout(null);

		JLabel lblNombreRep = new JLabel("Nombre:");
		lblNombreRep.setBounds(20, 20, 100, 20);
		panelRepartidores.add(lblNombreRep);
		txtNombreRep = new JTextField();
		txtNombreRep.setBounds(130, 20, 180, 20);
		panelRepartidores.add(txtNombreRep);

		JLabel lblTipoDocRep = new JLabel("Tipo doc:");
		lblTipoDocRep.setBounds(20, 50, 100, 20);
		panelRepartidores.add(lblTipoDocRep);
		cbTipoDocRep = new JComboBox<String>();
		cbTipoDocRep.setModel(new DefaultComboBoxModel<String>(new String[] {"CC", "TI", "CE", "Pasaporte"}));
		cbTipoDocRep.setBounds(130, 50, 180, 20);
		panelRepartidores.add(cbTipoDocRep);

		JLabel lblDocumentoRep = new JLabel("Documento:");
		lblDocumentoRep.setBounds(20, 80, 100, 20);
		panelRepartidores.add(lblDocumentoRep);
		txtDocumentoRep = new JTextField();
		txtDocumentoRep.setBounds(130, 80, 180, 20);
		panelRepartidores.add(txtDocumentoRep);

		JLabel lblVehiculo = new JLabel("Vehículo:");
		lblVehiculo.setBounds(20, 110, 100, 20);
		panelRepartidores.add(lblVehiculo);
		cbVehiculo = new JComboBox<String>();
		cbVehiculo.setModel(new DefaultComboBoxModel<String>(new String[] {"Moto", "Bicicleta", "Carro"}));
		cbVehiculo.setBounds(130, 110, 180, 20);
		panelRepartidores.add(cbVehiculo);

		JButton btnAgregarRepartidor = new JButton("Agregar repartidor");
		btnAgregarRepartidor.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        // leo los datos del formulario
		        String nombre = txtNombreRep.getText().trim();
		        String tipoDoc = (String) cbTipoDocRep.getSelectedItem();
		        String documento = txtDocumentoRep.getText().trim();
		        String vehiculo = (String) cbVehiculo.getSelectedItem();

		        if (nombre.isEmpty() || documento.isEmpty()) {
		            JOptionPane.showMessageDialog(null, "Llena todos los campos");
		        } else {
		            // el restaurante lo guarda en la cola y en la lista completa
		            restaurante.addRepartidor(nombre, tipoDoc, documento, vehiculo);
		            actualizarTablaRepartidores();
		            txtNombreRep.setText("");
		            txtDocumentoRep.setText("");
		            JOptionPane.showMessageDialog(null, "Repartidor agregado");
		        }
		    }
		});
		btnAgregarRepartidor.setBounds(130, 145, 180, 25);
		panelRepartidores.add(btnAgregarRepartidor);

		JScrollPane scrollRepartidores = new JScrollPane();
		scrollRepartidores.setBounds(340, 20, 400, 390);
		panelRepartidores.add(scrollRepartidores);
		// columnas de la tabla de repartidores
		modeloRepartidores = new DefaultTableModel(new Object[][] {},
		        new String[] {"Nombre", "Tipo doc", "Documento", "Vehiculo", "Estado"});
		tablaRepartidores = new JTable(modeloRepartidores);
		// la tabla solo muestra, no deja escribir en las celdas
		tablaRepartidores.setDefaultEditor(Object.class, null);
		scrollRepartidores.setViewportView(tablaRepartidores);

		//pedidos
		JPanel panelPedidos = new JPanel();
		tabbedPane.addTab("Pedidos", null, panelPedidos, null);
		panelPedidos.setLayout(null);

		JLabel lblClientePedido = new JLabel("Cliente:");
		lblClientePedido.setBounds(20, 20, 100, 20);
		panelPedidos.add(lblClientePedido);
		cbClientePedido = new JComboBox<String>();
		cbClientePedido.setBounds(130, 20, 180, 20);
		panelPedidos.add(cbClientePedido);

		JLabel lblPrecio = new JLabel("Precio:");
		lblPrecio.setBounds(20, 50, 100, 20);
		panelPedidos.add(lblPrecio);
		txtPrecio = new JTextField();
		txtPrecio.setBounds(130, 50, 180, 20);
		panelPedidos.add(txtPrecio);

		JLabel lblDireccionPedido = new JLabel("Dirección:");
		lblDireccionPedido.setBounds(20, 80, 100, 20);
		panelPedidos.add(lblDireccionPedido);
		txtDireccionPedido = new JTextField();
		txtDireccionPedido.setBounds(130, 80, 180, 20);
		panelPedidos.add(txtDireccionPedido);

		JLabel lblTipoPago = new JLabel("Pago:");
		lblTipoPago.setBounds(20, 110, 100, 20);
		panelPedidos.add(lblTipoPago);
		cbTipoPago = new JComboBox<String>();
		cbTipoPago.setModel(new DefaultComboBoxModel<String>(new String[] {"EFECTIVO", "TARJETA", "TRANSFERENCIA"}));
		cbTipoPago.setBounds(130, 110, 180, 20);
		panelPedidos.add(cbTipoPago);

		JButton btnCrearPedido = new JButton("Crear pedido");
		btnCrearPedido.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        crearPedido();
		    }
		});
		btnCrearPedido.setBounds(130, 145, 180, 25);
		panelPedidos.add(btnCrearPedido);

		JButton btnAsignar = new JButton("Asignar repartidor");
		btnAsignar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        asignarRepartidor();
		    }
		});
		btnAsignar.setBounds(130, 180, 180, 25);
		panelPedidos.add(btnAsignar);

		JSeparator separator = new JSeparator();
		separator.setBounds(20, 225, 290, 2);
		panelPedidos.add(separator);

		JLabel lblCambiarEstado = new JLabel("Pedido seleccionado en la tabla:");
		lblCambiarEstado.setBounds(20, 240, 290, 20);
		panelPedidos.add(lblCambiarEstado);

		JLabel lblEstado = new JLabel("Nuevo estado:");
		lblEstado.setBounds(20, 270, 100, 20);
		panelPedidos.add(lblEstado);
		cbEstado = new JComboBox<String>();
		cbEstado.setModel(new DefaultComboBoxModel<String>(new String[] {"PENDIENTE", "EN_PREPARACION", "ENVIADO", "ENTREGADO", "CANCELADO"}));
		cbEstado.setBounds(130, 270, 180, 20);
		panelPedidos.add(cbEstado);

		JButton btnActualizarEstado = new JButton("Actualizar estado");
		btnActualizarEstado.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        actualizarEstadoPedido();
		    }
		});
		btnActualizarEstado.setBounds(130, 305, 180, 25);
		panelPedidos.add(btnActualizarEstado);

		JScrollPane scrollPedidos = new JScrollPane();
		scrollPedidos.setBounds(340, 20, 400, 390);
		panelPedidos.add(scrollPedidos);
		// columnas de la tabla de pedidos
		modeloPedidos = new DefaultTableModel(new Object[][] {},
		        new String[] {"ID", "Cliente", "Precio", "Pago", "Estado", "Repartidor"});
		tablaPedidos = new JTable(modeloPedidos);
		tablaPedidos.setDefaultEditor(Object.class, null);
		// solo se puede seleccionar un pedido a la vez
		tablaPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scrollPedidos.setViewportView(tablaPedidos);

		//pagos
		JPanel panelPagos = new JPanel();
		tabbedPane.addTab("Pagos", null, panelPagos, null);
		panelPagos.setLayout(null);

		JScrollPane scrollPagos = new JScrollPane();
		scrollPagos.setBounds(20, 20, 720, 350);
		panelPagos.add(scrollPagos);
		// columnas de la tabla de pagos
		modeloPagos = new DefaultTableModel(new Object[][] {},
		        new String[] {"Valor", "Tipo", "Fecha"});
		tablaPagos = new JTable(modeloPagos);
		tablaPagos.setDefaultEditor(Object.class, null);
		scrollPagos.setViewportView(tablaPagos);

		lblTotal = new JLabel("Total recaudado: $0");
		lblTotal.setBounds(20, 380, 300, 20);
		panelPagos.add(lblTotal);

	}
	// borra la tabla y la vuelve a llenar con los clientes del restaurante
	private void actualizarTablaClientes() {
	    modeloClientes.setRowCount(0);
	    for (Cliente c : restaurante.getClientes()) {
	        modeloClientes.addRow(new Object[] {c.getNombre(), c.getTipoDocumento(),
	                c.getDocumento(), c.getTelefono(), c.getDireccion()});
	    }
	}

	// deja los campos vacios para el siguiente cliente
	private void limpiarCamposCliente() {
	    txtNombre.setText("");
	    txtDocumento.setText("");
	    txtTelefono.setText("");
	    txtDireccion.setText("");
	    cbTipoDoc.setSelectedIndex(0);
	}
	// recorre todos los repartidores, no solo los libres de la cola
	private void actualizarTablaRepartidores() {
	    modeloRepartidores.setRowCount(0);
	    for (Repartidor r : restaurante.getTodosRepartidores()) {
	        String estado = r.estaLibre() ? "Libre" : "Ocupado";
	        modeloRepartidores.addRow(new Object[] {r.getNombre(), r.getTipoDocumento(),
	                r.getDocumento(), r.getVehiculo(), estado});
	    }
	}

	// llena el combo de la pestana pedidos con los clientes, en el mismo orden de la lista
	private void actualizarComboClientes() {
	    cbClientePedido.removeAllItems();
	    for (Cliente c : restaurante.getClientes()) {
	        cbClientePedido.addItem(c.getNombre() + " - " + c.getDocumento());
	    }
	}

	private void actualizarTablaPedidos() {
	    modeloPedidos.setRowCount(0);
	    for (Pedido p : restaurante.getHistorialPedidos()) {
	        String rep = (p.getRepartidor() == null) ? "sin asignar" : p.getRepartidor().getNombre();
	        modeloPedidos.addRow(new Object[] {p.getId(), p.getCliente().getNombre(), p.getPrecio(),
	                p.getPago().getTipo(), p.getEstado(), rep});
	    }
	}

	private void actualizarTablaPagos() {
	    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	    double total = 0;
	    modeloPagos.setRowCount(0);
	    for (Pago p : restaurante.getHistorialPagos()) {
	        modeloPagos.addRow(new Object[] {p.getValor(), p.getTipo(), p.getFecha().format(formato)});
	        total = total + p.getValor();
	    }
	    lblTotal.setText("Total recaudado: $" + total);
	}

	// despues de cualquier cambio en pedidos, actualizo todo lo que depende de ellos
	private void actualizarTodo() {
	    actualizarTablaPedidos();
	    actualizarTablaRepartidores();
	    actualizarTablaPagos();
	}

	private void crearPedido() {
	    int posCliente = cbClientePedido.getSelectedIndex();
	    String textoPrecio = txtPrecio.getText().trim();
	    if (posCliente == -1) {
	        JOptionPane.showMessageDialog(null, "Primero agrega un cliente");
	    } else if (textoPrecio.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Escribe el precio");
	    } else {
	        try {
	            double precio = Double.parseDouble(textoPrecio);
	            // el combo y la lista de clientes estan en el mismo orden
	            Cliente cliente = restaurante.getClientes().get(posCliente);
	            String direccion = txtDireccionPedido.getText().trim();
	            // si no escriben direccion uso la del cliente
	            if (direccion.isEmpty()) {
	                direccion = cliente.getDireccion();
	            }
	            Tipo tipo = Tipo.valueOf((String) cbTipoPago.getSelectedItem());
	            Pago pago = restaurante.addPago(precio, tipo);
	            try {
	                restaurante.addPedido("P" + contadorPedidos, LocalDateTime.now(), precio, direccion, pago, cliente);
	                contadorPedidos++;
	                txtPrecio.setText("");
	                txtDireccionPedido.setText("");
	                JOptionPane.showMessageDialog(null, "Pedido creado");
	            } catch (EPedidoEnCurso ex) {
	                // el pedido no se creo, entonces quito el pago que ya se habia registrado
	                restaurante.getHistorialPagos().remove(pago);
	                JOptionPane.showMessageDialog(null, ex.getMessage());
	            }
	            actualizarTodo();
	        } catch (NumberFormatException ex) {
	            JOptionPane.showMessageDialog(null, "El precio debe ser un numero, por ejemplo 25000");
	        }
	    }
	}

	private void asignarRepartidor() {
	    // asignarRepartidor no dice si pudo, entonces miro cuantos libres habia antes y despues
	    int libresAntes = restaurante.getRepartidores().size();
	    restaurante.asignarRepartidor();
	    int libresDespues = restaurante.getRepartidores().size();
	    if (libresDespues < libresAntes) {
	        JOptionPane.showMessageDialog(null, "Repartidor asignado");
	    } else {
	        JOptionPane.showMessageDialog(null, "No se pudo asignar: no hay repartidores libres o pedidos en espera");
	    }
	    actualizarTodo();
	}

	private void actualizarEstadoPedido() {
	    int fila = tablaPedidos.getSelectedRow();
	    if (fila == -1) {
	        JOptionPane.showMessageDialog(null, "Selecciona un pedido en la tabla");
	    } else {
	        // la tabla y el historial estan en el mismo orden
	        Pedido pedido = restaurante.getHistorialPedidos().get(fila);
	        Estado nuevo = Estado.valueOf((String) cbEstado.getSelectedItem());
	        try {
	            restaurante.actualizarPedido(pedido, nuevo);
	            JOptionPane.showMessageDialog(null, "Estado actualizado");
	        } catch (ESinPedido ex) {
	            JOptionPane.showMessageDialog(null, ex.getMessage());
	        }
	        actualizarTodo();
	    }
	}
}
