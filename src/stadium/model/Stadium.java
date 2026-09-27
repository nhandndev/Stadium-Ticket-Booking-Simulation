package stadium.model;

import common.entity.BaseEntity;

public class Stadium extends BaseEntity {
    private String name;
    private String address;
   public Stadium( Long id, String name, String address) {
       super(id);
       this.name = name;
       this.address = address;
   }
   public String getName() {
       return this.name;
   }
   public void setName(String name) {
       this.name = name;
    }
    public String getAddress() {
       return this.address;
    }
    public void setAddress(String address) {
       this.address = address;
    }


}
