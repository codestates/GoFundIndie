package com.IndieAn.GoFundIndie.Controller;

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
                                         @RequestPart(value = "upload") MultipartFile image) {

        //image dir result
        try {
            String result;
            if (path.equals("user") && pathId == null) {
                //token expired 401
//            헤더에서 user 값 추출

                // Test User
                User user = entityManager.find(User.class, 1L);

                result = imageService.uploadUserImage(image, user);

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
                        result = imageService.uploadStillImage(image, pathId);

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
                        result = imageService.uploadCastingImage(image, pathId);

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
                        result = imageService.uploadPosterImage(image, pathId);

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
        } catch (MultipartException e) {
            body.clear();
            body.put("message", "bad request : file missing");
            return ResponseEntity.status(400).body(body);
        }
    }

    @DeleteMapping(value = {"/image/{path}/{path_id}", "/image/{path}"})
    public ResponseEntity<?> DeleteImage(@RequestHeader Map<String, String> header,
                                         @PathVariable(value = "path") String path,
                                         @PathVariable(value = "path_id", required = false) Long pathId) {
        if (path.equals("user") && pathId == null) {
            //header 에서 유저 검증
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
                //header에서 관리자인지 검증 필요
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
