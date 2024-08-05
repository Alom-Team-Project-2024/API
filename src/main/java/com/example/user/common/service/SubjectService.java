package com.example.user.common.service;

import com.example.user.common.entity.Subject;
import com.example.user.common.repository.SubjectRepository;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public void saveAllSubject() throws IOException, URISyntaxException {
        subjectList = Files.readAllLines(Paths.get(getClass().getResource("/static/subject/subjectList.txt").toURI()));

        for (String str : subjectList) {
            Subject subject = Subject.builder().subject(str).build();
            subjectRepository.save(subject);
        }
    }

    /* 모든 과목 리스트 조회 로직 */
    @Transactional
    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }

    /* 과목 Id 값을 통해 특정 과목 조회 로직 */
    @Transactional
    public Subject getSubject(Long id) {
        return subjectRepository.findById(id).orElseThrow();
    }

    /* 검색어와 비슷한 과목명 조회 로직 */
    @Transactional
    public List<Subject> getSimilarSubject(String query) {
        return subjectRepository.findByValueContaining(query);
    }
}
