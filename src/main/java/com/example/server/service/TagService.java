package com.example.server.service;

import com.example.server.domain.entity.Tag;
import com.example.server.repository.TagRepository;
import org.springframework.stereotype.Service;

@Service
public class TagService {

    private final TagRepository tagRepository;
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    //새로운 Tag Entity 만들기
    public Long makeNewTag(String tagName){
        Tag tag=new Tag(tagName);
        tagRepository.save(tag);
        return tag.getId();
    }
}
