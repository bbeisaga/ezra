package com.ezra.programandojuntos.models.services;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.ezra.programandojuntos.dto.ProductoDto;
import com.ezra.programandojuntos.models.entity.Categoria;
import com.ezra.programandojuntos.models.entity.Color;
import com.ezra.programandojuntos.models.entity.Material;
import com.ezra.programandojuntos.models.entity.Producto;
import com.ezra.programandojuntos.models.entity.Uso;

public interface ProductoService {
	
	public Producto findProductoById(Long id);
	
	public List<Producto> findProductoByNombre(String term);
	
	public List<Producto> findAllProductos();
	
	public Page<Producto> findAllProductoPageable(String query, Pageable pageRequest);
	
	public List<Color> findAllColores();

	public List<Material> findAllMateriales();
	
	public List<Categoria> findAllCategoriass();
	
	public List<Uso> findAllUsos();
	
	public Producto crear(Producto producto);
	
	public Producto crearConImagen(Producto producto, MultipartFile archivo) throws IOException ;
	
	public Producto actualizar(Producto producto, Long id);
	
	public Producto guardar(Producto producto);

}
