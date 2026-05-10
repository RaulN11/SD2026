package cars.microservice.CarsMicroservice.commands;

import cars.microservice.CarsMicroservice.services.AdvertisementCommandService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdatePriceCommand implements AdCommand {

    private final AdvertisementCommandService commandService;
    private final Long adId;
    private final Integer newPrice;
    private final String userEmail;

    @Override
    public void execute() {
        commandService.updateAdPriceSecured(adId, newPrice, userEmail);
    }
}