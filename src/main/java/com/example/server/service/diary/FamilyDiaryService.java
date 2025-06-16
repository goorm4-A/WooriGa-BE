package com.example.server.service.diary;

import com.example.server.domain.entity.*;
import com.example.server.dto.familyDiary.DiaryTagDto;
import com.example.server.dto.familyDiary.FamilyDiaryDto;
import com.example.server.dto.familyDiary.FamilyDiaryListDto;
import com.example.server.dto.familyDiary.FamilyDiaryResponseDto;
import com.example.server.global.code.exception.CustomException;
import com.example.server.global.status.ErrorStatus;
import com.example.server.domain.entity.FamilyDiary;
import com.example.server.repository.*;
import com.example.server.service.S3Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FamilyDiaryService {

    private final FamilyDiaryRepository familyDiaryRepository;
    private final S3Service s3Service;
    private final DiaryImgService diaryImgService;
    private final FamilyMemberRepository familyMemberRepository;
    private final DiaryParticipantRepository diaryParticipantRepository;
    private final UserRepository userRepository;
    private final DiaryTagService diaryTagService;
    private final FamilyRepository familyRepository;

    public FamilyDiaryService(FamilyDiaryRepository familyDiaryRepository, S3Service s3Service,
                              DiaryImgService diaryImgService, FamilyMemberRepository familyMemberRepository,
                              DiaryParticipantRepository diaryParticipantRepository, UserRepository userRepository,
                              FamilyRepository familyRepository,DiaryTagService diaryTagService) {
        this.familyDiaryRepository = familyDiaryRepository;
        this.s3Service = s3Service;
        this.diaryImgService = diaryImgService;
        this.familyMemberRepository = familyMemberRepository;
        this.diaryParticipantRepository = diaryParticipantRepository;
        this.userRepository = userRepository;
        this.familyRepository = familyRepository;
        this.diaryTagService=diaryTagService;
    }



    ///다이어리 생성
    public FamilyDiaryResponseDto createDiary(FamilyDiaryDto dto, List<MultipartFile> image) {

        
        //diaryTag, diaryParticipant 매핑하기

        //FamilyDiary 생성
        FamilyDiary familyDiary=FamilyDiaryDto.fromDto(dto);
        familyDiary.setWrittenDate(LocalDateTime.now());
        //FamilyMember 매핑 ⭐⭐
        FamilyMember member=getFamilyMember(dto.getFamilyId(),dto.getUserId());
        familyDiary.setFamilyMember(member);


        familyDiaryRepository.save(familyDiary); //ID 확보 위해 먼저 저장
        Long id=familyDiary.getId();

        ///다른 테이블과의 매핑

        //DiaryParticipant 매핑
        try{
            List<DiaryParticipant> participants=getDiaryParticipants(dto,id);
            familyDiary.setDiaryParticipants(participants);
        }catch(Exception e){
            throw new CustomException(ErrorStatus.DIARY_PARTICIPANTS_ERROR);
        }

        //DiaryTag 매핑
        try{
            List<DiaryTag> diaryTags=getDiaryTags(dto,id);
            System.out.println("🧹diaryTag 서비스 시작");
            familyDiary.setDiaryTags(diaryTags);
        }catch(Exception e){
            throw new CustomException(ErrorStatus.DIARY_TAG_ERROR);
        }


        //s3에 이미지 업로드
        // s3에 이미지 업로드
        try {
            List<DiaryImg> imgs = uploadDiaryImages(familyDiary, image);
            familyDiary.setImages(imgs);
        } catch (Exception e) {
            log.error("이미지 업로드 실패", e); // 예외 로그를 남김
            throw new CustomException(ErrorStatus.IMAGE_UPLOAD_ERROR);
        }



        //이미지 연관관계 반영 후 다시 저장
        familyDiaryRepository.save(familyDiary);
        return FamilyDiary.toDto(familyDiary);

    }

    //이미지 url 리스트 저장
    private List<DiaryImg> uploadDiaryImages(FamilyDiary familyDiary, List<MultipartFile> imageFiles) {
        List<String> imgUrls = s3Service.upload(imageFiles);
        return diaryImgService.createDiaryImg(imgUrls, familyDiary.getId());
    }

    //유저의 FamilyMemberId 찾기
    private FamilyMember getFamilyMember(Long familyId,Long userId){
        return familyMemberRepository.findByUserIdAndFamilyId(userId,familyId)
            .orElseThrow(()->new CustomException(ErrorStatus.FAMILY_MEMBER_NOT_FOUND));

    }

    //DiaryTag 객체 생성+저장 //Tag 작성 방식에 따라 변경
    private List<DiaryTag> getDiaryTags(FamilyDiaryDto dto,Long id){
        System.out.println("🧹getDiaryTags 호출");
        List<String> tags=dto.getDiaryTags();
        System.out.println("🧹tags:"+tags);

        //이미 존재하는 tag인지 아닌지 판단(존재O -> id, 존재X-> null)
        List<Map<String,Long>> tagExistences=diaryTagService.getTagExistenceOrId(tags);
        System.out.println("🧹tagExistence:"+tagExistences);

        //null인 tag들에 대해 새로운 Tag 객체 생성 후 Id와 함꼐 반환
        List<Map<String,Long>> newVersionTagList=diaryTagService.getNewVersionTagList(tagExistences);
        System.out.println("🍒newVersionTagList:"+newVersionTagList);

        //DiaryTag entity에 저장
        List<DiaryTag> diaryTagDtos=diaryTagService.saveDiaryTag(newVersionTagList,id);
        System.out.println("✅DiaryTag에 대한 모든 과정 완료!!");

        return diaryTagDtos;

    }




    //DiaryParticipant 객체 생성+저장
    private List<DiaryParticipant> getDiaryParticipants(FamilyDiaryDto dto,Long diaryId){

        List<DiaryParticipant> participantList=new ArrayList<>();
        FamilyDiary diary=findDiary(diaryId);

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new CustomException(ErrorStatus.USER_NOT_FOUND));


        //dto속 List<Long> DiaryParticipantsId 가져와서
        List<Long> participantsId=dto.getDiaryParticipants();
        for(Long participantID:participantsId){

            //DiaryParticipant 객체 생성
            DiaryParticipant diaryParticipant=new DiaryParticipant();
            diaryParticipant.setFamilyDiary(diary);
            diaryParticipant.setUser(user);

            FamilyMember familyMember=familyMemberRepository.findById(participantID).orElse(null);
            diaryParticipant.setFamilyMember(familyMember);

            diaryParticipantRepository.save(diaryParticipant);

            participantList.add(diaryParticipant);
        }

        return participantList;
    }


    //diaryID를 통해 Diary 객체 찾기
    private FamilyDiary findDiary(Long diaryId){
        return familyDiaryRepository.findById(diaryId).orElse(null);
    }


    ///가족별 추억일기 조회
