package util;

public class HospitalsTM {
    private String id;
    private String name;
    private String city;
    private String district;
    private String capacity;
    private String director;
    private String directorContact;
    private String tel1;
    private String tel2;
    private String fax;
    private String email;

    public HospitalsTM() {
    }

    public HospitalsTM(String id, String name, String city, String district, String capacity, String director, String directorContact, String tel1, String tel2, String fax, String email) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.district = district;
        this.capacity = capacity;
        this.director = director;
        this.directorContact = directorContact;
        this.tel1 = tel1;
        this.tel2 = tel2;
        this.fax = fax;
        this.email = email;
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

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getDirectorContact() {
        return directorContact;
    }

    public void setDirectorContact(String directorContact) {
        this.directorContact = directorContact;
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

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
