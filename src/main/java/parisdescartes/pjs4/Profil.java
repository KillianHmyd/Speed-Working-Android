package parisdescartes.pjs4;


import java.util.Date;

/**
 * Created by Killian on 08/02/2016.
 */
public class Profil {

    private int idUser;
    private String firstname;
    private String lastname;
    private String email;
    private Date birthday;
    private String gender;
    private String picture;

    public Profil(int idUser, String firstname, String lastname, String email, Date birthday, String gender, String picture) {
        this.idUser = idUser;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.birthday = birthday;
        this.gender = gender;
        this.picture = picture;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBrithday() {
        return birthday;
    }

    public void setBrithday(Date brithday) {
        this.birthday = brithday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String sexe) {
        this.gender = sexe;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
