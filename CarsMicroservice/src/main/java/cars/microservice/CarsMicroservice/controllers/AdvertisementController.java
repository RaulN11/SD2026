package cars.microservice.CarsMicroservice.controllers;

import cars.microservice.CarsMicroservice.dtos.AdRequestDTO;
import cars.microservice.CarsMicroservice.dtos.AdResponseDTO;
import cars.microservice.CarsMicroservice.dtos.CarResponseDTO;
import cars.microservice.CarsMicroservice.export.ExportStrategyInterface;
import cars.microservice.CarsMicroservice.models.Advertisement;
import cars.microservice.CarsMicroservice.models.UserClient;
import cars.microservice.CarsMicroservice.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.RequestPath;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/ad/api")
@RequiredArgsConstructor
public class AdvertisementController {
    private final AdvertisementCommandService commandService;
    private final AdvertisementQueryService queryService;
    private final ExportService exportService;

    @PostMapping(value = "/publish", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('ROLE_CLIENT')")
    public ResponseEntity<?> publishAd(@AuthenticationPrincipal String email,
                                       @RequestPart("adData") AdRequestDTO requestBody,
                                       @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        return ResponseEntity.ok(convertToResponseDTO(commandService.publishAd(email, requestBody, image)));
    }

    @GetMapping("/search")
    public List<AdResponseDTO> search(@RequestParam(required = false) String brand,
                                      @RequestParam(required = false) String model,
                                      @RequestParam(required = false) String chassis) {
        return queryService.loadAdsByCar(brand, model, chassis)
                .stream().map(this::convertToResponseDTO).toList();
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_CLIENT', 'ROLE_ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id,
                                    @AuthenticationPrincipal String email) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        commandService.deleteAdSecured(id, email, isAdmin);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ROLE_CLIENT')")
    public ResponseEntity<AdResponseDTO> editPrice(@PathVariable Long id,
                                                   @RequestParam Integer price,
                                                   @AuthenticationPrincipal String email) {
        commandService.updateAdPriceSecured(id, price, email);
        return ResponseEntity.ok(convertToResponseDTO(queryService.loadAdById(id)));
    }

    @GetMapping("/export")
    public ResponseEntity<String> export(@RequestParam(required = false) String brand,
                                         @RequestParam(required = false) String model,
                                         @RequestParam(required = false) String chassis,
                                         @RequestParam String format) {
        try {
            List<Advertisement> ads = queryService.loadAdsByCar(brand, model, chassis);
            ExportStrategyInterface strategy = exportService.getStrategy(format);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=export" + strategy.getFileExtension())
                    .contentType(MediaType.parseMediaType(strategy.getContentType()))
                    .body(strategy.export(ads));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Invalid format: " + format);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    private AdResponseDTO convertToResponseDTO(Advertisement ad) {
        CarResponseDTO carDTO = new CarResponseDTO();
        carDTO.setId(ad.getCar().getId());
        carDTO.setBrand(ad.getCar().getBrand());
        carDTO.setModel(ad.getCar().getModel());
        carDTO.setChassis(ad.getCar().getChassis());

        return new AdResponseDTO(
                ad.getId(),
                ad.getUserId(),
                ad.getUserEmail(),
                ad.getFirstName(),
                ad.getLastName(),
                carDTO,
                ad.getPrice(),
                ad.getYear(),
                ad.getImages()
        );
    }

}
