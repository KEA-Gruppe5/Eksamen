package kea.eksamen.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Project {

    private int id;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private int duration;
    private boolean archived;

    public Project(int id, String title, LocalDate startDate, LocalDate endDate, int duration, boolean archived) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
        this.archived = archived;
    }

    public Project(String title, LocalDate startDate, LocalDate endDate, int duration) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
    }


    public Project() {
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

    public int getDuration() {
        if (startDate != null && endDate != null) {
            if (startDate.isAfter(endDate)) {
                return 0;
            }
            return (int) ChronoUnit.DAYS.between(startDate, endDate);
        }
        return 0;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", duration=" + duration +
                '}';
    }
}
