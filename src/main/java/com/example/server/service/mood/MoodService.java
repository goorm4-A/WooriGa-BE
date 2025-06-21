package com.example.server.service.mood;

import com.example.server.domain.entity.*;
import com.example.server.dto.mood.MoodRequestDTO;
import com.example.server.dto.mood.MoodResponseDTO;
import com.example.server.global.code.exception.CustomException;
import com.example.server.global.status.ErrorStatus;
import com.example.server.repository.*;
import com.example.server.repository.mood.FamilyMoodRepository;
import com.example.server.repository.mood.MoodTagRepository;
import com.example.server.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MoodService {
    private final FamilyMoodRepository familyMoodRepository;
    private final MoodTagRepository moodTagRepository;
    private final TagRepository tagRepository;
    private final TagService tagService;
    private final FamilyMemberRepository familyMemberRepository;

    protected List<String> parseTags(String tags){
        if(tags == null || tags.isBlank()) return List.of();
        return Arrays.stream(tags.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(s -> s.startsWith("#") ? s.substring(1) : s)
                .collect(Collectors.toList());
    }

    public MoodResponseDTO createMood(User user, Long familyId, MoodRequestDTO dto){
        FamilyMember member = familyMemberRepository.findByUserIdAndFamilyId(user.getId(), familyId)
                .orElseThrow(() -> new CustomException(ErrorStatus.FAMILYMEMBER_NOT_FOUND));

        FamilyMood mood = new FamilyMood();
        mood.setUser(user);
        mood.setFamilyMember(member);
        mood.setMoodType(dto.getMoodType());
        familyMoodRepository.save(mood);

        List<String> tagNames = parseTags(dto.getTags());
        for(String name : tagNames){
            Tag tag = tagRepository.findByName(name)
                    .orElseGet(() -> {
                        Long id = tagService.makeNewTag(name);
                        return tagRepository.findById(id).orElse(null);
                    });
            if(tag == null) throw new CustomException(ErrorStatus.TAG_NOT_FOUND);
            MoodTag moodTag = new MoodTag();
            moodTag.setTag(tag);
            moodTag.setFamilyMood(mood);
            moodTagRepository.save(moodTag);
            mood.getMoodTags().add(moodTag);
        }
        return MoodResponseDTO.fromEntity(mood);
    }

    public List<MoodResponseDTO> getFamilyMoods(Long familyId){
        List<FamilyMood> moods = familyMoodRepository.findAllByFamilyMember_Family_Id(familyId);
        return moods.stream()
                .map(MoodResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public void deleteMood(User user, Long familyId, Long moodId){
        FamilyMember member = familyMemberRepository.findByUserIdAndFamilyId(user.getId(), familyId)
                .orElseThrow(() -> new CustomException(ErrorStatus.FAMILY_MEMBER_NOT_FOUND));

        FamilyMood mood = familyMoodRepository.findById(moodId)
                .orElseThrow(() -> new CustomException(ErrorStatus.MOOD_NOT_FOUND));

        if(!mood.getFamilyMember().getId().equals(member.getId())){
            throw new CustomException(ErrorStatus.MOOD_NOT_FOUND);
        }

        familyMoodRepository.delete(mood);
    }
}
