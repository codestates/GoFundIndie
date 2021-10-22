package com.IndieAn.GoFundIndie.Service;

import com.IndieAn.GoFundIndie.Domain.Entity.Board;
import com.IndieAn.GoFundIndie.Repository.BoardRepository;
import com.IndieAn.GoFundIndie.Repository.ImageRepository;
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
    private final BoardRepository boardRepository;
    private final ImageRepository imageRepository;
    private final AmazonS3Client amazonS3Client;
    private final AmazonS3 amazonS3;

    private Board board = null;
    private String result;

    @Value("#{info['gofundindie.s3.bucket']}")
    private String bucket;

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

    private void delete(String key) {
        try {
            DeleteObjectRequest dor = new DeleteObjectRequest(bucket, key);
            this.amazonS3.deleteObject(dor);
            log.info("s3 file delete ok");
        } catch (AmazonServiceException e) {
            log.info("s3 file delete fail");
        }
    }

    public String uploadUserImage(MultipartFile file, Long userId, String dir, String USER_PROFILE) {
//        user = userRepository.findUserId(userId);

//        if(user == null) return "NullPointException";

//        if(user.getPosterImg() != null){
//            delete(dir + "/" + USER_PROFILE);
//        }

        return uploadStandBy(file, dir, USER_PROFILE);
    }

    public String uploadStillImage(MultipartFile file, Long boardId, String dir) {
        board = boardRepository.findBoardId(boardId);

        //board valid check fail
        if(board == null) return "NullPointException";

        result = uploadStandBy(file, dir, UUID.randomUUID() + "-" + file.getOriginalFilename());

        //add DB still info
        imageRepository.addStillInfo(board, result);

        return result;
    }

    public String uploadCastingImage(MultipartFile file, Long boardId, String dir) {
        board = boardRepository.findBoardId(boardId);

        //board valid check fail
        if(board == null) return "NullPointException";

        result = uploadStandBy(file, dir, UUID.randomUUID() + "-" + file.getOriginalFilename());

        //add DB casting info
        imageRepository.addCastingInfo(board, result);

        return result;
    }

    public String uploadPosterImage(MultipartFile file, Long boardId, String dir, String MOVIE_POSTER){
        board = boardRepository.findBoardId(boardId);

        //board valid check fail
        if(board == null) return "NullPointException";

        if(board.getPosterImg() != null){
            delete(dir + "/" + MOVIE_POSTER);
        }

        //only one poster image is possible
        return uploadStandBy(file, dir, MOVIE_POSTER);
    }
}
