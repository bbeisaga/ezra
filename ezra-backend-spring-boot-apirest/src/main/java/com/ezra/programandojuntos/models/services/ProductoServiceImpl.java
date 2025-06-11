package com.ezra.programandojuntos.models.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ezra.programandojuntos.dto.ProductoDto;
import com.ezra.programandojuntos.mappers.ProductoMapper;
import com.ezra.programandojuntos.models.dao.IProductoDao;
import com.ezra.programandojuntos.models.entity.Categoria;
import com.ezra.programandojuntos.models.entity.Color;
import com.ezra.programandojuntos.models.entity.Material;
import com.ezra.programandojuntos.models.entity.Producto;
import com.ezra.programandojuntos.models.entity.Uso;

@Service
public class ProductoServiceImpl implements ProductoService {
	
	@Autowired
	private IProductoDao productoDao;
	
	@Autowired
	private IUploadFileService uploadFileService;
	
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
		
		//log.info("findAllProductoPageable pageRequest= {}",pageRequest);
	
		return productoDao.findAllProductoPageable(query, pageRequest);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Producto findProductoById(Long id){
		return productoDao.findById(id).orElseThrow();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Color> findAllColores() {
		return productoDao.findAllColores();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Material> findAllMateriales(){
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
	
//	@Override
//	@Transactional
//	public Producto crear(Producto producto) {
//		return productoDao.save(producto);
//	}
	
	
	@Override
	public Producto crearConImagen(Producto producto, MultipartFile archivo) throws IOException {
		//producto.setImagen(null);
		if(archivo != null) {
			String nombreArchivo = uploadFileService.copyFileToPath(archivo);
			producto.setImagen(nombreArchivo);
		} 
		return productoDao.save(producto);
	}

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
	public Producto actualizarConImagen(Producto producto, MultipartFile archivo, Long id) throws IOException {
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
		productoActual.setMinCantidadPedido(producto.getMinCantidadPedido());
		productoActual.setMaxCantidadPedido(producto.getMaxCantidadPedido());
		productoActual.setGruposDe(producto.getGruposDe());
		productoActual.setPrecioBruto(producto.getPrecioBruto());
		productoActual.setPrecioNeto(producto.getPrecioNeto());
		productoActual.setPrecioBrutoRebajado(producto.getPrecioBrutoRebajado());
		productoActual.setPrecioNetoRabajado(producto.getPrecioNetoRabajado());
		productoActual.setFechaPrecioRebajadoDesde(producto.getFechaPrecioRebajadoDesde());
		productoActual.setFechaPrecioRebajadoHasta(producto.getFechaPrecioRebajadoHasta());
		productoActual.setVisibleEnTienda(producto.isVisibleEnTienda());
		productoActual.setActivo(producto.isActivo());
		productoActual.setColor(producto.getColor());
		productoActual.setMaterial(producto.getMaterial());
		productoActual.setCategoria(producto.getCategoria());
		productoActual.setUso(producto.getUso());	
		//productoActual.setImagen(producto.getImagen());
		
		if (archivo != null) {
			String nombreArchivo = uploadFileService.copyFileToPath(archivo);
			if(!productoActual.getImagen().equalsIgnoreCase("no-imagen.jpg"))  {
				uploadFileService.eliminar(productoActual.getImagen());
			} 
			productoActual.setImagen(nombreArchivo);
		} else {
			productoActual.setImagen(producto.getImagen());
		}
		return productoDao.save(productoActual);
	}
	
	@Override
	@Transactional
	public Producto guardar(Producto producto) {	
		return productoDao.save(producto);
	}
	
	public List<Producto> findProductByCategory(Long categoriaId){
		List<Producto> lstProd = null;
		if(categoriaId > 0) {
			lstProd = productoDao.findProductByCategoriaActiveStore(categoriaId);
		} else {
			lstProd = productoDao.findAllProductsActiveStore();
		}
		return lstProd;
	}


}
