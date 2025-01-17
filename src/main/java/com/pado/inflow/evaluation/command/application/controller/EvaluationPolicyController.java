package com.pado.inflow.evaluation.command.application.controller;

import com.pado.inflow.common.ResponseDTO;
import com.pado.inflow.evaluation.command.application.service.EvaluationPolicyService;
import com.pado.inflow.evaluation.command.domain.aggregate.dto.request.CreateEvaluationPolicyRequestDTO;
import com.pado.inflow.evaluation.command.domain.aggregate.dto.request.UpdateEvaluationPolicyRequestDTO;
import com.pado.inflow.evaluation.command.domain.aggregate.dto.response.CreateEvaluationPolicyResponseDTO;
import com.pado.inflow.evaluation.command.domain.aggregate.dto.response.UpdateEvaluationPolicyResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("CommandEvaluationPolicyController")
@RequestMapping("/api/evaluations/evaluationPolicy")
public class EvaluationPolicyController {

    @Autowired
    private final EvaluationPolicyService evaluationPolicyService;

    public EvaluationPolicyController(EvaluationPolicyService evaluationPolicyService) {
        this.evaluationPolicyService = evaluationPolicyService;
    }

    // 평가 정책 생성
    @PostMapping("/policyCreation")
    public ResponseDTO<CreateEvaluationPolicyResponseDTO> createEvaluationPolicy(
            @RequestBody CreateEvaluationPolicyRequestDTO createEvaluationPolicyRequestDTO
            ) {
        CreateEvaluationPolicyResponseDTO createdEvaluationPolicy =
                evaluationPolicyService.createEvaluationPolicy(createEvaluationPolicyRequestDTO);
        return ResponseDTO.ok(createdEvaluationPolicy);
    }

    // 평가 정책 수정
    @PatchMapping("/{evaluationPolicyId}")
    public ResponseDTO<UpdateEvaluationPolicyResponseDTO> updateEvaluationPolicy(
            @PathVariable( value = "evaluationPolicyId") Long evaluationPolicyId
           ,@RequestBody UpdateEvaluationPolicyRequestDTO updateEvaluationPolicyRequestDTO
            ) {
        UpdateEvaluationPolicyResponseDTO updatedEvaluationPolicy =
                evaluationPolicyService.updateEvaluationPolicy(updateEvaluationPolicyRequestDTO, evaluationPolicyId);
        return ResponseDTO.ok(updatedEvaluationPolicy);
    }

    // 평가 정책 삭제
    @DeleteMapping("/{evaluationPolicyId}")
    public ResponseDTO<String> deleteEvaluationPolicy(
            @PathVariable( value = "evaluationPolicyId") Long evaluationPolicyId
    ) {
        evaluationPolicyService.deleteEvaluationPolicyByEvaluationPolicyId(evaluationPolicyId);
        return ResponseDTO.ok("평가 정책 삭제를 완료하였습니다.");
    }
}
