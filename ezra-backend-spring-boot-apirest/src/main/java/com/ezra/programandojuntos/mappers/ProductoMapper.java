package com.ezra.programandojuntos.mappers;

import org.mapstruct.Mapper;

import com.ezra.programandojuntos.dto.ProductoDto;
import com.ezra.programandojuntos.models.entity.Producto;

@Mapper(componentModel = "spring")
public interface ProductoMapper {
	
	Producto asProducto (ProductoDto productoDto);

}
