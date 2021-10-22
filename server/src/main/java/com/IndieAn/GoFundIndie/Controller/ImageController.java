package com.IndieAn.GoFundIndie.Controller;

import com.IndieAn.GoFundIndie.Repository.ImageRepository;
import com.IndieAn.GoFundIndie.Service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;
    private final ImageRepository imageRepository;

    private final static String MOVIE_DIR = "movie";
    private final static String MOVIE_POSTER = "move-poster";
    private final static String USER_DIR = "user";
    private final static String USER_PROFILE = "user-profile";

    private final HashMap<String, Object> body = new HashMap<>();

    //image dir result
    private String result;

    @GetMapping("/image")
    public ResponseEntity<?> test(){
        body.clear();
        body.put("dir","dir");
        return ResponseEntity.status(200).body(body);
    }

    @PostMapping("/image/{path}/{path_id}")
    public ResponseEntity<?> UploadImage(@RequestHeader Map<String, String> header,
                                         @PathVariable(value = "path") String path,
                                         @PathVariable(value = "path_id") Long pathId,
                                         @RequestPart(value = "upload") MultipartFile image) {

        //TODO String result; 클래스 안 선언 말고 / 메소드 선언 Garbage Collector 로 안 없애는지??
        if (path.equals("user")) {
                //user valid check
//                if(header userid == pathId){
//                    body.clear();
//                    return ResponseEntity.status(403).body(body.put("message", "invalid user"));
//                }

            //token expired 401

            result = imageService.uploadUserImage(image, pathId, USER_DIR + "/" + pathId, USER_PROFILE);

            //user db update
            //imageService.updateUser -> userRepository

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
        } else if (path.equals("still")) {
            result = imageService.uploadStillImage(image, pathId, MOVIE_DIR + "/" + pathId.toString() + "/still");

            //board valid check fail
            if(result.equals("NullPointException")) {
                body.clear();
                body.put("message", "can not find board");
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
        } else if (path.equals("casting")) {
            result = imageService.uploadCastingImage(image, pathId, MOVIE_DIR + "/" + pathId.toString() + "/casting");

            //board valid check fail
            if(result.equals("NullPointException")) {
                body.clear();
                body.put("message", "can not find board");
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
        } else if (path.equals("board")) {
            result = imageService.uploadPosterImage(image, pathId, MOVIE_DIR + "/" + pathId.toString(), MOVIE_POSTER);

            //board valid check fail
            if(result.equals("NullPointException")) {
                body.clear();
                body.put("message", "can not find board");
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
            body.clear();
            body.put("message", "can not find path");
            return ResponseEntity.status(400).body(body);
        }


        //403 invalid user

        //404 can not find path_id

        //body.clear();
        //return ResponseEntity.status(201).body(body.put("dir","dir"));
    }
}
