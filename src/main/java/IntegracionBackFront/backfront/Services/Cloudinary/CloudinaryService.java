package IntegracionBackFront.backfront.Services.Cloudinary;

import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

import static com.sun.tools.javac.resources.CompilerProperties.Errors.IoException;

@Service
public class CloudinaryService {

    //1. Determinar el tamaño de las iagenes en MB
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;
    //2. Definir las extenciones permitidas
    private static final String[] ALLOWED_EXTENSIONS = {".jpg", ".jpeg", ".png"};
    //3. Atributo cloudinary
    private final Cloudinary cloudinary;
    //Contructor para inyectar la dependencia de cloudinary
    public CloudinaryService(Cloudinary cloudinary){
        this.cloudinary = cloudinary;
    }

    public String uploadImage(MultipartFile file)throws IOException{
        validateImagen(file);

    }

    private void validateImagen(MultipartFile file) {
        //1. Verificar si el archivo esta vacio
        if(file.isEmpty()){
            throw new IllegalArgumentException("El archivo esta vacio");
        }
        //2. Verificar si el tamaño excede el limite permitido
        if (file.getSize() > MAX_FILE_SIZE){
            throw new IllegalArgumentException("El tamaño del archivo no debe de ser mayor a 5MB");
        }
        //3. Verificar el nombre original del archivo
        String origianlFileName = file.getOriginalFilename();
        if (origianlFileName == null){
            throw new IllegalArgumentException("Nombre del archivo invalido");
        }
        //4. Extraer y validar la extencion del archivo
        String extension = origianlFileName.substring(origianlFileName.lastIndexOf(".")).toLowerCase();
        if (!Arrays.asList(ALLOWED_EXTENSIONS).contains(extension)){
            throw new IllegalArgumentException("Solo se permite archivo JPG, JPEG, PNG");
        }
        //5.vERIFICAR QUE EL TIPO MINE SEA UNA IMAGEN
        if (!file.getContentType().startsWith("image/")){
            throw new IllegalArgumentException("El archivo debe ser una imagen valida");
        }
    }
}
