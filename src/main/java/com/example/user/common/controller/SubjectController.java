package com.example.user.common.controller;

import com.example.user.common.entity.Subject;
import com.example.user.common.service.SubjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Tag(name = "Subject", description = "과목 API")
public class SubjectController {

    private final SubjectService subjectService;

    /* 모든 과목 리스트 조회 */
    @Operation(summary = "모든 과목 리스트 조회", description = "2024년 1학기 2학기 개설과목 리스트 조회")
    @GetMapping("/subjects")
    public List<Subject> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    /* 과목 Id 값으로 특정 과목 조회 */
    @Operation(summary = "특정 과목 조회", description = "과목 Id값을 parameter로 받아 특정 과목 명 조회")
    @GetMapping("/subjects/{id}")
    public Subject getSubject(@PathVariable("id") Long id) {
        return subjectService.getSubject(id);
    }

    /* DB에서 검색 시 입력된 검색어과 비슷한 과목명 리스트 반환 */
    @Operation(summary = "유사 과목명 조회", description = "사용자로부터 입력된 검색어를 포함하는 모든 과목명 List 조회")
    @GetMapping("/subjects/query/{query}")
    public List<Subject> getSimilarString(@PathVariable("query") String query) {
        return subjectService.getSimilarSubject(query);
    }
}
