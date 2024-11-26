package com.pado.inflow.employee.attach.query.service;

import com.pado.inflow.common.exception.CommonException;
import com.pado.inflow.common.exception.ErrorCode;
import com.pado.inflow.employee.attach.query.dto.FamilyMemberDTO;
import com.pado.inflow.employee.attach.query.repository.FamilyMemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("FMQueryService")
public class FamilyMemberService {

    static FamilyMemberMapper familyMemberMapper;

    @Autowired
    public FamilyMemberService(FamilyMemberMapper familyMemberMapper) {
        this.familyMemberMapper = familyMemberMapper;
    }

    // 사원의 가구원 조회
    public List<FamilyMemberDTO> getFamilyMemberAll(String employeeId) {
        return Optional.of(familyMemberMapper.getAllFamilyMemberList(employeeId))
                .filter(fam -> !fam.isEmpty())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_FAMILY_MEMBER));
    }
}
