package com.IndieAn.GoFundIndie.Controller;

import com.IndieAn.GoFundIndie.Common.ImagePathTypes;
import com.IndieAn.GoFundIndie.Domain.Entity.User;
import com.IndieAn.GoFundIndie.Repository.ImageRepository;
import com.IndieAn.GoFundIndie.Service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;
    private final EntityManager entityManager;

    private final HashMap<String, Object> body = new HashMap<>();

    @PostMapping(value = {"/image/{path}/{path_id}", "/image/{path}"})
    public ResponseEntity<?> UploadImage(@RequestHeader Map<String, String> header,
                                         @PathVariable(value = "path") String path,
                                         @PathVariable(value = "path_id", required = false) Long pathId,
                                         @RequestPart(value = "upload",required = false) MultipartFile image ) {
        //image dir result
        try {
            ImagePathTypes type = ImagePathTypes.findImagePathType(path);
            log.info(type.toString());
            switch (type) {
                case IMAGE_PATH_TYPES_USER:
                    return imageService.uploadUserImage(image, header);
                case IMAGE_PATH_TYPES_STILL:
                    return imageService.uploadStillImage(image, pathId, header);
                case IMAGE_PATH_TYPES_CASTING:
                    return imageService.uploadCastingImage(image, pathId, header);
                case IMAGE_PATH_TYPES_BOARD:
                    return imageService.uploadPosterImage(image, pathId, header);
                default:
                    return imageService.pathTypeDefault();
            }
        } catch (MultipartException e) {
            return imageService.noFileUploadHandler();
        } catch (RuntimeException e) {
            return imageService.pathTypeDefault();
        }
    }

    @DeleteMapping(value = {"/image/{path}/{path_id}", "/image/{path}"})
    public ResponseEntity<?> DeleteImage(@RequestHeader Map<String, String> header,
                                         @PathVariable(value = "path") String path,
                                         @PathVariable(value = "path_id", required = false) Long pathId) {
        try {
            switch (ImagePathTypes.findImagePathType(path)) {
                case IMAGE_PATH_TYPES_USER:
                    return imageService.deleteUserImage(header);
                case IMAGE_PATH_TYPES_STILL:
                    return imageService.deleteStill(pathId, header);
                case IMAGE_PATH_TYPES_CASTING:
                    return imageService.deleteCastingImage(pathId, header);
                case IMAGE_PATH_TYPES_BOARD:
                    return imageService.deletePosterImage(pathId, header);
                default:
                    return imageService.pathTypeDefault();
            }
        } catch (RuntimeException e) {
            return imageService.pathTypeDefault();
        }
    }
}
