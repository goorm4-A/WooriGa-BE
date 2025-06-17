package com.example.server.service.diary;

import com.example.server.domain.entity.DiaryTag;
import com.example.server.domain.entity.FamilyDiary;
import com.example.server.domain.entity.Tag;
import com.example.server.global.code.exception.CustomException;
import com.example.server.global.status.ErrorStatus;
import com.example.server.repository.diary.DiaryTagRepository;
import com.example.server.repository.diary.FamilyDiaryRepository;
import com.example.server.repository.TagRepository;
import com.example.server.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DiaryTagService {

    private final TagRepository tagRepository;
    private final TagService tagService;
    private final DiaryTagRepository diaryTagRepository;
    private final FamilyDiaryRepository familyDiaryRepository;

    public DiaryTagService(TagRepository tagRepository, TagService tagService, DiaryTagRepository diaryTagRepository, FamilyDiaryRepository familyDiaryRepository) {
        this.tagRepository = tagRepository;
        this.tagService = tagService;
        this.diaryTagRepository = diaryTagRepository;
        this.familyDiaryRepository = familyDiaryRepository;
    }

    //이미 존재하는 Tag인지 name을 통해 찾기
    //존재 -> TagID , 미존재 -> false
    public List<Map<String,Long>> getTagExistenceOrId(List<String> tagNames){
        return tagNames.stream()
                .map(name->{
                    Optional<Tag> optionalTag=tagRepository.findByName(name);
                    System.out.println("🧹optionalTag:"+optionalTag);
                    Long value=optionalTag.map(Tag::getId).orElse(0L);
                    System.out.println("🧹value:"+value);
                    return Map.of(name,value);
                })
                .collect(Collectors.toList());

    }

    //존재X Tag들에 대해 새로운 entity 생성
    public List<Map<String,Long>> getNewVersionTagList(List<Map<String,Long>> tagExistences){

        //새로 생성해야 하는 이름의 tag 생성 후, null 대신 tagId 추가
        return tagExistences.stream()
                .map(map->{
                    Map.Entry<String,Long> entry=map.entrySet().iterator().next();
                    String tagName=entry.getKey();
                    Long value=entry.getValue();

                    Map<String,Long> newMap;
                    if(value.equals(0L)){
                        Long newTagId=tagService.makeNewTag(tagName); //새 태그 만들고 ID 얻기
                        newMap=Map.of(tagName,newTagId);
                    }else{
                        newMap=Map.of(tagName,value); //기존에 Object -> Long 타입으로 캐스팅
                    }
                    return newMap;
                })
                .collect(Collectors.toList());

    }


    //기존에 존재 + 새로 생성한 Tag 모두 DiaryTag 엔티티에 저장 //<tagName,tagId>
    public List<DiaryTag> saveDiaryTag(List<Map<String,Long>> newVersionTagList, Long id){

        FamilyDiary familyDiary=familyDiaryRepository.findById(id)
                        .orElseThrow(()->new CustomException(ErrorStatus.FAMILY_DIARY_NOT_FOUND));

        newVersionTagList.forEach(map->{

                    Map.Entry<String,Long> entry=map.entrySet().iterator().next();
                    Long value=entry.getValue();
                    Tag tag=tagRepository.findById(value)
                            .orElseThrow(()->new CustomException(ErrorStatus.TAG_NOT_FOUND));

                    DiaryTag diaryTag=new DiaryTag();
                    diaryTag.setTag(tag);
                    //FamilyDiary Id도 set 해줘야✏️
                    diaryTag.setFamilyDiary(familyDiary);

                    diaryTagRepository.save(diaryTag);

                });

        List<DiaryTag> diaryTags=diaryTagRepository.findAllByFamilyDiaryId(id);
//        List<DiaryTagDto> diaryTagDtos=diaryTags.stream()
//                .map(DiaryTagDto::new)
//                .toList();
        return diaryTags;

    }


}
