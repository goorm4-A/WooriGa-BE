package com.example.server.converter;

import com.example.server.domain.entity.FamilyEvent;
import com.example.server.domain.entity.FamilyMember;
import com.example.server.domain.entity.User;
import com.example.server.dto.familyEvent.FamilyEventRequest;
import com.example.server.dto.familyEvent.FamilyEventResponse;

public class FamilyEventConverter {

    public static FamilyEvent toFamilyEvent(FamilyEventRequest request, User user, FamilyMember member) {
        return FamilyEvent.builder()
                .title(request.getTitle())
                .location(request.getLocation())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .timeline(request.getDate())
                .user(user)
                .familyMember(member)
                .build();
    }

    public static FamilyEventResponse toFamilyEventResponse(FamilyEvent event) {
        return FamilyEventResponse.builder()
                .eventId(event.getId())
                .familyName(event.getFamilyMember().getFamily().getName())
                .title(event.getTitle())
                .date(event.getTimeline())
                .location(event.getLocation())
                .latitude(event.getLatitude())
                .longitude(event.getLongitude())
                .build();
    }
}
