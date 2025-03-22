package com.ezra.programandojuntos.util;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.regex.Pattern;

import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.unit.DataSize;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import com.ezra.programandojuntos.exceptions.StorageExceptions;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class StorageUtil implements ServletContextAware {
	
    private static final Logger log = LoggerFactory.getLogger(StorageUtil.class);

    private ServletContext servletContext;
    public static final String[] VALID_TYPES = {
        "image/jpeg", "image/png", "image/x-ms-bmp", "image/gif",
        "message/rfc822",
        "application/pdf",
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
        "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
        "application/vnd.openxmlformats-officedocument.presentationml.presentation",
        "application/vnd.ms-powerpoint",
        "application/vnd.ms-excel",
        "application/msword",
        "application/vnd.ms-outlook"
    };
    public static final Pattern VALID_TYPES_REGEX = Pattern.compile(String.join("|", VALID_TYPES), Pattern.CASE_INSENSITIVE);

    @Value("${spring.servlet.multipart.max-file-size:10MB}")
    DataSize maxFileSize;

    /**
     * Copia el archivo subido a una ruta temporal y lo expone a un consumer
     *
     */
    public void manageUpload(MultipartFile multipartFile, Consumer<Path> upload) {
        Path file = null;
        try {
            validateFile(multipartFile);
            file = createTempFile();
            multipartFile.transferTo(file);
            upload.accept(file);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } finally {
            deleteQuietly(file);
        }
    }

    private void validateFileSize(MultipartFile multipartFile) {
        if (multipartFile.getSize() > maxFileSize.toBytes()) {
            throw new StorageExceptions(String.format("El tamaño del archivo %s supera el máximo permitido: %s",
                   // multipartFile.getOriginalFilename(), FileUtils.byteCountToDisplaySize(maxFileSize.toMegabytes())));
            multipartFile.getOriginalFilename(), maxFileSize.toMegabytes()));

        }
    }

    public void validateFile(MultipartFile multipartFile) {
        try {
            validateFileSize(multipartFile);
            try ( var in = multipartFile.getInputStream()) {
                Tika tika = new Tika();
                String contentType = tika.detect(in);
                boolean valid = VALID_TYPES_REGEX.matcher(contentType).matches();
                if (!valid) {
                    log.warn("Se rechazó el archivo '{}' porque tiene un tipo de datos no admitido. Tipo_detectado={}", multipartFile.getOriginalFilename(), contentType);
                    throw new StorageExceptions(String.format("El archivo %s tiene un tipo de datos inválido", multipartFile.getOriginalFilename()));
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

//    public void validateFile(MultipartFile multipartFile) {
//        Path file = null;
//        try {
//            validateFileSize(multipartFile);
//            file = createTempFile();
//            try ( var in = new BufferedInputStream(multipartFile.getInputStream())) {
//                Files.copy(in, file, StandardCopyOption.REPLACE_EXISTING);
//            }
//            validateFile(file, multipartFile.getOriginalFilename());
//        } catch (IOException e) {
//            throw new UncheckedIOException(e);
//        } finally {
//            deleteQuietly(file);
//        }
//    }
//
//    private void validateFile(Path file, String filename) throws IOException {
//        Tika tika = new Tika();
//        String contentType = tika.detect(file);
//        boolean valid = VALID_TYPES_REGEX.matcher(contentType).matches();
//        if (!valid) {
//            log.warn("Se rechazó el archivo '{}' porque tiene un tipo de datos no admitido. Tipo_detectado={}", filename, contentType);
//            throw new ValidationException(String.format("El archivo '%s' tiene un tipo de datos inválido", filename));
//        }
//    }
    public void manageDownload(Consumer<Path> download, String name, HttpServletResponse response) {
        this.manageDownload(download, name, response, true);
    }

    /**
     * Ofrece un consumer responsable de escribir un archivo y renderiza el
     * archivo en el response
     */
    public void manageDownload(Consumer<Path> download, String name, HttpServletResponse response, boolean attachment) {
        Path tmpFile = null;
        try {
            tmpFile = createTempFile();
            download.accept(tmpFile);
            prepareResponse(name, tmpFile, attachment, response);
            streamFile(tmpFile, response);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } finally {
            deleteQuietly(tmpFile);
        }
    }

    private Path createTempFile() throws IOException {
        return Files.createTempFile(null, null);
    }

    private void prepareResponse(String name, Path file, boolean attachment, HttpServletResponse response)
            throws IOException {
        String mimeType = servletContext.getMimeType(name);
        response.setContentType(mimeType);
        String nombreArchivo = name.replace("\"", "");
        String contentDisp = String.format("%s;filename=\"%s\";size=%s",
                attachment ? "attachment" : "inline", nombreArchivo, Files.size(file));
        response.setHeader("Content-Disposition", contentDisp);
    }

    private void deleteQuietly(Path file) {
        if (file != null) {
            try {
                if (Files.exists(file)) {
                    Files.deleteIfExists(file);
                }
            } catch (IOException ignore) {
                log.debug("Error al borrar archivo {}", file.toString(), ignore);
            }
        }
    }

    private void streamFile(Path file, HttpServletResponse response) throws IOException {
        Files.copy(file, response.getOutputStream());
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

}