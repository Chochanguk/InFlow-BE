package com.pado.inflow.attendance.command.application.service;

import com.pado.inflow.attendance.command.application.dto.RequestBusinessTripRequestDTO;
import com.pado.inflow.attendance.command.application.dto.RequestCommuteRequestDTO;
import com.pado.inflow.attendance.command.application.dto.ResponseBusinessTripRequestDTO;
import com.pado.inflow.attendance.command.application.dto.ResponseCommuteRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@SpringBootTest
@Transactional
class AttendanceRequestServiceTests {

    @Autowired
    private AttendanceRequestService attendanceRequestService;

    @DisplayName("재택근무 신청 테스트")
    @Test
    void testRegistRemoteRequest() {
        // Given
        RequestCommuteRequestDTO reqCommuteRequestDTO = RequestCommuteRequestDTO
                .builder()
                .requestReason("오늘은 재택근무 하는 날")
                .startDate(LocalDate.now().toString())
                .employeeId(1L)
                .attendanceRequestTypeId(1L)
                .build();

        // When
        ResponseCommuteRequestDTO resCommuteRequestDTO = attendanceRequestService.registRemoteRequest(reqCommuteRequestDTO);
        if (resCommuteRequestDTO != null) {
            log.info(resCommuteRequestDTO.toString());
        }

        // Then
        Assertions.assertNotNull(resCommuteRequestDTO);
    }

    @DisplayName("초과근무 신청 테스트")
    @Test
    void testRegistOvertimeRequest() {
        // Given
        LocalDateTime now = LocalDateTime.now().withNano(0);

        int minute = now.getMinute();
        int roundedMinute = (minute < 30) ? 30 : 0;

        LocalDateTime startTime;

        // 만약 0분이 되면 시간을 1시간 뒤로 설정
        if (roundedMinute == 0) {
            startTime = now.plusHours(1).withMinute(0).withSecond(0).withNano(0);
        } else {
            startTime = now.withMinute(roundedMinute).withSecond(0).withNano(0);
        }

        LocalDateTime endTime = startTime.plusMinutes(30);

        String formattedStartTime = startTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        String formattedEndTime = endTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));

        RequestCommuteRequestDTO reqCommuteRequestDTO = RequestCommuteRequestDTO
                .builder()
                .requestReason("야근해야합니다")
                .startDate(formattedStartTime)
                .endDate(formattedEndTime)
                .employeeId(1L)
                .attendanceRequestTypeId(2L)
                .build();

        // When
        ResponseCommuteRequestDTO resCommuteRequestDTO = attendanceRequestService.registOvertimeRequest(reqCommuteRequestDTO);
        if (resCommuteRequestDTO != null) {
            log.info(resCommuteRequestDTO.toString());
        }

        // Then
        Assertions.assertNotNull(resCommuteRequestDTO);
    }

    @DisplayName("출장 신청 테스트")
    @Test
    void testRegistBusinessTripRequest() {
        // Given
        RequestBusinessTripRequestDTO reqBusinessTripRequestDTO = RequestBusinessTripRequestDTO
                .builder()
                .requestReason("출장 가야합니다.")
                .startDate(LocalDate.now().toString())
                .endDate(LocalDate.now().plusDays(3).toString())
                .destination("실리콘밸리 본사")
                .employeeId(1L)
                .attendanceRequestTypeId(3L)
                .build();

        // When
        ResponseBusinessTripRequestDTO resBusinessTripRequestDTO =
                attendanceRequestService.registBusinessTripRequest(reqBusinessTripRequestDTO);
        if (resBusinessTripRequestDTO != null) {
            log.info(resBusinessTripRequestDTO.toString());
        }

        // Then
        Assertions.assertNotNull(resBusinessTripRequestDTO);
    }

    @DisplayName("파견 신청 테스트")
    @Test
    void testRegistDispatchRequest() {
        // Given
        RequestBusinessTripRequestDTO reqBusinessTripRequestDTO = RequestBusinessTripRequestDTO
                .builder()
                .requestReason("파견 가야합니다.")
                .startDate(LocalDate.now().toString())
                .endDate(LocalDate.now().plusDays(10).toString())
                .destination("LA 지사")
                .employeeId(1L)
                .attendanceRequestTypeId(4L)
                .build();

        // When
        ResponseBusinessTripRequestDTO resBusinessTripRequestDTO =
                attendanceRequestService.registDispatchRequest(reqBusinessTripRequestDTO);
        if (resBusinessTripRequestDTO != null) {
            log.info(resBusinessTripRequestDTO.toString());
        }

        // Then
        Assertions.assertNotNull(resBusinessTripRequestDTO);
    }

}