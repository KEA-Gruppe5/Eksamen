package kea.eksamen.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.time.LocalDate;

public class Project {

    private int id;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private int duration;

    public Project(int id, String title, LocalDate startDate, LocalDate endDate, int duration) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
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
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
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
