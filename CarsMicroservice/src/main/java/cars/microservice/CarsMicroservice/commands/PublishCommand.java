package cars.microservice.CarsMicroservice.commands;

import cars.microservice.CarsMicroservice.dtos.AdRequestDTO;
import cars.microservice.CarsMicroservice.models.Advertisement;
import cars.microservice.CarsMicroservice.services.AdvertisementCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
public class PublishCommand implements AdCommand{
    private final AdvertisementCommandService commandService;
    private final String email;
    private final AdRequestDTO requestDTO;
    private final MultipartFile image;
    private Advertisement result;

    @Override
    public void execute() throws Exception {
        result = commandService.publishAd(email, requestDTO, image);
    }

    public Advertisement getResult(){
        return result;
    }
}
