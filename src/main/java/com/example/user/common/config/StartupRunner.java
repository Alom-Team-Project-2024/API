package com.example.user.common.config;

import com.example.user.common.service.SubjectService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class StartupRunner implements CommandLineRunner {

    private final SubjectService subjectService;

    @Override
    public void run(String... args) throws Exception {
        subjectService.saveAllSubject();
    }
}
