package com.example.user.common.service;

import com.example.user.common.entity.Subject;
import com.example.user.common.repository.SubjectRepository;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private static List<String> subjectList = new ArrayList<>();


    /* 모든 과목 저장 */
    public void saveAllSubject() throws IOException, URISyntaxException {
        subjectList = Files.readAllLines(Paths.get(getClass().getResource("/static/subject/subjectList.txt").toURI()));

        for (String str : subjectList) {
            Subject subject = Subject.builder().subject(str).build();
            subjectRepository.save(subject);
        }
    }
}
