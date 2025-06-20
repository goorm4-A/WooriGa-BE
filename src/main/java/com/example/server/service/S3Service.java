package com.example.server.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.server.global.code.exception.CustomException;
import com.example.server.global.status.ErrorStatus;
import com.example.server.repository.diary.DiaryImgRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class S3Service {

    private final AmazonS3 s3Client;
    private final DiaryImgRepository diaryImgRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public S3Service(AmazonS3 s3Client, DiaryImgRepository diaryImgRepository) {
        this.s3Client = s3Client;
        this.diaryImgRepository = diaryImgRepository;
    }

    //이미지 업로드
    public List<String> upload(List<MultipartFile> multipartFiles) {
        List<String> imgUrlList=new ArrayList<>();

        //forEach 구문을 통해 multipartFile로 넘어온 파일을 하나씩 fileNameList에 추가
        for (MultipartFile multipartFile : multipartFiles) {

            String fileName=createFileName(multipartFile.getOriginalFilename());
            //수정
            String key="post/image"+fileName; //객체 키(파일 경로)

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(multipartFile.getContentType());
            objectMetadata.setContentLength(multipartFile.getSize());

            try(InputStream inputStream= multipartFile.getInputStream()){
                s3Client.putObject(new PutObjectRequest(bucket,key,inputStream,objectMetadata));

                String imgUrl=s3Client.getUrl(bucket,key).toString();
                imgUrlList.add(imgUrl);


            }catch(IOException e){
                throw new CustomException(ErrorStatus.IMAGE_UPLOAD_ERROR);
            }
        }
        return imgUrlList;
    }

    //이미지 파일명 중복 방지
    private String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    //파일 유효성 검사
    private String getFileExtension(String fileName) {
        if(fileName.isEmpty()){
            throw new CustomException(ErrorStatus.WRONG_INPUT_IMAGE);
        }

        ArrayList<String> fileValidate=new ArrayList<>();
        fileValidate.add(".jpg");
        fileValidate.add(".jpeg");
        fileValidate.add(".png");
        fileValidate.add(".JPG");
        fileValidate.add(".JPEG");
        fileValidate.add(".PNG");
        //마지막 점 이후의 문자열을 잘라냄
        String idxFileName=fileName.substring(fileName.lastIndexOf("."));
        if(!fileValidate.contains(idxFileName)){
            throw new CustomException(ErrorStatus.WRONG_IMAGE_FORMAT);
        }
        return idxFileName;
    }

    // 디렉토리별 단일 이미지 저장
    public String uploadImage(MultipartFile image, String directory) throws IOException {
        // 디렉토리 포함 + 고유한 파일 이름 생성
        String fileName = directory + "/" + UUID.randomUUID() + "_" + image.getOriginalFilename();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(image.getContentType());
        metadata.setContentLength(image.getSize());

        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, fileName, image.getInputStream(), metadata);
        s3Client.putObject(putObjectRequest);

        return getPublicUrl(fileName);
    }

    /**
     * S3에 이미지 업로드 하기
     */
    ////////// TEST 용
    public String uploadImage(MultipartFile image) throws IOException {
        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename(); // 고유한 파일 이름 생성

        // 메타데이터 설정
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(image.getContentType());
        metadata.setContentLength(image.getSize());

        // S3에 파일 업로드 요청 생성
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, fileName, image.getInputStream(), metadata);

        // S3에 파일 업로드
        s3Client.putObject(putObjectRequest);

        return getPublicUrl(fileName);
    }

    private String getPublicUrl(String fileName) {
        return String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, s3Client.getRegionName(), fileName);
    }
    ///////////


    //일기 삭제 -> 버킷에서 이미지 삭제
    public void deleteFile(String filename){
        try{
            s3Client.deleteObject(bucket,filename);
        }catch(SdkClientException e){
            throw new CustomException(ErrorStatus.S3_DELETE_ERROR);
        }
    }
}
