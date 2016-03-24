package parisdescartes.pjs4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import parisdescartes.pjs4.classItems.Group;
import parisdescartes.pjs4.classItems.Profil;
import parisdescartes.pjs4.classItems.Skill;
import parisdescartes.pjs4.classItems.User;


/**
 * Created by Vocxtir on 21/03/2016.
 */
public class ERelationDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ERelation.db";

    public static final String ERelation_CREATE_USER_TABLE =
            "create table USER (" +
                    "idUser INTEGER PRIMARY KEY," +
                    "idFacebook TEXT," +
                    "tokenFacebook TEXT," +
                    "lastActivity TEXT" +
            ")"
            ;

    public static final String ERelation_CREATE_PROFIL_TABLE =
            "create table PROFIL (" +
                    "idUser INTEGER PRIMARY KEY NOT NULL, " +
                    "firstName TEXT NOT NULL," +
                    "lastName TEXT NOT NULL," +
                    "email TEXT NOT NULL," +
                    "birthday TEXT NOT NULL," +
                    "gender TEXT NOT NULL," +
                    "picture TEXT," +
                    "matched INTEGER NOT NULL," +
                    "FOREIGN KEY(idUser) REFERENCES USER(idUser)" +
            ")"
            ;

    public static final String ERelation_CREATE_GROUPS_TABLE =
            "create table GROUPS (" +
                    "idGroup INTEGER PRIMARY KEY NOT NULL, " +
                    "nameGroup TEXT NOT NULL," +
                    "presentation TEXT NOT NULL," +
                    "idLeader INTEGER NOT NULL," +
                    "finish INTEGER DEFAULT 0," +
                    "FOREIGN KEY (idLeader) REFERENCES PROFIL(idUser)" +
            ")"
            ;

    public static final String ERelation_CREATE_SKILL_TABLE =
            "create table SKILL (" +
                    "idUser INTEGER PRIMARY KEY NOT NULL," +
                    "skillName TEXT NOT NULL," +
                    "FOREIGN KEY (idUser) REFERENCES USER(idUser)" +
            ")"
            ;

    public static final String ERelation_CREATE_OWNSKILL_TABLE =
            "create table OWNSKILL (" +
                    "idUser INTEGER NOT NULL, " +
                    "idSkill INTEGER NOT NULL," +
                    "FOREIGN KEY (idUser) REFERENCES USER(idUser),"+
                    "FOREIGN KEY (idSkill) REFERENCES SKILL(idSkill)"+
            ")"
            ;

    public static final String ERelation_CREATE_ACCESSGRP_TABLE =
            "create table ACCESSGRP (" +
                    "idUser INTEGER NOT NULL," +
                    "idGroup INTEGER NOT NULL," +
                    "PRIMARY KEY(idUser, idGroup),"+
                    "FOREIGN KEY(idUser) REFERENCES USER(idUser)," +
                    "FOREIGN KEY(idGroup) REFERENCES GROUPS(idGroup)" +
            ")"
            ;

    public static final String ERelation_CREATE_MESSAGES_TABLE =
            "create table MESSAGE (" +
                    "idMsg INTEGER PRIMARY KEY NOT NULL, " +
                    "msgContent TEXT NOT NULL," +
                    "idConv INTEGER NOT NULL," +
                    "idUser INTEGER NOT NULL" +
            ")"
            ;

    //CONSTRUCTOR
    public ERelationDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ERelation_CREATE_USER_TABLE);
        db.execSQL(ERelation_CREATE_PROFIL_TABLE);
        db.execSQL(ERelation_CREATE_GROUPS_TABLE);
        db.execSQL(ERelation_CREATE_MESSAGES_TABLE);
        db.execSQL(ERelation_CREATE_OWNSKILL_TABLE);
        db.execSQL(ERelation_CREATE_SKILL_TABLE);
        db.execSQL(ERelation_CREATE_ACCESSGRP_TABLE);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS USER");
        db.execSQL("DROP TABLE IF EXISTS PROFIL");
        db.execSQL("DROP TABLE IF EXISTS GROUPS");
        db.execSQL("DROP TABLE IF EXISTS SKILL");
        db.execSQL("DROP TABLE IF EXISTS OWNSKILL");
        db.execSQL("DROP TABLE IF EXISTS MESSAGES");
        db.execSQL("DROP TABLE IF EXISTS ACCESSGRP");
        onCreate(db);
    }


    /** USER **/
    public boolean insertUser(User user){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("idUser", user.getIdUser());
        contentValues.put("idFacebook", user.getIdFacebook());
        contentValues.put("tokenFacebook", user.getTokenFacebook());
        contentValues.put("lastActivity", user.getLastActivity().toString());

        long result = db.insert("USER", null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    //Supposant que l'utilisateur supprime son profil de notre application
    public Boolean deleteUser(Context context){
        return context.deleteDatabase(DATABASE_NAME);
    }

    /** PROFIL **/
    public boolean insertProfile(Profil profile, Boolean matched){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("idUser", profile.getIdUser());
        contentValues.put("firstname", profile.getFirstname());
        contentValues.put("lastname", profile.getLastname());
        contentValues.put("email", profile.getEmail());
        contentValues.put("gender", profile.getGender());
        contentValues.put("birthday", profile.getBirthday().toString());
        contentValues.put("email", profile.getEmail());
        contentValues.put("picture", profile.getPicture());
        int match;
        if(matched)
            match=1;
        else
            match=0;
        contentValues.put("matched", match);
        System.out.println("IDUSER INSERT : "+profile.getIdUser());
        long result = db.insert("PROFIL", null, contentValues);
        if(result == -1){
            System.out.println("Pas ok");
            return false;
        }else{
            System.out.println("OK");
            return true;
        }
    }

    public Integer deleteProfile(Profil profil){
        SQLiteDatabase db = getWritableDatabase() ;
        return db.delete("PROFIL", "idUser" + " = ?", new String[]{String.valueOf(profil.getIdUser())});
    }

    public Profil getProfile(long idUser){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from PROFIL WHERE idUser = ?", new String[]{idUser + ""}) ;
        System.out.println("IDUSER GET : "+idUser);
        if(result.getCount() == 0){
            return null;
        }

        result.moveToFirst();
        Profil profil = new Profil(
                result.getInt(0),
                result.getString(1),
                result.getString(2),
                result.getString(3),
                null, //TODO convert String to Date
                result.getString(5),
                result.getString(6),
                null,
                null
        );
        return profil;
    }

    public Cursor getAllProfile(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from PROFIL", null);
        return result;
    }

    public Cursor getMatchedProfile(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from PROFIL WHERE matched = 1", null);
        return result;
    }

    public Cursor getUnmatchedProfile(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from PROFIL WHERE matched = 0", null);
        return result;
    }

    /** SKILL**/
    public boolean insertSkill(User user, String skillName){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("idUser", user.getIdUser());
        contentValues.put("nameSkill", skillName);

        long result = db.insert("SKILL", null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean updateSkill(Skill skill){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("idSkill", skill.getIdSkill());
        contentValues.put("nameSkill", skill.getSkillName());

        long result = db.update("SKILL", contentValues, "idSkill = ?", new String[]{skill.getIdSkill() + ""});
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Integer deleteSkill(Skill skill){
        SQLiteDatabase db = getWritableDatabase() ;
        return db.delete("SKILL", "idSkill" + " = ?", new String[]{String.valueOf(skill.getIdSkill())});
    }

    public Cursor getAllSkill(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from SKILL", null);
        return result;
    }

    /** OWNSKILL **/
    public boolean insertOwnSkill(User user, Skill skill){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("idUser", user.getIdUser());
        contentValues.put("idSkill", skill.getIdSkill());

        long result = db.insert("SKILL", null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Integer deleteOwnSkill(long id){
        SQLiteDatabase db = getWritableDatabase() ;
        return db.delete("OWNSKILL", "idSkill" + " = ?", new String[]{String.valueOf(id)});
    }

    public Cursor getAllOwnSkill(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from OWNSKILL", null);
        return result;
    }

    /** GROUPS **/
    public boolean insertGroup(Group group){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("idGroup", group.getIdGroup());
        contentValues.put("nameGroup", group.getGrpName());
        contentValues.put("presentation", group.getDescription());
        contentValues.put("idLeader", group.getIdUserLead());
        contentValues.put("finish", group.isEndOfProject());
        db.insert("GROUPS", null, contentValues);

        long result = db.insert("GROUPS", null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean updateGroup(Group group){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("idGroup", group.getIdGroup());
        contentValues.put("nameGroup", group.getGrpName());
        contentValues.put("presentation", group.getDescription());
        contentValues.put("idLeader", group.getIdUserLead());
        contentValues.put("finish", group.isEndOfProject());

        long result = db.update("GROUPS", contentValues, "idGroup = ?", new String[]{group.getIdGroup() + ""});
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Integer deleteGroup(long id){
        SQLiteDatabase db = getWritableDatabase() ;
        return db.delete("GROUPS", "idGroup" + " = ?", new String[]{String.valueOf(id)});
    }

    public Group getGroup(int idGroup){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from GROUPS WHERE idGroup = ?", new String[] {idGroup + ""});

        if(result.getCount() == 0){
            //show message "AUCUN USER CORREPONDANT A CET ID
            return null;
        }
        result.moveToFirst();
        boolean endOfProject = result.getInt(5) == 1 ? true : false ;
        Group group = new Group(
                result.getInt(0),
                result.getInt(1),
                result.getString(2),
                result.getString(3),
                result.getString(4),
                endOfProject,
                null,
                null,
                null
        );
        return group;
    }

    public Cursor getAllGroups(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from GROUPS", null);
        return result;
    }

    /** ACCESSGROUP **/
    public boolean insertUserToGroup(User user, Group group){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("idUser", user.getIdUser());
        contentValues.put("idGroup", group.getIdGroup());

        long result = db.insert("ACCESSGRP", null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Integer deleteUserToGroup(User user, Group group){
        SQLiteDatabase db = getWritableDatabase() ;
        return db.delete("ACCESSGRP", "idGroup" + "= ?" + "AND idUser = ?",
                new String[]{
                    String.valueOf(group.getIdGroup()),
                    String.valueOf(user.getIdUser())
                }
        );
    }

    public Cursor getAllUserToGroup(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from ACCESSGRP", null);
        return result;
    }
}
