package com.pado.inflow.payroll.query.service;

import com.pado.inflow.payroll.query.dto.AllPaymentsDTO;
import com.pado.inflow.payroll.query.dto.PageDTO;
import com.pado.inflow.payroll.query.dto.PayrollDTO;

public interface PayrollService {

    // 사원별 연월별 급여 명세서 조회
    PayrollDTO findPaymentDetail(Long employeeId, Integer year, Integer month);

    // 사원별 전체 급여 내역 조회
    PageDTO<AllPaymentsDTO> findPaymentsByEmployeeId(Long employeeId, Integer pageNo);
}
