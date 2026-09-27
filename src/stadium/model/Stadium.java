package stadium.model;

import common.entity.BaseEntity;

public class Stadium extends BaseEntity {
    private String name;
    private String address;

    public Stadium(Long id, String name, String address) {
        super(id);
        this.name = name;
        this.address = address;
    }

    public void updateDetails(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
