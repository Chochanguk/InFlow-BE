package com.pado.inflow.attendance.command.application.service;

import com.pado.inflow.attendance.command.application.dto.*;
import com.pado.inflow.attendance.command.domain.aggregate.entity.AttendanceRequest;
import com.pado.inflow.attendance.command.domain.aggregate.entity.AttendanceRequestType;
import com.pado.inflow.attendance.command.domain.aggregate.entity.Commute;
import com.pado.inflow.attendance.command.domain.aggregate.type.CancelStatus;
import com.pado.inflow.attendance.command.domain.aggregate.type.OvertimeStatus;
import com.pado.inflow.attendance.command.domain.aggregate.type.RemoteStatus;
import com.pado.inflow.attendance.command.domain.aggregate.type.RequestStatus;
import com.pado.inflow.attendance.command.domain.repository.*;
import com.pado.inflow.common.exception.CommonException;
import com.pado.inflow.common.exception.ErrorCode;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service("appAttendanceService")
public class AttendanceRequestServiceImpl implements AttendanceRequestService {

    private final ModelMapper modelMapper;
    private final AttendanceRequestRepository attendanceRequestRepository;
    private final AttendanceRequestTypeRepository attendanceRequestTypeRepository;
    private final AttendanceRequestFileRepository attendanceRequestFileRepository;
    private final BusinessTripRepository businessTripRepository;
    private final CommuteRepository commuteRepository;
    private final LeaveReturnRepository leaveReturnRepository;

    @Autowired
    public AttendanceRequestServiceImpl(ModelMapper modelMapper,
                                        AttendanceRequestRepository attendanceRequestRepository,
                                        AttendanceRequestTypeRepository attendanceRequestTypeRepository,
                                        AttendanceRequestFileRepository attendanceRequestFileRepository,
                                        BusinessTripRepository businessTripRepository,
                                        CommuteRepository commuteRepository,
                                        LeaveReturnRepository leaveReturnRepository) {
        this.modelMapper = modelMapper;
        this.attendanceRequestRepository = attendanceRequestRepository;
        this.attendanceRequestTypeRepository = attendanceRequestTypeRepository;
        this.attendanceRequestFileRepository = attendanceRequestFileRepository;
        this.businessTripRepository = businessTripRepository;
        this.commuteRepository = commuteRepository;
        this.leaveReturnRepository = leaveReturnRepository;
    }

    // 재택근무 신청
    @Override
    public ResponseCommuteRequestDTO registRemoteRequest(RequestCommuteRequestDTO reqCommuteRequestDTO) {
        // 사원 유효성 검사
//        employeeRepository.findById(reqCommuteRequestDTO.getEmployeeId())
//                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_EMPLOYEE));

        // 근태신청유형 유효성 검사
        AttendanceRequestType attendanceRequestType = attendanceRequestTypeRepository.findById(reqCommuteRequestDTO.getAttendanceRequestTypeId())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_ATTENDANCE_REQUEST_TYPE));

        if (!attendanceRequestType.getAttendanceRequestTypeName().equals("재택근무")) {
            throw new CommonException(ErrorCode.INVALID_REQUEST_BODY);
        }

        // 신청사유 유효성 검사
        if (reqCommuteRequestDTO.getRequestReason() == null || reqCommuteRequestDTO.getRequestReason().isEmpty()) {
            throw new CommonException(ErrorCode.INVALID_REQUEST_BODY);
        }

        // 날짜 String -> LocalDateTime 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date;
        LocalDateTime startTime;
        LocalDateTime startDate;
        LocalDateTime endTime;
        LocalDateTime endDate;
        try {
            date = LocalDate.parse(reqCommuteRequestDTO.getStartDate(), formatter);

            startTime = date.atTime(9, 0); // 오전 9시 0분 0초
            startDate = date.atStartOfDay();

            endTime = date.atTime(18, 0); // 오후 6시 0분 0초
            endDate = date.atStartOfDay();

        } catch (DateTimeParseException e) {
            throw new CommonException(ErrorCode.INVALID_REQUEST_BODY);
        }

        // 재택근무 시작일이 신청일보다 이전일 경우
        if (startDate.isBefore(LocalDateTime.now().withNano(0))) {
            throw new CommonException(ErrorCode.INVALID_REQUEST_BODY);
        }

        ResponseCommuteRequestDTO responseCommuteRequestDTO = ResponseCommuteRequestDTO
                .builder()
                .requestReason(reqCommuteRequestDTO.getRequestReason())
                .startDate(startDate)
                .endDate(endDate)
                .createdAt(LocalDateTime.now().withNano(0))
                .rejectionReason(null)
                .requestStatus(RequestStatus.ACCEPT.name())
                .canceledAt(null)
                .cancelReason(null)
                .cancelStatus(CancelStatus.N.name())
                .employeeId(reqCommuteRequestDTO.getEmployeeId())
                .attendanceRequestTypeId(reqCommuteRequestDTO.getAttendanceRequestTypeId())
                .build();

        // 재택근무 신청 내역 등록
        AttendanceRequest attendanceRequest =
                attendanceRequestRepository.save(modelMapper.map(responseCommuteRequestDTO, AttendanceRequest.class));

        // 재택근무 내역 등록
        CommuteDTO commuteDTO = CommuteDTO
                .builder()
                .startTime(startTime)
                .endTime(endTime)
                .remoteStatus(RemoteStatus.Y)
                .overtimeStatus(OvertimeStatus.N)
                .employeeId(reqCommuteRequestDTO.getEmployeeId())
                .attendanceRequestId(attendanceRequest.getAttendanceRequestId())
                .build();

        commuteRepository.save(modelMapper.map(commuteDTO, Commute.class));

        return modelMapper.map(attendanceRequest, ResponseCommuteRequestDTO.class);
    }

    // 초과근무 신청
    @Override
    public ResponseCommuteRequestDTO registOvertimeRequest(RequestCommuteRequestDTO reqCommuteRequestDTO) {
        return null;
    }

    // 출장 신청
    @Override
    public ResponseBusinessTripRequestDTO registBusinessTripRequest(RequestBusinessTripRequestDTO reqBusinessTripRequestDTO) {
        return null;
    }

    // 파견 신청
    @Override
    public ResponseBusinessTripRequestDTO registDispatchRequest(RequestBusinessTripRequestDTO reqBusinessTripRequestDTO) {
        return null;
    }

    // 휴직 신청
    @Override
    public ResponseLeaveReturnRequestDTO registLeaveReturnRequest(RequestLeaveReturnRequestDTO reqLeaveReturnRequestDTO) {
        return null;
    }

    // 복직 처리
    @Override
    public ResponseLeaveReturnRequestDTO reinstate(Long attendanceRequestId) {
        return null;
    }

    // 초과근무 연장
    @Override
    public ResponseCommuteRequestDTO extendOvertime(Long attendanceRequestId) {
        return null;
    }

}