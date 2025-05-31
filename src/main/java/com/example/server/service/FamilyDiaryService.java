package com.example.server.service;

import com.example.server.domain.entity.*;
import com.example.server.dto.familyDiary.FamilyDiaryDto;
import com.example.server.dto.familyDiary.FamilyDiaryResponseDto;
import com.example.server.global.code.exception.CustomException;
import com.example.server.global.status.ErrorStatus;
import com.example.server.domain.entity.FamilyDiary;
import com.example.server.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FamilyDiaryService {

    private final FamilyDiaryRepository familyDiaryRepository;
    private final S3Service s3Service;
    private final DiaryImgService diaryImgService;
    private final FamilyMemberRepository familyMemberRepository;
    private final DiaryParticipantRepository diaryParticipantRepository;
    private final UserRepository userRepository;
    private final FamilyRepository familyRepository;

    public FamilyDiaryService(FamilyDiaryRepository familyDiaryRepository, S3Service s3Service, DiaryImgService diaryImgService, FamilyMemberRepository familyMemberRepository, DiaryParticipantRepository diaryParticipantRepository, UserRepository userRepository, FamilyRepository familyRepository) {
        this.familyDiaryRepository = familyDiaryRepository;
        this.s3Service = s3Service;
        this.diaryImgService = diaryImgService;
        this.familyMemberRepository = familyMemberRepository;
        this.diaryParticipantRepository = diaryParticipantRepository;
        this.userRepository = userRepository;
        this.familyRepository = familyRepository;
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

//        //DiaryTag 매핑
//        try{
//            List<DiaryTag> tags=getDiaryTags(dto,id);
//            familyDiary.setDiaryParticipants(participants);
//        }catch(Exception e){
//            throw new CustomException(ErrorStatus.DIARY_PARTICIPANTS_ERROR);
//        }


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
//    private List<DiaryTag> getDiaryTags(FamilyDiaryDto dto,Long id){
//
//        List<DiaryTag> tagList=new ArrayList<>();
//        FamilyDiary diary=findDiary(id);
//
//
//
//    }

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



}
