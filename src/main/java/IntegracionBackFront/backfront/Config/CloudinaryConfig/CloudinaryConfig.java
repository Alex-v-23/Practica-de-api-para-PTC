package IntegracionBackFront.backfront.Config.CloudinaryConfig;

import com.cloudinary.Cloudinary;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {


    @Bean
    public Cloudinary cloudinary(){
        //Crear un objeto para las variables de archivo .env
        Dotenv dotenv = Dotenv.load();

        //cREAR UN MAP PARA ALMACENAR LA CONFIGURACION NECESARI PARA CLOUDINARY
        Map<String, String> config = new HashMap<>();

        config.put("cloud_name", dotenv.get("CLOUDINARY_CLOUD_NAME"));
        config.put("api_key", dotenv.get("CLOUDINARY_API_KEY"));
        config.put("api_secret", dotenv.get("CLOUDINARY_API_SECRET"));

        return  new Cloudinary(config);
    }
}
