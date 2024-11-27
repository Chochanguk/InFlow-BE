package com.pado.inflow.attendance.query.service;

import com.pado.inflow.attendance.query.dto.AttendanceRequestDTO;
import com.pado.inflow.attendance.query.dto.PageDTO;
import com.pado.inflow.attendance.query.repository.AttendanceRequestMapper;
import com.pado.inflow.common.exception.CommonException;
import com.pado.inflow.common.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class AttendanceRequestServiceImpl implements AttendanceRequestService {

    private final Integer PAGE_SIZE = 10; // 페이지 간격
    private final Integer ELEMENTS_PER_PAGE = 10; // 한 페이지 당 요소 개수

    private final AttendanceRequestMapper attendanceRequestMapper;

    @Autowired
    public AttendanceRequestServiceImpl(AttendanceRequestMapper attendanceRequestMapper) {
        this.attendanceRequestMapper = attendanceRequestMapper;
    }

    // 사원별 초과근무 신청 내역 미리보기 조회
    @Override
    public List<AttendanceRequestDTO> findOvertimeRequestPreviewsByEmployeeId(Long employeeId) {
        List<AttendanceRequestDTO> attendanceRequests =
                attendanceRequestMapper.findOvertimeRequestPreviewsByEmployeeId(employeeId);

        if (attendanceRequests == null || attendanceRequests.isEmpty()) {
            throw new CommonException(ErrorCode.NOT_FOUND_ATTENDANCE_REQUEST);
        }

        return attendanceRequests;
    }

    // 사원별 초과근무 신청 내역 전체 조회
    @Override
    public PageDTO<AttendanceRequestDTO> findOvertimeRequestsByEmployeeId(Long employeeId, Integer pageNo, String date) {
        // 페이지 번호 유효성 검사
        if(pageNo == null || pageNo < 1) {
            throw new CommonException(ErrorCode.INVALID_PARAMETER_FORMAT);
        }

        // 날짜 형식 유효성 검사 및 변환 (yyyy-MM)
        YearMonth parsedDate;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
            parsedDate = YearMonth.parse(date, formatter);
        } catch (DateTimeParseException e) {
            throw new CommonException(ErrorCode.INVALID_PARAMETER_FORMAT);
        }

        LocalDate startOfMonth = parsedDate.atDay(1); // 해당 월의 첫 번째 날

        Integer totalElements = attendanceRequestMapper.getTotalOvertimeRequestsByEmployeeId(employeeId, startOfMonth);
        if(totalElements == null || totalElements < 1) {
            throw new CommonException(ErrorCode.NOT_FOUND_COMMUTE);
        }

        Integer offset = (pageNo - 1) * ELEMENTS_PER_PAGE;
        List<AttendanceRequestDTO> attendanceRequests =
                attendanceRequestMapper.findOvertimeRequestsByEmployeeId(employeeId, ELEMENTS_PER_PAGE, offset, startOfMonth);
        if (attendanceRequests == null || attendanceRequests.isEmpty()) {
            throw new CommonException(ErrorCode.NOT_FOUND_ATTENDANCE_REQUEST);
        }

        return new PageDTO<>(attendanceRequests, pageNo, PAGE_SIZE, ELEMENTS_PER_PAGE, totalElements);
    }

    // 사원별 재택근무 신청 내역 미리보기 조회
    @Override
    public List<AttendanceRequestDTO> findRemoteRequestPreviewsByEmployeeId(Long employeeId) {
        List<AttendanceRequestDTO> attendanceRequests =
                attendanceRequestMapper.findRemoteRequestPreviewsByEmployeeId(employeeId);

        if (attendanceRequests == null || attendanceRequests.isEmpty()) {
            throw new CommonException(ErrorCode.NOT_FOUND_ATTENDANCE_REQUEST);
        }

        return attendanceRequests;
    }

    // 사원별 재택근무 신청 내역 전체 조회
    @Override
    public PageDTO<AttendanceRequestDTO> findRemoteRequestsByEmployeeId(Long employeeId, Integer pageNo, String date) {
        // 페이지 번호 유효성 검사
        if(pageNo == null || pageNo < 1) {
            throw new CommonException(ErrorCode.INVALID_PARAMETER_FORMAT);
        }

        // 날짜 형식 유효성 검사 및 변환 (yyyy-MM)
        YearMonth parsedDate;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
            parsedDate = YearMonth.parse(date, formatter);
        } catch (DateTimeParseException e) {
            throw new CommonException(ErrorCode.INVALID_PARAMETER_FORMAT);
        }

        LocalDate startOfMonth = parsedDate.atDay(1); // 해당 월의 첫 번째 날

        Integer totalElements = attendanceRequestMapper.getTotalRemoteRequestsByEmployeeId(employeeId, startOfMonth);
        if(totalElements == null || totalElements < 1) {
            throw new CommonException(ErrorCode.NOT_FOUND_COMMUTE);
        }

        Integer offset = (pageNo - 1) * ELEMENTS_PER_PAGE;
        List<AttendanceRequestDTO> attendanceRequests =
                attendanceRequestMapper.findRemoteRequestsByEmployeeId(employeeId, ELEMENTS_PER_PAGE, offset, startOfMonth);
        if (attendanceRequests == null || attendanceRequests.isEmpty()) {
            throw new CommonException(ErrorCode.NOT_FOUND_ATTENDANCE_REQUEST);
        }

        return new PageDTO<>(attendanceRequests, pageNo, PAGE_SIZE, ELEMENTS_PER_PAGE, totalElements);
    }

    // 사원별 출장 신청 내역 미리보기 조회
    @Override
    public List<AttendanceRequestDTO> findBusinessTripRequestPreviewsByEmployeeId(Long employeeId) {
        List<AttendanceRequestDTO> attendanceRequests =
                attendanceRequestMapper.findBusinessTripRequestPreviewsByEmployeeId(employeeId);

        if (attendanceRequests == null || attendanceRequests.isEmpty()) {
            throw new CommonException(ErrorCode.NOT_FOUND_ATTENDANCE_REQUEST);
        }

        return attendanceRequests;
    }

    // 사원별 출장 신청 내역 전체 조회
    @Override
    public PageDTO<AttendanceRequestDTO> findBusinessTripRequestsByEmployeeId(Long employeeId, Integer pageNo, String date) {
        // 페이지 번호 유효성 검사
        if(pageNo == null || pageNo < 1) {
            throw new CommonException(ErrorCode.INVALID_PARAMETER_FORMAT);
        }

        // 날짜 형식 유효성 검사 및 변환 (yyyy-MM)
        YearMonth parsedDate;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
            parsedDate = YearMonth.parse(date, formatter);
        } catch (DateTimeParseException e) {
            throw new CommonException(ErrorCode.INVALID_PARAMETER_FORMAT);
        }

        LocalDate startOfMonth = parsedDate.atDay(1); // 해당 월의 첫 번째 날

        Integer totalElements = attendanceRequestMapper.getTotalBusinessTripRequestsByEmployeeId(employeeId, startOfMonth);
        if(totalElements == null || totalElements < 1) {
            throw new CommonException(ErrorCode.NOT_FOUND_COMMUTE);
        }

        Integer offset = (pageNo - 1) * ELEMENTS_PER_PAGE;
        List<AttendanceRequestDTO> attendanceRequests =
                attendanceRequestMapper.findBusinessTripRequestsByEmployeeId(employeeId, ELEMENTS_PER_PAGE, offset, startOfMonth);
        if (attendanceRequests == null || attendanceRequests.isEmpty()) {
            throw new CommonException(ErrorCode.NOT_FOUND_ATTENDANCE_REQUEST);
        }

        return new PageDTO<>(attendanceRequests, pageNo, PAGE_SIZE, ELEMENTS_PER_PAGE, totalElements);
    }

    // 사원별 파견 신청 내역 미리보기 조회
    @Override
    public List<AttendanceRequestDTO> findDispatchRequestPreviewsByEmployeeId(Long employeeId) {
        List<AttendanceRequestDTO> attendanceRequests =
                attendanceRequestMapper.findDispatchRequestPreviewsByEmployeeId(employeeId);

        if (attendanceRequests == null || attendanceRequests.isEmpty()) {
            throw new CommonException(ErrorCode.NOT_FOUND_ATTENDANCE_REQUEST);
        }

        return attendanceRequests;
    }

    // 사원별 파견 신청 내역 전체 조회
    @Override
    public PageDTO<AttendanceRequestDTO> findDispatchRequestsByEmployeeId(Long employeeId, Integer pageNo, String date) {
        // 페이지 번호 유효성 검사
        if(pageNo == null || pageNo < 1) {
            throw new CommonException(ErrorCode.INVALID_PARAMETER_FORMAT);
        }

        // 날짜 형식 유효성 검사 및 변환 (yyyy-MM)
        YearMonth parsedDate;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
            parsedDate = YearMonth.parse(date, formatter);
        } catch (DateTimeParseException e) {
            throw new CommonException(ErrorCode.INVALID_PARAMETER_FORMAT);
        }

        LocalDate startOfMonth = parsedDate.atDay(1); // 해당 월의 첫 번째 날

        Integer totalElements = attendanceRequestMapper.getTotalDispatchRequestsByEmployeeId(employeeId, startOfMonth);
        if(totalElements == null || totalElements < 1) {
            throw new CommonException(ErrorCode.NOT_FOUND_COMMUTE);
        }

        Integer offset = (pageNo - 1) * ELEMENTS_PER_PAGE;
        List<AttendanceRequestDTO> attendanceRequests =
                attendanceRequestMapper.findDispatchRequestsByEmployeeId(employeeId, ELEMENTS_PER_PAGE, offset, startOfMonth);
        if (attendanceRequests == null || attendanceRequests.isEmpty()) {
            throw new CommonException(ErrorCode.NOT_FOUND_ATTENDANCE_REQUEST);
        }

        return new PageDTO<>(attendanceRequests, pageNo, PAGE_SIZE, ELEMENTS_PER_PAGE, totalElements);
    }

    // 사원별 휴직 신청 내역 미리보기 조회
    @Override
    public List<AttendanceRequestDTO> findLeaveRequestPreviewsByEmployeeId(Long employeeId) {
        List<AttendanceRequestDTO> attendanceRequests =
                attendanceRequestMapper.findLeaveRequestPreviewsByEmployeeId(employeeId);

        if (attendanceRequests == null || attendanceRequests.isEmpty()) {
            throw new CommonException(ErrorCode.NOT_FOUND_ATTENDANCE_REQUEST);
        }

        return attendanceRequests;
    }

    // 사원별 휴직 신청 내역 전체 조회
    @Override
    public PageDTO<AttendanceRequestDTO> findLeaveRequestsByEmployeeId(Long employeeId, Integer pageNo, String date) {
        // 페이지 번호 유효성 검사
        if(pageNo == null || pageNo < 1) {
            throw new CommonException(ErrorCode.INVALID_PARAMETER_FORMAT);
        }

        // 날짜 형식 유효성 검사 및 변환 (yyyy-MM)
        YearMonth parsedDate;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
            parsedDate = YearMonth.parse(date, formatter);
        } catch (DateTimeParseException e) {
            throw new CommonException(ErrorCode.INVALID_PARAMETER_FORMAT);
        }

        LocalDate startOfMonth = parsedDate.atDay(1); // 해당 월의 첫 번째 날

        Integer totalElements = attendanceRequestMapper.getTotalLeaveRequestsByEmployeeId(employeeId, startOfMonth);
        if(totalElements == null || totalElements < 1) {
            throw new CommonException(ErrorCode.NOT_FOUND_COMMUTE);
        }

        Integer offset = (pageNo - 1) * ELEMENTS_PER_PAGE;
        List<AttendanceRequestDTO> attendanceRequests =
                attendanceRequestMapper.findLeaveRequestsByEmployeeId(employeeId, ELEMENTS_PER_PAGE, offset, startOfMonth);
        if (attendanceRequests == null || attendanceRequests.isEmpty()) {
            throw new CommonException(ErrorCode.NOT_FOUND_ATTENDANCE_REQUEST);
        }

        return new PageDTO<>(attendanceRequests, pageNo, PAGE_SIZE, ELEMENTS_PER_PAGE, totalElements);
    }

    // 사원별 복직 신청 내역 미리보기 조회
    @Override
    public List<AttendanceRequestDTO> findReturnRequestPreviewsByEmployeeId(Long employeeId) {
        List<AttendanceRequestDTO> attendanceRequests =
                attendanceRequestMapper.findReturnRequestPreviewsByEmployeeId(employeeId);

        if (attendanceRequests == null || attendanceRequests.isEmpty()) {
            throw new CommonException(ErrorCode.NOT_FOUND_ATTENDANCE_REQUEST);
        }

        return attendanceRequests;
    }

    // 사원별 복직 신청 내역 전체 조회
    @Override
    public PageDTO<AttendanceRequestDTO> findReturnRequestsByEmployeeId(Long employeeId, Integer pageNo, String date) {
        // 페이지 번호 유효성 검사
        if(pageNo == null || pageNo < 1) {
            throw new CommonException(ErrorCode.INVALID_PARAMETER_FORMAT);
        }

        // 날짜 형식 유효성 검사 및 변환 (yyyy-MM)
        YearMonth parsedDate;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
            parsedDate = YearMonth.parse(date, formatter);
        } catch (DateTimeParseException e) {
            throw new CommonException(ErrorCode.INVALID_PARAMETER_FORMAT);
        }

        LocalDate startOfMonth = parsedDate.atDay(1); // 해당 월의 첫 번째 날

        Integer totalElements = attendanceRequestMapper.getTotalReturnRequestsByEmployeeId(employeeId, startOfMonth);
        if(totalElements == null || totalElements < 1) {
            throw new CommonException(ErrorCode.NOT_FOUND_COMMUTE);
        }

        Integer offset = (pageNo - 1) * ELEMENTS_PER_PAGE;
        List<AttendanceRequestDTO> attendanceRequests =
                attendanceRequestMapper.findReturnRequestsByEmployeeId(employeeId, ELEMENTS_PER_PAGE, offset, startOfMonth);
        if (attendanceRequests == null || attendanceRequests.isEmpty()) {
            throw new CommonException(ErrorCode.NOT_FOUND_ATTENDANCE_REQUEST);
        }

        return new PageDTO<>(attendanceRequests, pageNo, PAGE_SIZE, ELEMENTS_PER_PAGE, totalElements);
    }

}
