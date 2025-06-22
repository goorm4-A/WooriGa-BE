package com.example.server.service.event;

import com.example.server.converter.FamilyEventConverter;
import com.example.server.domain.entity.Family;
import com.example.server.domain.entity.FamilyEvent;
import com.example.server.domain.entity.FamilyMember;
import com.example.server.domain.entity.User;
import com.example.server.dto.familyEvent.FamilyEventRequest;
import com.example.server.dto.familyEvent.FamilyEventResponse;
import com.example.server.dto.familyEvent.FamilyEventTimelineDto;
import com.example.server.global.code.exception.CustomException;
import com.example.server.global.status.ErrorStatus;
import com.example.server.repository.FamilyMemberRepository;
import com.example.server.repository.FamilyRepository;
import com.example.server.repository.event.FamilyEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FamilyEventService {

    private final FamilyRepository familyRepository;
    private final FamilyMemberRepository familyMemberRepository;
    private final FamilyEventRepository familyEventRepository;

    @Transactional
    public FamilyEventResponse createFamilyEvent(User user, FamilyEventRequest request) {
        log.info(request.getFamilyName());
        log.info(request.getTitle());
        log.info(request.getLatitude());
        log.info(request.getLongitude());
        log.info(request.toString());
        Family family = familyRepository.findByName(request.getFamilyName())
                .orElseThrow(() -> new CustomException(ErrorStatus.FAMILY_NOT_FOUND));

        FamilyMember member = familyMemberRepository.findByUserIdAndFamilyId(user.getId(), family.getId())
                .orElseThrow(() -> new CustomException(ErrorStatus.FAMILY_MEMBER_NOT_FOUND));

        FamilyEvent event = FamilyEventConverter.toFamilyEvent(request,user,member);
        familyEventRepository.save(event);
        return FamilyEventConverter.toFamilyEventResponse(event);
    }


    @Transactional(readOnly = true)
    public List<FamilyEventTimelineDto> getFamilyEventTimeline(User user, Long familyId) {
        familyMemberRepository.findByUserIdAndFamilyId(user.getId(), familyId)
                .orElseThrow(() -> new CustomException(ErrorStatus.FAMILY_MEMBER_NOT_FOUND));
        List<FamilyEvent> events = familyEventRepository
                .findAllByFamilyMember_Family_IdOrderByTimelineDesc(familyId);
        return events.stream()
                .map(FamilyEventTimelineDto::fromEntity)
                .collect(Collectors.toList());
    }
}
