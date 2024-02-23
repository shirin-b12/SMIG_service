package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.model.Tag;
import org.acme.repository.TagRepository;

import java.util.List;

@ApplicationScoped
public class TagService {

    @Inject
    TagRepository tagRepository;

    public List<Tag> listAll() {
        return tagRepository.listAll();
    }

    public Tag createTag(Tag tag) {
        if (tag != null && tag.getNom_tag() != null && !tag.getNom_tag().isEmpty()) {
            tagRepository.persist(tag);
            return tag;
        }
        return null;
    }
}
