package com.ezra.programandojuntos.models.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileServiceImpl implements IUploadFileService {

	private final Logger log = LoggerFactory.getLogger(UploadFileServiceImpl.class);

	private final static String DIRECTORIO_UPLOAD = "uploads";

	@Override
	public Resource cargar(String nombreFoto) throws MalformedURLException {

		Path rutaArchivo = getPath(DIRECTORIO_UPLOAD, nombreFoto);
		log.info(rutaArchivo.toString());

		Resource recurso = new UrlResource(rutaArchivo.toUri());

		if (!recurso.exists() && !recurso.isReadable()) {
			// Path rutaArchivoDefault =
			// Paths.get("src/main/resources/static/images").resolve("no-imagen.png").toAbsolutePath();
			Path rutaArchivoDefault = getPath("src/main/resources/static/images", "no-imagen.jpg");
			Resource recursoDefault = new UrlResource(rutaArchivoDefault.toUri());
			// log.error("Error no se pudo cargar la imagen: " + nombreFoto);
			return recursoDefault;
		}
		return recurso;
	}

	@Override
	public String copyFileToPath(MultipartFile archivo, boolean clienteOnline) throws IOException {
		String nombreArchivo = archivo.getOriginalFilename().replace(" ", "-");
		if (clienteOnline) {
			nombreArchivo = UUID.randomUUID().toString() + "_" + nombreArchivo;
		}

		Path rutaArchivo = getPath(DIRECTORIO_UPLOAD, nombreArchivo);
		log.info(rutaArchivo.toString());
		Files.copy(archivo.getInputStream(), rutaArchivo);
		return nombreArchivo;
		// }
		// return nombreArchivo;
	}

	@Override
	public boolean eliminar(String nombreImagen) {

		if (nombreImagen != null && nombreImagen.length() > 0) {
			Path rutaFotoAnterior = Paths.get(DIRECTORIO_UPLOAD).resolve(nombreImagen).toAbsolutePath();
			File archivoFotoAnterior = rutaFotoAnterior.toFile();
			if (archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()) {
				archivoFotoAnterior.delete();
				return true;
			}
		}

		return false;
	}

	@Override
	public Path getPath(String ruta, String nombreImagen) {
		return Paths.get(ruta).resolve(nombreImagen).toAbsolutePath();
	}

}
