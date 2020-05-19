package util;

public class QuarantineCentersTM {
    private String id;
    private String name;
    private String city;
    private String district;
    private String head;
    private String headContact;
    private String tel1;
    private String tel2;
    private String capacity;

    public QuarantineCentersTM() {
    }

    public QuarantineCentersTM(String id, String name, String city, String district, String head, String headContact, String tel1, String tel2, String capacity) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.district = district;
        this.head = head;
        this.headContact = headContact;
        this.tel1 = tel1;
        this.tel2 = tel2;
        this.capacity = capacity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getHeadContact() {
        return headContact;
    }

    public void setHeadContact(String headContact) {
        this.headContact = headContact;
    }

    public String getTel1() {
        return tel1;
    }

    public void setTel1(String tel1) {
        this.tel1 = tel1;
    }

    public String getTel2() {
        return tel2;
    }

    public void setTel2(String tel2) {
        this.tel2 = tel2;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
