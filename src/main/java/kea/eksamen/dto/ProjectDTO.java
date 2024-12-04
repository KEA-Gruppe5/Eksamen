package kea.eksamen.dto;
import kea.eksamen.util.EndDateAfterStartDate;
import java.time.temporal.ChronoUnit;

public class ProjectDTO {
    private int id;
    private String title;
    @EndDateAfterStartDate
    private DateRange dateRange;
    private int duration;
    private double hoursForAllTasks;
    private double hoursToWorkPerDay;

    public ProjectDTO(int id, String title, DateRange dateRange, int duration) {
        this.id = id;
        this.title = title;
        this.dateRange = dateRange;
        this.duration = duration;
    }

    public ProjectDTO() {
        this.dateRange = new DateRange();
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

    public DateRange getDateRange() {
        return dateRange;
    }

    public void setDateRange(DateRange dateRange) {
        this.dateRange = dateRange;
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
        if (dateRange.getStartDate() != null && dateRange.getEndDate() != null) {
            if (dateRange.getStartDate().isAfter(dateRange.getEndDate())) {
                return 0;
            }
            return (int) ChronoUnit.DAYS.between(dateRange.getStartDate(), dateRange.getEndDate());
        }
        return 0;
    }
}
