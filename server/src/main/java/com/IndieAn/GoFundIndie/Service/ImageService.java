package com.IndieAn.GoFundIndie.Service;

import com.IndieAn.GoFundIndie.Domain.Entity.Board;
import com.IndieAn.GoFundIndie.Domain.Entity.Casting;
import com.IndieAn.GoFundIndie.Domain.Entity.Still;
import com.IndieAn.GoFundIndie.Domain.Entity.User;
import com.IndieAn.GoFundIndie.Repository.BoardRepository;
import com.IndieAn.GoFundIndie.Repository.CastingRepository;
import com.IndieAn.GoFundIndie.Repository.ImageRepository;
import com.IndieAn.GoFundIndie.Repository.UserRepository;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {
    private final CastingRepository castingRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final ImageRepository imageRepository;

    private final UserService userService;

    private final AmazonS3Client amazonS3Client;
    private final AmazonS3 amazonS3;

    private final HashMap<String, Object> body = new HashMap<>();

    private String result;
    private String dir;

    @Value("#{info['gofundindie.s3.bucket']}")
    private String bucket;

    @Value("#{info['dir.movie']}")
    private String DIR_MOVIE;

    @Value("#{info['dir.movie.poster']}")
    private String DIR_MOVIE_POSTER;

    @Value("#{info['dir.user']}")
    private String DIR_USER;

    @Value("#{info['dir.user.profile']}")
    private String DIR_USER_PROFILE;

    /**
     * MultipartFile 에서 File 로 변환 <br>
     * convert multipart file -> file <br>
     *
     */
    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(file.getOriginalFilename());
        if(convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }

    /** 변환된 파일 업로드 후 이미지 저장 주소 리턴 <br>
     * s3 file upload & url return <br>
     * <p>
     *     파일 변환 문제 - IllegalArgumentException <br>
     *     <br>
     * </p>
     */
    private String upload(File file, String dirName, String oriFileName) {
        String fileName = dirName + "/" + oriFileName;
        try {
            amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, file)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            removeNewFile(file);
        } catch (IllegalArgumentException e) {
            log.error("upload IllegalArgumentException");
            return "IllegalArgumentException";
        }
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    /**
     * 업로드 후 남은 파일 삭제<br>
     * local file delete<br>
     */
    private void removeNewFile(File targetFile) {
        if(!targetFile.delete()) {
            log.error("Local File Can not be Deleted");
        }
    }

    /**
     * <p> S3 파일 업로드 준비 <br>
     * S3 uploadStandBy
     * </p>
     * <br>
     * <p>
     *     용량 초과시 - IOException<br>
     *     파일 자체 문제 - MultipartException<br>
     *     파일 이름 없음 - NullPointerException<br>
     *     <br>
     * </p>
     */
    private String uploadStandBy(MultipartFile multipartFile, String dirName, String oriFileName) {
        try{
            File file = convert(multipartFile)
                       .orElseThrow(() -> new IllegalArgumentException("file convert fail"));
            return upload(file, dirName, oriFileName);
        } catch (IOException e) {
            log.error("uploadStanBy IOException");
            return "IOException";
        } catch (MultipartException e) {
            log.error("uploadStanBy MultipartException");
            return "MultipartException";
        } catch (NullPointerException e) {
            log.error("uploadStanBy NullPointerException");
            return "NullPointerException";
        }
    }

    /**
     * <p> S3 파일 삭제 <br>
     * S3 file delete
     * </p>
     * <p>
     * key 값을 받아서 S3 안 이미지 삭제 <br>
     * <br>
     * key 값 예시 <br>
     * <br>
     *   /user/2/user-profile.png
     * </p>
     *  <br>
     */
    private void delete(String key) {
        try {
            DeleteObjectRequest dor = new DeleteObjectRequest(bucket, key);
            this.amazonS3.deleteObject(dor);
        } catch (AmazonServiceException e) {
            log.error("S3 File Can not be Deleted");
        }
    }

    /**
     * <p>
     * One-line ResponseEntity Result
     * </p>
     */
    private ResponseEntity<?> singlePut(int status, String key, Object value) {
        body.clear();
        body.put(key,value);
        return ResponseEntity.status(status).body(body);
    }

    /**
     * <p>
     * If PathVariable is not Enum value
     * </p>
     */
    public ResponseEntity<?> pathTypeDefault() {
        return singlePut(400, "code", 4009);
    }

    /**
     * <p>
     * If RequestPart is null
     * </p>
     */
    public ResponseEntity<?> noFileUploadHandler() {
        return singlePut(400, "code", 4007);
    }

    /**
     * <p>
     * Result case separation function
     * </p>
     */
    private ResponseEntity<?> resultExport(String result) {
        if(result.equals("IOException") || result.equals("IllegalArgumentException")) {
            return singlePut(400, "code", 4008);
        } else if(result.equals("MultipartException") || result.equals("NullPointerException")) {
            return noFileUploadHandler();
        } else {
            return singlePut(200, "dir", result);
        }
    }

    /**
     * <p>
     * Header token valid check
     * </p>
     */
    private int headerTokenCheck(Map<String, String> header) {
        try {
            if(header.get("accesstoken") == null) return 4000;

            Map<String, Object> checkToken = userService.CheckToken(header.get("accesstoken"));

            if(checkToken.get("email").toString() == null)
                return Integer.parseInt(checkToken.get("code").toString());
            else
                return 0;
        } catch (NullPointerException e) {
            return 4000;
        }
    }

    /**
     * <p>
     * User not found
     * </p>
     */
    private ResponseEntity<?> userNotFound() {
        return singlePut(400,"code", 4400);
    }

    /**
     * <p>
     * Use token to extract user from header
     * <br>
     * <br>
     * </p>
     * @throws NullPointerException If the user cannot be found by the token's email
     */
    private User userFindToHeader(Map<String, String> header) {
        User user = userService.FindUserUseEmail(
                    userService.CheckToken(
                        header.get("accesstoken"))
                        .get("email")
                        .toString());
        if(user == null) throw new NullPointerException();
        return user;
    }

    /**
     * 유저 이미지가 존재하면 삭제하고 새로운 이미지로 교체 <br>
     * 없으면 이미지 등록 <br>
     *  <br>
     */
    public ResponseEntity<?> uploadUserImage(MultipartFile file, Map<String, String> header) {
        int code = headerTokenCheck(header);

        try {
            if(code == 0) {
                User user = userFindToHeader(header);

                result = user.getProfilePicture();

                dir = DIR_USER + "/" + user.getId();

                if(result != null){
                    delete(dir + "/" + result.substring(result.lastIndexOf("/") + 1));
                }

                try {
                    result = uploadStandBy(file, dir,
                            DIR_USER_PROFILE + "." + Objects.requireNonNull(file.getContentType())
                                    .substring(file.getContentType().lastIndexOf("/") + 1));

                    userRepository.UpdateUserImg(user, result);

                    return resultExport(result);
                } catch (NullPointerException e) {
                    return noFileUploadHandler();
                }
            } else {
                return singlePut(401, "code", code);
            }
        } catch (NullPointerException e) {
            return userNotFound();
        }
    }

    /**
     * 스틸 이미지 업로드 <br>
     *  <br>
     * 보드 작성유저가 아닐경우 거부 <br>
     * 보드가 승인된 경우 관리자만 가능 <br>
     *  <br>
     */
    public ResponseEntity<?> uploadStillImage(MultipartFile file, Long boardId,
                                              Map<String, String> header) {
        int code = headerTokenCheck(header);

        if(code == 0) {
            Board board = boardRepository.findBoardId(boardId);
            //board valid check fail
            if(board == null) return singlePut(404, "code", 4401);

            try {
                User user = userFindToHeader(header);

                if(!board.isApprove() && board.getUserId().getId() != user.getId())
                    return singlePut(403, "code", 4301);
                else if(board.isApprove() && !user.isAdminRole())
                    return singlePut(403, "code", 4300);

                dir = DIR_MOVIE + "/" + boardId + "/still";

                result = uploadStandBy(file, dir, UUID.randomUUID() + "-" + file.getOriginalFilename());

                //add DB still info
                imageRepository.addStillInfo(board, result);

                return resultExport(result);
            } catch (NullPointerException e) {
                return userNotFound();
            }
        } else {
            return singlePut(401, "code", code);
        }
    }

    /**
     * 캐스팅 이미지 업로드 <br>
     *  <br>
     * 보드 작성유저가 아닐경우 거부 <br>
     * 보드가 승인된 경우 관리자만 가능 <br>
     *  <br>
     */
    public ResponseEntity<?> uploadCastingImage(MultipartFile file, Long castingId,
                                                Map<String, String> header) {
        int code = headerTokenCheck(header);

        if(code == 0) {
            Casting casting = castingRepository.findCastingById(castingId);
            //board valid check fail
            if(casting == null) return singlePut(400,"code",4403);

            try {
                User user = userFindToHeader(header);

                try {
                    Board board = casting.getBoardId();
                    if(board == null) return singlePut(400,"code",4401);

                    if(!board.isApprove() && board.getUserId().getId() != user.getId())
                        return singlePut(403, "code", 4301);
                    else if(board.isApprove() && !user.isAdminRole())
                        return singlePut(403, "code", 4300);

                    dir = DIR_MOVIE + "/" + casting.getBoardId().getId() + "/casting";

                    if(casting.getImage() != null){
                        result = casting.getImage();
                        delete(dir + "/" + result.substring(result.lastIndexOf("/") + 1));
                    }

                    result = uploadStandBy(file, dir, castingId + "-" + file.getOriginalFilename());

                    //add DB casting info
                    castingRepository.updateCastingImage(casting, result);

                    return resultExport(result);
                } catch (NullPointerException e) {
                    return singlePut(400, "code", 4401);
                }
            } catch (NullPointerException e) {
                return userNotFound();
            }
        } else {
            return singlePut(401,"code",code);
        }
    }

    /**
     * 포스터 이미지 업로드 <br>
     *  <br>
     * 보드 작성유저가 아닐경우 거부 <br>
     * 보드가 승인된 경우 관리자만 가능 <br>
     *  <br>
     */
    public ResponseEntity<?> uploadPosterImage(MultipartFile file, Long boardId,
                                    Map<String, String> header){
        int code = headerTokenCheck(header);

        if(code == 0) {
            Board board = boardRepository.findBoardId(boardId);

            //board valid check fail
            if(board == null) return singlePut(400,"code",4401);

            result = board.getPosterImg();

            try {
                User user = userFindToHeader(header);

                if(!board.isApprove() && board.getUserId().getId() != user.getId())
                    return singlePut(403, "code", 4301);
                else if(board.isApprove() && !user.isAdminRole())
                    return singlePut(403, "code", 4300);

                dir = DIR_MOVIE + "/" + boardId;

                if(result != null){
                    delete(dir + "/" + result.substring(result.lastIndexOf("/") + 1));
                }

                try {
                    result = uploadStandBy(file, dir,
                            DIR_MOVIE_POSTER + "." + Objects.requireNonNull(file.getContentType())
                                    .substring(file.getContentType().lastIndexOf("/") + 1));

                    boardRepository.updateBoardImg(board, result);

                    return resultExport(result);
                } catch (NullPointerException e) {
                    return noFileUploadHandler();
                }
            } catch (NullPointerException e) {
                return userNotFound();
            }
        } else {
            return singlePut(401, "code", code);
        }
    }

    /**
     * 유저 이미지 삭제 <br>
     *  <br>
     */
    public ResponseEntity<?> deleteUserImage(Map<String, String> header) {
        int code = headerTokenCheck(header);

        try{
            if(code == 0) {
                User user = userFindToHeader(header);

                result = user.getProfilePicture();

                if(result != null) {
                    delete(DIR_USER + "/" + user.getId() + "/" + result.substring(result.lastIndexOf("/") + 1));
                    userRepository.UpdateUserImg(user, null);
                }

                return singlePut(200, "code", 2000);
            } else {
                return singlePut(401, "code", code);
            }
        } catch (NullPointerException e) {
            return userNotFound();
        }
    }

    /**
     * 스틸 이미지 삭제 <br>
     *  <br>
     * 보드 작성유저가 아닐경우 거부 <br>
     * 보드가 승인된 경우 관리자만 가능 <br>
     *  <br>
     */
    public ResponseEntity<?> deleteStill(Long id, Map<String, String> header) {
        int code = headerTokenCheck(header);

        try {
            if(code == 0) {
                User user = userFindToHeader(header);

                Still still = imageRepository.findStillById(id);
                if(still == null) return singlePut(404,"code",4402);

                try {
                    Board board = still.getBoardId();
                    if(board == null) return singlePut(400,"code",4401);

                    if(!board.isApprove() && board.getUserId().getId() != user.getId())
                        return singlePut(403, "code", 4301);
                    else if(board.isApprove() && !user.isAdminRole())
                        return singlePut(403, "code", 4300);

                    //S3 delete
                    dir = DIR_MOVIE + "/" + still.getBoardId().getId() + "/still/";
                    result = still.getImage();
                    delete(dir + result.substring(result.lastIndexOf("/") + 1));

                    //DB delete
                    imageRepository.deleteStill(still);

                    return singlePut(200, "code", 2000);
                } catch (NullPointerException e) {
                    return singlePut(400, "code", 4401);
                }
            } else {
                return singlePut(401, "code", code);
            }
        } catch (NullPointerException e) {
            return userNotFound();
        }
    }

    /**
     * 캐스팅 이미지 삭제 <br>
     *  <br>
     * 보드 작성유저가 아닐경우 거부 <br>
     * 보드가 승인된 경우 관리자만 가능 <br>
     *  <br>
     */
    public ResponseEntity<?> deleteCastingImage(Long id, Map<String, String> header) {
        int code = headerTokenCheck(header);

        try {
            if(code == 0) {
                User user = userFindToHeader(header);

                Casting casting = castingRepository.findCastingById(id);
                if(casting == null) return singlePut(400, "code", 4403);

                try {
                    Board board = casting.getBoardId();
                    if(board == null) return singlePut(400,"code",4401);

                    if(!board.isApprove() && board.getUserId().getId() != user.getId())
                        return singlePut(403, "code", 4301);
                    else if(board.isApprove() && !user.isAdminRole())
                        return singlePut(403, "code", 4300);

                    //S3 delete
                    dir = DIR_MOVIE + "/" + casting.getBoardId().getId() + "/casting/";
                    result = casting.getImage();

                    if(result != null) {
                        delete(dir + result.substring(result.lastIndexOf("/") + 1));
                        castingRepository.updateCastingImage(casting, null);
                    }

                    return singlePut(200, "code", 2000);
                } catch (NullPointerException e) {
                    return singlePut(400, "code", 4401);
                }
            } else {
                return singlePut(401, "code", code);
            }
        } catch (NullPointerException e) {
            return userNotFound();
        }
    }

    /**
     * 포스터 이미지 삭제 <br>
     *  <br>
     * 보드 작성유저가 아닐경우 거부 <br>
     * 보드가 승인된 경우 관리자만 가능 <br>
     *  <br>
     */
    public ResponseEntity<?> deletePosterImage(Long id, Map<String, String> header) {
        int code = headerTokenCheck(header);

        try {
            if(code == 0) {
                Board board = boardRepository.findBoardId(id);
                //board valid check fail
                if(board == null) return singlePut(404,"code", 4401);

                User user = userFindToHeader(header);

                if(!board.isApprove() && board.getUserId().getId() != user.getId())
                    return singlePut(403, "code", 4301);
                else if(board.isApprove() && !user.isAdminRole())
                    return singlePut(403, "code", 4300);

                result = board.getPosterImg();

                dir = DIR_MOVIE + "/" + id + "/";

                if(result != null) {
                    delete(dir + result.substring(result.lastIndexOf("/") + 1));
                    boardRepository.updateBoardImg(board,null);
                }

                return singlePut(200, "code", 2000);
            } else {
                return singlePut(401, "code", code);
            }
        } catch (NullPointerException e) {
            return userNotFound();
        }
    }
}
