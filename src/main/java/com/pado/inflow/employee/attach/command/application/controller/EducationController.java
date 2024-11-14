package com.pado.inflow.employee.attach.command.application.controller;

import com.pado.inflow.employee.attach.command.application.service.EducationService;
import com.pado.inflow.employee.attach.command.domain.aggregate.dto.EducationDTO;
import com.pado.inflow.employee.attach.command.domain.aggregate.entity.Education;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("ECommandController")
@RequestMapping("/api/employee/education")
public class EducationController {

    private final EducationService educationService;

    @Autowired
    public EducationController(EducationService educationService) {
        this.educationService = educationService;
    }

    // 사원의 학력정보 등록
    @PostMapping("/add")
    public ResponseEntity addEdu(@RequestBody List<EducationDTO> educations) {
        List<Education> result = educationService.addEdus(educations);
        return result.size()>0 ? ResponseEntity.ok(result) :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("학력정보 저장 실패");
    }

    // 사원의 학력정보 수정
    @PostMapping("/modify")
    public ResponseEntity modifyEdu(@RequestBody List<EducationDTO> educations) {
        List<Education> result = educationService.modifyEdus(educations);
        return result.size()>0 ? ResponseEntity.ok(result) :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("학력정보 수정 실패");
    }

    // 사원의 학력정보 삭제
    @DeleteMapping("/delete")
    public ResponseEntity deleteEdu(@RequestBody List<Long> educations) {
        Boolean result = educationService.deleteEdus(educations);
        return result ? ResponseEntity.ok("삭제 완료") :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("학력정보 삭제 실패");
    }
}