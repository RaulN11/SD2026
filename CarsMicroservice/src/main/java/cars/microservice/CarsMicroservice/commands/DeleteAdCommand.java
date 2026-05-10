package cars.microservice.CarsMicroservice.commands;

import cars.microservice.CarsMicroservice.services.AdvertisementCommandService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteAdCommand implements AdCommand{
    private final AdvertisementCommandService commandService;
    private final Long adId;
    private final String userEmail;
    private final boolean isAdmin;
    @Override
    public void execute() throws Exception {
        commandService.deleteAdSecured(adId, userEmail, isAdmin);
    }
}
