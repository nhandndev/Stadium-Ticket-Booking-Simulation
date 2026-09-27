package stadium.model;

import common.entity.BaseEntity;

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

    public void updateLocation(String rowLabel, int seatNumber) {
        this.rowLabel = rowLabel;
        this.seatNumber = seatNumber;
    }

    public void activate() {
        active = true;
    }

    public void deactivate() {
        active = false;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public String getRowLabel() {
        return rowLabel;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public boolean isActive() {
        return active;
    }
}
