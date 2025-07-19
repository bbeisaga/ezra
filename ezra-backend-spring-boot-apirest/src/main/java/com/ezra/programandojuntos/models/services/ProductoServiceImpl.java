package com.ezra.programandojuntos.models.services;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ezra.programandojuntos.dto.ProductoDto;
import com.ezra.programandojuntos.mappers.ProductoMapper;
import com.ezra.programandojuntos.models.dao.IProductoDao;
import com.ezra.programandojuntos.models.dao.ItemDao;
import com.ezra.programandojuntos.models.entity.Categoria;
import com.ezra.programandojuntos.models.entity.Color;
import com.ezra.programandojuntos.models.entity.EstadoProducto;
import com.ezra.programandojuntos.models.entity.ItemPedido;
import com.ezra.programandojuntos.models.entity.MargenProducto;
import com.ezra.programandojuntos.models.entity.Material;
import com.ezra.programandojuntos.models.entity.Producto;
import com.ezra.programandojuntos.models.entity.Uso;
import com.ezra.programandojuntos.enums.EstadoProductoEnum;
import com.ezra.programandojuntos.enums.TipoPedidoEnum;

@Service
public class ProductoServiceImpl implements ProductoService {

//	public static final Long PRODUCTO_NUEVO = 1L;
//	public static final Long PRODUCTO_PROXIMO = 2L;
//	public static final Long PRODUCTO_SE_VA = 3L;
//	public static final Long PRODUCTO_AGOTADO = 4L;

	@Autowired
	private IProductoDao productoDao;

	@Autowired
	private IUploadFileService uploadFileService;

	@Autowired
	private ItemDao itemDao;

//	@Autowired
//	private ProductoMapper productoMapper;

