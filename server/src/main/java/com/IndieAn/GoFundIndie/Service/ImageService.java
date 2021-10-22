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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {
    private final CastingRepository castingRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final ImageRepository imageRepository;
    private final AmazonS3Client amazonS3Client;
    private final AmazonS3 amazonS3;

    private Board board = null;
    private String result;
    private String dir;

    @Value("#{info['gofundindie.s3.bucket']}")
    private final String bucket;

    @Value("#{info['dir.movie']}")
    private final String DIR_MOVIE;

    @Value("#{info['dir.movie.poster']}")
    private final String DIR_MOVIE_POSTER;

    @Value("#{info['dir.user']}")
    private final String DIR_USER;

    @Value("#{info['dir.user.profile']}")
    private final String DIR_USER_PROFILE;

    //convert multipart file -> file
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

    //s3 file upload & url return
    private String upload(File file, String dirName, String oriFileName) {
        String fileName = dirName + "/" + oriFileName;
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, file)
                      .withCannedAcl(CannedAccessControlList.PublicRead));
        removeNewFile(file);
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    //local file delete
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("local file removed");
        } else {
            log.info("local file remove fail");
        }
    }

    //upload standBy
    private String uploadStandBy(MultipartFile multipartFile, String dirName, String oriFileName) {
        try{
            File file = convert(multipartFile)
                       .orElseThrow(() -> new IllegalArgumentException("file convert fail"));
            return upload(file, dirName, oriFileName);
        } catch (IOException e) {
            return "IOException";
        }
    }

    //S3 file delete
    private void delete(String key) {
        try {
            DeleteObjectRequest dor = new DeleteObjectRequest(bucket, key);
            this.amazonS3.deleteObject(dor);
            log.info("s3 file delete ok");
        } catch (AmazonServiceException e) {
            log.info("s3 file delete fail");
        }
    }

    public String uploadUserImage(MultipartFile file, User user) {
//        user = userRepository.findUserId(userId);
        result = user.getProfilePicture();

        if(user == null) return "NullPointException";

        dir = DIR_USER + "/" + user.getId();

        if(result != null){
            delete(dir + "/" + result.substring(result.lastIndexOf("/" + 1)));
        }

        return uploadStandBy(file, dir,
                DIR_USER_PROFILE + file.getContentType()
                        .substring(file.getContentType().lastIndexOf("/" + 1)));
    }

    public String uploadStillImage(MultipartFile file, Long boardId) {
        board = boardRepository.findBoardId(boardId);

        //board valid check fail
        if(board == null) return "NullPointException";

        dir = DIR_MOVIE + "/" + boardId + "/still";

        result = uploadStandBy(file, dir, UUID.randomUUID() + "-" + file.getOriginalFilename());

        //add DB still info
        imageRepository.addStillInfo(board, result);

        return result;
    }

    public String uploadCastingImage(MultipartFile file, Long boardId) {
        board = boardRepository.findBoardId(boardId);

        //board valid check fail
        if(board == null) return "NullPointException";

        dir = DIR_MOVIE + "/" + boardId + "/casting";

        result = uploadStandBy(file, dir, UUID.randomUUID() + "-" + file.getOriginalFilename());

        //add DB casting info
        castingRepository.addCastingInfo(board, result);

        return result;
    }

    public String uploadPosterImage(MultipartFile file, Long boardId){
        board = boardRepository.findBoardId(boardId);
        result = board.getPosterImg();

        //board valid check fail
        if(board == null) return "NullPointException";

        dir = DIR_MOVIE + "/" + boardId;

        if(result != null){
            delete(dir + "/" + result.substring(result.lastIndexOf("/" + 1)));
        }

        result = uploadStandBy(file, dir,
                DIR_MOVIE_POSTER + file.getContentType()
                        .substring(file.getContentType().lastIndexOf("/" + 1)));

        boardRepository.updateBoardImg(board, result);

        return result;
    }

    public boolean deleteUserImage(User user) {
        result = user.getProfilePicture();

        if(user == null) return false;

        if(result != null) {
            delete(DIR_USER + "/" + user.getId() + "/" + result.substring(result.lastIndexOf("/" + 1)));
            userRepository.UpdateUserImg(user, null);
        }

        return true;
    }

    public boolean deleteStill(Long id) {
        Still still = imageRepository.findStillById(id);

        if(still == null) return false;

        //S3 delete
        dir = DIR_MOVIE + "/" + still.getBoardId().getId() + "/still/";
        result = still.getImage();
        delete(dir + result.substring(result.lastIndexOf("/" + 1)));

        //DB delete
        imageRepository.deleteStill(still);

        return true;
    }

    public boolean deleteCastingImage(Long id) {
        Casting casting = castingRepository.findCastingById(id);

        if(casting == null) return false;

        //S3 delete
        dir = DIR_MOVIE + "/" + casting.getBoardId().getId() + "/casting/";
        result = casting.getImage();
        delete(dir + result.substring(result.lastIndexOf("/" + 1)));

        return true;
    }

    public boolean deletePosterImage(Long id) {
        board = boardRepository.findBoardId(id);
        result = board.getPosterImg();

        //board valid check fail
        if(board == null) return false;

        dir = DIR_MOVIE + "/" + id + "/";

        if(result != null) {
            delete(dir + result.substring(result.lastIndexOf("/" + 1)));
            boardRepository.updateBoardImg(board,null);
        }

        return true;
    }
}
