package com.ezra.programandojuntos.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezra.programandojuntos.models.dao.ICategoriaDao;
import com.ezra.programandojuntos.models.entity.Categoria;

@Service
public class CategoriaServiceImpl implements ICategoriaService {

	@Autowired
	private ICategoriaDao categoriaDao;

	@Autowired
	private IUploadFileService uploadFileService;

	@Override
	public Categoria createCategory(Categoria Categoria) {
		return categoriaDao.save(Categoria);
	}

	@Override
	public Categoria updateCategory(Categoria categoria, Long id) {
		Categoria categoriaActual = categoriaDao.findById(id).orElse(null);
		if (categoriaActual == null) {
			return null;
		}
		categoriaActual.setNombre(categoria.getNombre());
		categoriaActual.setDescripcion(categoria.getDescripcion());
		categoriaActual.setActiva(categoria.isActiva());

		if (!categoria.getImagen().equalsIgnoreCase("no-imagen.jpg")
				&& !categoria.getImagen().equalsIgnoreCase(categoriaActual.getImagen())) {
			if (!categoriaActual.getImagen().equalsIgnoreCase("no-imagen.jpg")) {
				uploadFileService.eliminar(categoriaActual.getImagen());
			}
			categoriaActual.setImagen(categoria.getImagen());
		}
		return categoriaDao.save(categoriaActual);
	}
	
	public List<Categoria> listCategoryActive() {
		return categoriaDao.listCategoryActive();
	}

}
