package com.IndieAn.GoFundIndie.Controller;

import com.IndieAn.GoFundIndie.Domain.Entity.User;
import com.IndieAn.GoFundIndie.Repository.ImageRepository;
import com.IndieAn.GoFundIndie.Service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;
    private final ImageRepository imageRepository;
    private final EntityManager entityManager;

    private final static String MOVIE_DIR = "movie";
    private final static String MOVIE_POSTER = "move-poster";
    private final static String USER_DIR = "user";
    private final static String USER_PROFILE = "user-profile";

    private final HashMap<String, Object> body = new HashMap<>();

    //image dir result
    private String result;

    @PostMapping("/image/{path}/{path_id}")
    public ResponseEntity<?> UploadImage(@RequestHeader Map<String, String> header,
                                         @PathVariable(value = "path") String path,
                                         @PathVariable(value = "path_id", required = false) Long pathId,
                                         @RequestPart(value = "upload") MultipartFile image) {

        //TODO String result; 클래스 안 선언 말고 / 메소드 선언 Garbage Collector 로 안 없애는지??
        if (path.equals("user") && pathId == null) {
            //token expired 401
//            헤더에서 user 값 추출
            // Test User
            User user = entityManager.find(User.class, 1L);

            result = imageService.uploadUserImage(image, user, USER_DIR + "/" + user.getId(), USER_PROFILE);

            //board valid check fail
            if(result.equals("NullPointException")) {
                body.clear();
                body.put("message", "can not find user");
                return ResponseEntity.status(404).body(body);

            //image file bad request
            } else if(result.equals("IOException")) {
                body.clear();
                body.put("message", "bad request");
                return ResponseEntity.status(400).body(body);
            }

            body.clear();
            body.put("dir", result);
            return ResponseEntity.status(201).body(body);
        } else {
            switch (path) {
                case "still":
                    result = imageService.uploadStillImage(image, pathId, MOVIE_DIR + "/" + pathId.toString() + "/still");

                    //board valid check fail
                    if (result.equals("NullPointException")) {
                        body.clear();
                        body.put("message", "can not find board");
                        return ResponseEntity.status(404).body(body);

                        //image file bad request
                    } else if (result.equals("IOException")) {
                        body.clear();
                        body.put("message", "bad request");
                        return ResponseEntity.status(400).body(body);
                    }

                    body.clear();
                    body.put("dir", result);
                    return ResponseEntity.status(201).body(body);
                case "casting":
                    result = imageService.uploadCastingImage(image, pathId, MOVIE_DIR + "/" + pathId.toString() + "/casting");

                    //board valid check fail
                    if (result.equals("NullPointException")) {
                        body.clear();
                        body.put("message", "can not find board");
                        return ResponseEntity.status(404).body(body);

                        //image file bad request
                    } else if (result.equals("IOException")) {
                        body.clear();
                        body.put("message", "bad request");
                        return ResponseEntity.status(400).body(body);
                    }

                    body.clear();
                    body.put("dir", result);
                    return ResponseEntity.status(201).body(body);
                case "board":
                    result = imageService.uploadPosterImage(image, pathId, MOVIE_DIR + "/" + pathId.toString(), MOVIE_POSTER);

                    //board valid check fail
                    if (result.equals("NullPointException")) {
                        body.clear();
                        body.put("message", "can not find board");
                        return ResponseEntity.status(404).body(body);

                        //image file bad request
                    } else if (result.equals("IOException")) {
                        body.clear();
                        body.put("message", "bad request");
                        return ResponseEntity.status(400).body(body);
                    }

                    body.clear();
                    body.put("dir", result);
                    return ResponseEntity.status(201).body(body);
                default:
                    body.clear();
                    body.put("message", "bad request");
                    return ResponseEntity.status(400).body(body);
            }
        }
    }

    @DeleteMapping("/image/{path}/{path_id}")
    public ResponseEntity<?> DeleteImage(@RequestHeader Map<String, String> header,
                                         @PathVariable(value = "path") String path,
                                         @PathVariable(value = "path_id", required = false) Long pathId) {
        if (path.equals("user") && pathId == null) {
            User user = entityManager.find(User.class, 1L);

            if(imageService.deleteUserImage(user)){
                body.clear();
                return ResponseEntity.status(204).body(body);
            } else {
                body.clear();
                body.put("message", "user not found");
                return ResponseEntity.status(404).body(body);
            }
        } else {
            switch (path) {
                case "still":
                    if(imageService.deleteStill(pathId)){
                        body.clear();
                        return ResponseEntity.status(204).body(body);
                    } else {
                        body.clear();
                        body.put("message", "still not found");
                        return ResponseEntity.status(404).body(body);
                    }
                case "casting":
                    if(imageService.deleteCastingImage(pathId)){
                        body.clear();
                        return ResponseEntity.status(204).body(body);
                    } else {
                        body.clear();
                        body.put("message", "casting not found");
                        return ResponseEntity.status(404).body(body);
                    }
                case "board":
                    if(imageService.deletePosterImage(pathId)){
                        body.clear();
                        return ResponseEntity.status(204).body(body);
                    } else {
                        body.clear();
                        body.put("message", "board not found");
                        return ResponseEntity.status(404).body(body);
                    }
                default:
                    body.clear();
                    body.put("message", "bad request");
                    return ResponseEntity.status(400).body(body);
            }
        }
    }
}
