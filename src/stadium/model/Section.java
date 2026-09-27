package stadium.model;

import common.entity.BaseEntity;

public class Section extends BaseEntity {
    private Long stadiumId;
    private String name;
    public Section(Long id, Long stadiumId, String name) {
        super(id);
        this.stadiumId = stadiumId;
        this.name = name;
    }
    public Long getStadiumId() {
        return this.stadiumId;
    }
    public void setStadiumId(Long stadiumId) {
        this.stadiumId = stadiumId;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

}
