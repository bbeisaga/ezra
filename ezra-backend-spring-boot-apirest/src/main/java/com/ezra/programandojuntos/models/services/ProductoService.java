package com.ezra.programandojuntos.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ezra.programandojuntos.models.entity.Categoria;
import com.ezra.programandojuntos.models.entity.Color;
import com.ezra.programandojuntos.models.entity.EstadoProducto;
import com.ezra.programandojuntos.models.entity.ItemPedido;
import com.ezra.programandojuntos.models.entity.Material;
import com.ezra.programandojuntos.models.entity.Producto;
import com.ezra.programandojuntos.models.entity.Uso;

public interface ProductoService {

	public Producto findProductoById(Long id);

	public Producto findProductoByCod(String codigo);
	
	public List<Producto> lstProductoUsoServcioEnvio();

	public List<Producto> findProductoByNombre(String term);

	public List<Producto> findAllProductos();

	public Page<Producto> findAllProductoPageable(String query, Pageable pageRequest);

	public List<Color> findAllColores();

	public List<Material> findAllMateriales();

	public List<Categoria> findAllCategoriass();

	public List<Uso> findAllUsos();

	public List<EstadoProducto> findAllEstadoProducto();

	public Producto crear(Producto producto);

	// public Producto crearConImagen(Producto producto, MultipartFile archivo,
	// boolean clienteOnline) throws IOException ;

	public Producto actualizar(Producto producto, Long id);

	// public Producto actualizarConImagen(Producto producto, MultipartFile archivo,
	// Long id, boolean clienteOnline) throws IOException;

	public Producto actualizarExistenciasEstado(Producto producto, ItemPedido item, Long tipoPedidoId);

	public Producto actualizarCostoPrecio(Producto producto, ItemPedido item);

	public Producto guardar(Producto producto);

	public List<Producto> findProductByCategory(Long categoriaId);

}
