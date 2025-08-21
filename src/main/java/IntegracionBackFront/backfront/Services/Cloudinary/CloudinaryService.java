package IntegracionBackFront.backfront.Services.Cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@Service
public class CloudinaryService {

    //1. Constante para definir el tamaño maximo definido para los archivos (5MB)
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;

    //2. Extenciones de archivo permitido para subir a coudinary
    private static final String[] ALLOWED_EXTENSIONS = {".jpg", ".png",".jpeg"};

    //3. Cliente de cloudinary inyecta como dependencis
    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    //Subir imagen a la raiz de clodinary
    public String uploadImage(MultipartFile file) throws IOException {
        validateImage(file);

        Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                "resource_type", "auto",
                "quality", "auto:good"
        ));
        return (String) uploadResult.get("secure_url");
    }

    public String uploadImage(MultipartFile file, String folder) throws IOException{
        validateImage(file);
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        String uniqueFilename = "img_" + UUID.randomUUID() + fileExtension;

        Map<String, Object> options = ObjectUtils.asMap(
          "folder", folder,     //Carpeta de destino
          "public_id", uniqueFilename,  //Nombre unico para el Archivo
          "use_filename", false,        //No usar el nombre original
          "unique_filename", false,     //
          "overwrite", false,
          "resource_type", "auto",
          "quality", "auto:good"
        );
        Map<?, ?> uploadResult = cloudinary.uploader().upload(file.getBytes(),options);
        return (String) uploadResult.get("secure_url");
    }

    private void validateImage(MultipartFile file) {
        if (file.isEmpty()) throw  new IllegalArgumentException("El archivo no puede estar vacio"); //1. verifica si e archivo esta vacio
        if (file.getSize()> MAX_FILE_SIZE) throw new IllegalArgumentException("El tamaño del archivo no puede exceder de 5MB"); //2. verificar si el archivo excede del tamaño limite
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) throw new IllegalArgumentException("Nombre del archivo no valido"); //3. valida el nombre del archivo
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        if (!Arrays.asList(ALLOWED_EXTENSIONS).contains(extension)) throw new IllegalArgumentException("Solo se aceptan archivos jpg, jpeg, png");
        if (!file.getContentType().startsWith("image/")) throw new IllegalArgumentException("El archivo debe ser una imagen valida");

    }
    //Subir imagenes a una carpeta de cloudinary
}
