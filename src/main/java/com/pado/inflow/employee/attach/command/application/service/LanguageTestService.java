package com.pado.inflow.employee.attach.command.application.service;

import com.pado.inflow.employee.attach.command.domain.aggregate.dto.LanguageTestDTO;

import java.util.List;

public interface LanguageTestService {

    // 사원의 어학 정보 등록
    List<LanguageTestDTO> addLangTests(List<LanguageTestDTO> langTests);

    // 사원의 어학 정보 수정
    List<LanguageTestDTO> modifyLangTests(List<LanguageTestDTO> langTests);

    // 사원의 어학 정보 삭제
    Boolean deleteLangTests(List<Long> langTests);
}
