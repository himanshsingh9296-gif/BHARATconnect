package com.bharatconnect.service;

import com.bharatconnect.model.Report;
import com.bharatconnect.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public Report submitReport(Report report) {
        report.setStatus("PENDING");
        report.setCreatedAt(LocalDateTime.now());
        return reportRepository.save(report);
    }

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    public Report getReportById(String id) {
        Optional<Report> report = reportRepository.findById(id);
        return report.orElse(null);
    }

    public List<Report> getPendingReports() {
        return reportRepository.findByStatus("PENDING");
    }

    public void resolveReport(String id, String decision, String adminNotes) {
        Optional<Report> report = reportRepository.findById(id);
        if (report.isPresent()) {
            Report r = report.get();
            r.setStatus("RESOLVED");
            r.setDecision(decision);
            r.setAdminNotes(adminNotes);
            r.setResolvedAt(LocalDateTime.now());
            reportRepository.save(r);
        }
    }

    public List<Report> getReportsByUserId(String userId) {
        return reportRepository.findByReporterId(userId);
    }

    public void deleteReport(String id) {
        reportRepository.deleteById(id);
    }
}
