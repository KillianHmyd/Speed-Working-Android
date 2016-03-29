
package parisdescartes.pjs4.classItems;

import java.util.Date;

/**
 * Created by Killian on 08/02/2016.
 */
public class Profil {

    private Integer idUser;
    private String firstname;
    private String lastname;
    private String email;
    private String birthday;
    private String gender;
    private String picture;

    private Integer code ;
    private String message;

    public Profil(Integer idUser, String firstname, String lastname, String email, String birthday,
                  String gender, String picture, Integer code, String msg) {
        this.idUser = idUser;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.birthday = birthday;
        this.gender = gender;
        this.picture = picture;
        this.code = code;
        this.message = msg;
    }

    public Integer getIdUser() {
        return idUser;
    }
    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getGender() {
        return gender;
    }

    public String getPicture() {
        return picture;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
