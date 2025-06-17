package com.example.server.service.diary;

import com.example.server.domain.entity.DiaryImg;
import com.example.server.domain.entity.FamilyDiary;
import com.example.server.repository.diary.DiaryImgRepository;
import com.example.server.repository.diary.FamilyDiaryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiaryImgService {

    private final DiaryImgRepository diaryImgRepository;
    private final FamilyDiaryRepository familyDiaryRepository;
    public DiaryImgService(DiaryImgRepository diaryImgRepository,FamilyDiaryRepository familyDiaryRepository) {
        this.diaryImgRepository = diaryImgRepository;
        this.familyDiaryRepository = familyDiaryRepository;
    }

    //DiaryImg table에 imgUrl 업로드
    public List<DiaryImg> createDiaryImg (List<String> imgUrlList,Long familyDiaryId){

        FamilyDiary diary=familyDiaryRepository.getReferenceById(familyDiaryId);
        List<DiaryImg> imgList=new ArrayList<DiaryImg>();

        for(String imgUrl : imgUrlList){
            DiaryImg diaryImg = new DiaryImg(imgUrl,diary);
            System.out.println("✅"+diaryImg);
            diaryImgRepository.save(diaryImg);

            imgList.add(diaryImg);
        }

        return imgList;

    }


}
