package com.pado.inflow.evaluation.query.service;

import com.pado.inflow.evaluation.query.dto.FeedbackDTO;

public interface FeedbackService {
    FeedbackDTO findFeedbackByempIdAndYearAndHalf(Long empId, Integer year, String half);

    FeedbackDTO findFeedbackByFeedbackId(Long feedbackId);

    boolean existsFeedbackByempIdAndYearAndHalf(Long empId, Integer year, String half);
}
