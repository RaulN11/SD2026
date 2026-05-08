package cars.microservice.CarsMicroservice.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageService {
    String saveFile(MultipartFile file) throws IOException;
}
