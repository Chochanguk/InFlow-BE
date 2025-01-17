package com.pado.inflow.evaluation.command.domain.aggregate.dto.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TaskItemDTO {

    private Long taskItemId;
    private String taskName;
    private String taskContent;
    private Long assignedEmployeeCount;
    private Long taskTypeId;
    private Long employeeId;
    private String departmentCode;
    private Long evaluationPolicyId;
    private Boolean isManagerWritten;       // 부서장 작성 여부
}