//    public List<FamilyDiaryListDto> getDiaryList(Long familyId){
//        Family family=familyRepository.findById(familyId).orElse(null);
//
//        //family가 null이면 안됨
//        List<FamilyDiary> diaryList= Objects.requireNonNull(family).getFamilyDiaries();
//
//        List<FamilyDiaryListDto> familyDiaryListDtos=diaryList.stream()
//                .map(diaryLi)
//
//
//    }


    //특정 가족일기 조회
    public FamilyDiaryResponseDto getFamilyDiaryDto(Long diaryId){
        FamilyDiary diary=familyDiaryRepository.findById(diaryId)
                .orElseThrow(()-> new CustomException(ErrorStatus.FAMILY_DIARY_NOT_FOUND));
        return FamilyDiary.toDto(diary);
    }

    //추억 목록 조회
    public List<FamilyDiaryListDto> getFamilyDiaryListDto(Long familyId){
        List<FamilyDiary> diaryList=familyDiaryRepository.findByFamilyId(familyId);
        System.out.println("✅diaryList="+diaryList); //비어 있음
        if(diaryList==null){
            return null;
        }
        List<FamilyDiaryListDto> dtoList=FamilyDiaryListDto.toDto(diaryList);
        System.out.println("✅dtoList="+dtoList); //비어 있음
        return dtoList;
    }

    //가족일기 삭제
    public void deleteDiary(Long familyDiaryId){
        FamilyDiary diary=familyDiaryRepository.findById(familyDiaryId)
                .orElseThrow(()-> new CustomException(ErrorStatus.FAMILY_DIARY_NOT_FOUND));

        //S3 이미지 삭제 기능
        //일기 하나에 저장된 여러 개 이미지의 imgUrl List로 만들기
        List<String> images=diary.getImages().stream()
                        .map(DiaryImg::getImgUrl)
                        .toList();
        System.out.println("🪧images:"+images);
        //images 리스트에 저장된 파일명 가져오기
        List<String> fileNames=images.stream()
                        .map(filename->filename.split("/")[3]) //imgUrl에서 4번째 부분(인덱스 3)을 추출
                        .toList();
        System.out.println("🪧file names:"+fileNames);

        try{
            //S3에서 이미지 삭제하는 메서드 호출
            fileNames.forEach(s3Service::deleteFile);
        }catch(Exception e){
            log.error("🚨S3이미지 삭제 실패",e);
            throw new CustomException(ErrorStatus.IMAGE_DELETE_FAILED);
        }


        //DB에서 삭제
        try{
            familyDiaryRepository.delete(diary);
        }catch(Exception e){
            log.error("🚨가족 일기 db 삭제 실패",e);
            throw new CustomException(ErrorStatus.IMAGE_DELETE_FAILED);
        }


    }

    //가족일기 검색
    public List<FamilyDiaryListDto> searchFamilyDiary(String keyword,Long familyId){
        List<FamilyDiary> diaryList=familyDiaryRepository.findByFamilyIdAndTitleContaining(familyId,keyword);
        if(diaryList==null){
            return null;
        }
        return FamilyDiaryListDto.toDto(diaryList);
    }

//    //가족일기 수정
//    public FamilyDiaryResponseDto updateDiary(FamilyDiaryResponseDto dto, List<MultipartFile> image){
//        Long diaryId=dto.getDiaryId();
//        familyDiaryRepository.findById(diaryId)
//                .orElseThrow(()-> new CustomException(ErrorStatus.FAMILY_DIARY_NOT_FOUND));
//        if(dto.)
//    }



}
