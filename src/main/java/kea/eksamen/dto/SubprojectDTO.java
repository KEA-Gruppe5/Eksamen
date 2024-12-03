package kea.eksamen.dto;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class SubprojectDTO {
    private int id;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private int duration;
    private double hoursForAllTasks;
    private double hoursToWorkPerDay;

    public SubprojectDTO(int id, String title, LocalDate startDate, LocalDate endDate, int duration) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getHoursToWorkPerDay() {
        return hoursToWorkPerDay;
    }

    public void setHoursToWorkPerDay(double hoursToWorkPerDay) {
        this.hoursToWorkPerDay = hoursToWorkPerDay;
    }

    public double getHoursForAllTasks() {
        return hoursForAllTasks;
    }

    public void setHoursForAllTasks(double hoursForAllTasks) {
        this.hoursForAllTasks = hoursForAllTasks;
    }

    public int getDuration() {
        if (startDate != null && endDate != null) {
            if (startDate.isAfter(endDate)) {
                return 0;
            }
            return (int) ChronoUnit.DAYS.between(startDate, endDate);
        }
        return 0;
    }
}