	@Override
	@Transactional(readOnly = true)
	public List<Producto> findProductoByNombre(String term) {
		return productoDao.findByNombreContainingIgnoreCase(term);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Producto> findAllProductos() {
		return (List<Producto>) productoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Producto> findAllProductoPageable(String query, Pageable pageRequest) {

		// log.info("findAllProductoPageable pageRequest= {}",pageRequest);

		return productoDao.findAllProductoPageable(query, pageRequest);
	}

	@Override
	@Transactional(readOnly = true)
	public Producto findProductoById(Long id) {
		return productoDao.findById(id).orElseThrow();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Producto findProductoByCod(String codigo) {
		return productoDao.findByCodigo(codigo);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Producto> lstProductoUsoServcioEnvio() {
		return productoDao.findAllUsoServicioEnvio();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Producto> lstProductoUsoServicios() {
		return productoDao.findAllUsoServicio();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Color> findAllColores() {
		return productoDao.findAllColores();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Material> findAllMateriales() {
		return productoDao.findAllMateriales();
	}

	@Override
	public List<Categoria> findAllCategoriass() {
		return productoDao.findAllCategorias();
	}

	@Override
	public List<Uso> findAllUsos() {
		return productoDao.findAllUsos();
	}

	@Override
	public List<EstadoProducto> findAllEstadoProducto() {
		return productoDao.findAllEstadoProducto();
	}

	@Override
	@Transactional
	public Producto crear(Producto producto) {
		producto.setEstadoProducto(productoDao.findAllEstadoProducto().stream()
				.filter(e -> e.getId() == EstadoProductoEnum.PROXIMO.getValue()).collect(Collectors.toList()).get(0));
		
		return productoDao.save(producto);
	}

//	@Override
//	public Producto crearConImagen(Producto producto, MultipartFile archivo, boolean clienteOnline) throws IOException {
//		if (archivo != null) {
//			String nombreArchivo = uploadFileService.copyFileToPath(archivo, clienteOnline);
//			producto.setImagen(nombreArchivo);
//		}
//		producto.setEstadoProducto(productoDao.findAllEstadoProducto().stream().filter(e -> e.getId() == EstadoProductoEnum.PROXIMO.getValue())
//				.collect(Collectors.toList()).get(0));
//		return productoDao.save(producto);
//	}

//	@Override
//	@Transactional
//	public Producto actualizar(Producto producto, Long id) {
//		Producto productoActual = this.findProductoById(id);
//
//		if (productoActual == null) {
//			return null;
//		}
//		
//		productoActual.setPeso(producto.getPeso());
//		productoActual.setMedidas(producto.getMedidas());
//		productoActual.setColor(producto.getColor());
//		productoActual.setMaterial(producto.getMaterial());
//		productoActual.setCategoria(producto.getCategoria());
//		productoActual.setUso(producto.getUso());		
//		return productoDao.save(producto);
//	}

	@Override
	@Transactional
	public Producto actualizar(Producto producto, Long id) {
		Producto productoActual = this.findProductoById(id);
		if (productoActual == null) {
			return null;
		}

		productoActual.setNombre(producto.getNombre());
		productoActual.setDescripcion(producto.getDescripcion());
		productoActual.setMedidas(producto.getMedidas());
		productoActual.setPeso(producto.getPeso());
		productoActual.setUmbralPocaCantidad(producto.getUmbralPocaCantidad());
		productoActual.setUmbralCantidadAgotada(producto.getUmbralCantidadAgotada());
		productoActual.setCantidadStock(producto.getCantidadStock());
		productoActual.setMinCantidadPedido(producto.getMinCantidadPedido());
		productoActual.setMaxCantidadPedido(producto.getMaxCantidadPedido());
		productoActual.setGruposDe(producto.getGruposDe());
		productoActual.setCostoUnitario(producto.getCostoUnitario());
		// productoActual.setCostoPersonalizacion(producto.getCostoPersonalizacion());
		productoActual.setImpuestoIgv(producto.getImpuestoIgv());
		// productoActual.setPrecioBruto(producto.getPrecioBruto());
		// productoActual.setPrecioBrutoRebajado(producto.getPrecioBrutoRebajado());
		// productoActual.setPrecioNetoRabajado(producto.getPrecioNetoRabajado());
		// productoActual.setFechaPrecioRebajadoDesde(producto.getFechaPrecioRebajadoDesde());
		// productoActual.setFechaPrecioRebajadoHasta(producto.getFechaPrecioRebajadoHasta());
		productoActual.setVisibleEnTienda(producto.isVisibleEnTienda());
		productoActual.setActivo(producto.isActivo());
		productoActual.setColor(producto.getColor());
		productoActual.setMaterial(producto.getMaterial());
		productoActual.setCategoria(producto.getCategoria());
		productoActual.setUso(producto.getUso());
		productoActual.setMargenesProducto(producto.getMargenesProducto());
		// productoActual.setImagen(producto.getImagen());

		if (!producto.getImagen().equalsIgnoreCase("no-imagen.jpg")
				&& !producto.getImagen().equalsIgnoreCase(productoActual.getImagen())) {
			// String nombreArchivo = uploadFileService.copyFileToPath(archivo,
			// clienteOnline);
			if (!productoActual.getImagen().equalsIgnoreCase("no-imagen.jpg")) {
				uploadFileService.eliminar(productoActual.getImagen());
			}
			productoActual.setImagen(producto.getImagen());
		} /*
			 * else { productoActual.setImagen(producto.getImagen()); }
			 */
		return productoDao.save(productoActual);
	}

	@Override
	@Transactional
	public Producto actualizarExistenciasEstado(Producto producto, ItemPedido item, Long tipoPedidoId) {
		if (tipoPedidoId == TipoPedidoEnum.VENTA.getValue()) {
			producto.setCantidadVendidos(producto.getCantidadVendidos() + item.getCantidad());
			producto.setCantidadStock(producto.getCantidadStock() - item.getCantidad());
		}

		if (tipoPedidoId == TipoPedidoEnum.COMPRA.getValue()) {
			producto.setCantidadStock(producto.getCantidadStock() + item.getCantidad());
		}
		// COMPRA
		if (item.getCantidad() > producto.getUmbralPocaCantidad() && producto.getFchUltRealVenta() == null
				&& producto.getFchUltRealCompra() == null) {
			producto.setEstadoProducto(productoDao.findAllEstadoProducto().stream()
					.filter(e -> e.getId() == EstadoProductoEnum.NUEVO.getValue()).collect(Collectors.toList()).get(0));
		}
		// VENTA
		if (item.getCantidad() <= producto.getUmbralCantidadAgotada()) {
			producto.setEstadoProducto(productoDao.findAllEstadoProducto().stream()
					.filter(e -> e.getId() == EstadoProductoEnum.AGOTADO.getValue()).collect(Collectors.toList())
					.get(0));
		}
		// COMPRA Y VENTA
		if (item.getCantidad() > producto.getUmbralCantidadAgotada()
				&& item.getCantidad() < producto.getUmbralPocaCantidad()
				&& (producto.getFchUltRealVenta() != null || producto.getFchUltRealCompra() != null)) {
			producto.setEstadoProducto(productoDao.findAllEstadoProducto().stream()
					.filter(e -> e.getId() == EstadoProductoEnum.POR_AGOTARSE.getValue()).collect(Collectors.toList())
					.get(0));
		}
		// COMPRA Y VENTA
		if (item.getCantidad() > producto.getUmbralPocaCantidad()
				&& (producto.getFchUltRealVenta() != null && producto.getFchUltRealCompra() != null)) {
			producto.setEstadoProducto(productoDao.findAllEstadoProducto().stream()
					.filter(e -> e.getId() == EstadoProductoEnum.EN_STOCK.getValue()).collect(Collectors.toList())
					.get(0));
		}
		// return productoDao.save(producto);
		return producto;

	}

	@Override
	@Transactional
	public Producto actualizarCostoPrecio(Producto producto, ItemPedido item) {
		BigDecimal costoUnitarioItem = item.getImporte().divide(BigDecimal.valueOf(item.getCantidad()));
		item.setCostoUnitarioItem(costoUnitarioItem.floatValue());
		List<ItemPedido> items = itemDao.findItemsByProductoId(producto.getId());
		var maxItem = Collections.max(items,
				(i1, i2) -> Float.compare(i1.getCostoUnitarioItem(), i2.getCostoUnitarioItem()));
		var maxCostoUnitario = (maxItem.getCostoUnitarioItem() > costoUnitarioItem.floatValue())
				? BigDecimal.valueOf(maxItem.getCostoUnitarioItem())
				: costoUnitarioItem;
		producto.setCostoUnitario(maxCostoUnitario);
		var impuestoIgv = BigDecimal.valueOf(producto.getImpuestoIgv());
		// El metodo calculo el precio unitario neto por articulo
		// costos * (100 + (impuestoIgv + margenGanancia)) / 100;
		for(MargenProducto m : producto.getMargenesProducto() ) {
			m.setPrecioNetoSugerido(maxCostoUnitario.multiply(
					(BigDecimal.valueOf(100).add(impuestoIgv.add(m.getMargen()))).divide(BigDecimal.valueOf(100))));
		}
		return producto;
	}

	@Override
	@Transactional
	public Producto guardar(Producto producto) {
		return productoDao.save(producto);
	}

	public List<Producto> findProductByCategory(Long categoriaId) {
		List<Producto> lstProd = null;
		if (categoriaId > 0) {
			lstProd = productoDao.findProductByCategoriaActiveStore(categoriaId);
		} else {
			lstProd = productoDao.findAllProductsActiveStore();
		}
		return lstProd;
	}

}
