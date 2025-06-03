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
	
	@Override
	@Transactional
	public Producto crear(Producto producto) {
		return productoDao.save(producto);
	}
	
	
	@Override
	public Producto crearConImagen(Producto producto, MultipartFile archivo) throws IOException {
		String nombreArchivo = null;
//		if(archivo == null) {
//			nombreArchivo = "sin-imagen";
//		} else {
			nombreArchivo = uploadFileService.copyFileToPath(archivo);
			String nombreImagenAnterior = producto.getImagen();
			if(nombreImagenAnterior != null && nombreImagenAnterior.length() > 0 ) {
				Path rutaImagenAnterior = uploadFileService.getPath(nombreImagenAnterior); 
				File archivoImagenAnterior = rutaImagenAnterior.toFile();
				if(archivoImagenAnterior.exists() && archivoImagenAnterior.canRead()) {
					archivoImagenAnterior.delete();
				}
			}
		//}
			producto.setImagen(nombreArchivo);
	//	return productoDao.save(productoMapper.asProducto(producto));
		return productoDao.save(producto);


	}

	@Override
	@Transactional
	public Producto actualizar(Producto producto, Long id) {
		Producto productoActual = this.findProductoById(id);

		if (productoActual == null) {
			return null;
//			response.put("mensaje", "Error: no se pudo editar, el producto ID: "
//					.concat(id.toString().concat(" no existe en la base de datos!")));
//			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		productoActual.setPeso(producto.getPeso());
		productoActual.setMedidas(producto.getMedidas());
		productoActual.setColor(producto.getColor());
		productoActual.setMaterial(producto.getMaterial());
		productoActual.setCategoria(producto.getCategoria());
		productoActual.setUso(producto.getUso());		
		return productoDao.save(producto);
	}
	
	@Override
	@Transactional
	public Producto guardar(Producto producto) {	
		return productoDao.save(producto);
	}

}
