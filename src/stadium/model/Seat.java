package stadium.model;

import common.entity.BaseEntity;
import stadium.enums.SeatStatus;

public class Seat extends BaseEntity {
    private Long sectionId;
    private String rowLabel;
    private int seatNumber;
    private boolean active;
    public Seat(Long id, Long sectionId, String rowLabel, int seatNumber, boolean active) {
        super(id);
        this.sectionId = sectionId;
        this.rowLabel = rowLabel;
        this.seatNumber = seatNumber;
        this.active = active;
    }
    public Long getSectionId() {
        return this.sectionId;
    }
    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }
    public String getRowLabel() {
        return this.rowLabel;
    }
    public void setRowLabel(String rowLabel) {
        this.rowLabel = rowLabel;
    }
    public int getSeatNumber() {
        return this.seatNumber;
    }
    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }
    public boolean isActive() {
        return this.active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
}
