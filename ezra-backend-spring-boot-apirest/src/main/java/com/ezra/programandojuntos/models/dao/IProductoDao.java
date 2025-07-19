package com.ezra.programandojuntos.models.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ezra.programandojuntos.models.entity.Categoria;
import com.ezra.programandojuntos.models.entity.Color;
import com.ezra.programandojuntos.models.entity.EstadoProducto;
import com.ezra.programandojuntos.models.entity.Material;
import com.ezra.programandojuntos.models.entity.Producto;
import com.ezra.programandojuntos.models.entity.TipoDocumento;
import com.ezra.programandojuntos.models.entity.Uso;

public interface IProductoDao extends CrudRepository<Producto, Long>{

	@Query("select p from Producto p where p.nombre like %?1%")
	public List<Producto> findByNombre(String term);
	
	@Query("select p from Producto p where p.codigo =:codigo")
	public Producto findByCodigo(String codigo);
	
	@Query("select p from Producto p where p.activo =true and p.uso.id = 6")
	public List<Producto> findAllUsoServicioEnvio();
	
	@Query("select p from Producto p where p.activo =true and p.uso.id IN (4,5,6)")//NO MOVER LA TABLA USOS
	public List<Producto> findAllUsoServicio();
	
	public List<Producto> findByNombreContainingIgnoreCase(String term);
	
	public List<Producto> findByNombreStartingWithIgnoreCase(String term);
	
	//@Query("SELECT p FROM Producto p WHERE (p.nombre like %:query% OR p.marca like %:query% OR c.tipoDocumento.acronimo like %:query% OR c.numeroDocumento like %:query% OR c.celular like %:query%)")
	@Query("SELECT p FROM Producto p WHERE (p.nombre like %:query%)")
	Page<Producto> findAllProductoPageable(@Param("query") String query, Pageable pageRequest);
	
	@Query("SELECT p FROM Producto p WHERE p.visibleEnTienda=true AND p.activo=true AND p.categoria.id = :categoriaId")
	List<Producto> findProductByCategoriaActiveStore(Long categoriaId);
	
	@Query("SELECT p FROM Producto p WHERE p.visibleEnTienda=true AND p.activo=true")
	List<Producto> findAllProductsActiveStore();
		
	@Query("from Color")
	public List<Color> findAllColores();
	
	@Query("from Material")
	public List<Material> findAllMateriales();
	
	@Query("from Categoria")
	public List<Categoria> findAllCategorias();
	
	@Query("from Uso")
	public List<Uso> findAllUsos();
	
	@Query("from EstadoProducto")
	public List<EstadoProducto> findAllEstadoProducto();
}
