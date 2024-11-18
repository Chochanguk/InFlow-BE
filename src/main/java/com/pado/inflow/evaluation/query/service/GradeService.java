package com.pado.inflow.evaluation.query.service;

import com.pado.inflow.evaluation.query.dto.GradeDTO;

import java.util.List;

public interface GradeService {
    List<GradeDTO> findGradeByYearAndHalf(Integer year, String half);
}