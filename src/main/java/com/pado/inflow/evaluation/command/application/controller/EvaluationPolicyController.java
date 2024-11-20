package com.pado.inflow.evaluation.command.application.controller;

import com.pado.inflow.common.ResponseDTO;
import com.pado.inflow.evaluation.command.application.service.EvaluationPolicyService;
import com.pado.inflow.evaluation.command.domain.aggregate.dto.request.CreateEvaluationPolicyRequestDTO;
import com.pado.inflow.evaluation.command.domain.aggregate.dto.response.CreateEvaluationPolicyResponseDTO;
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

    @PostMapping("/policyCreation")
    public ResponseDTO<CreateEvaluationPolicyResponseDTO> createEvaluationPolicy(
            @RequestBody CreateEvaluationPolicyRequestDTO createEvaluationPolicyRequestDTO
            ) {
        CreateEvaluationPolicyResponseDTO createdEvaluationPolicy =
                evaluationPolicyService.createEvaluationPolicy(createEvaluationPolicyRequestDTO);
        return ResponseDTO.ok(createdEvaluationPolicy);
    }


}
