package parisdescartes.pjs4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import parisdescartes.pjs4.classItems.Contributor;
import parisdescartes.pjs4.classItems.Conversation;
import parisdescartes.pjs4.classItems.Group;
import parisdescartes.pjs4.classItems.Message;
import parisdescartes.pjs4.classItems.OwnSkill;
import parisdescartes.pjs4.classItems.Participant;
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
                    "lastName TEXT," +
                    "email TEXT," +
                    "birthday TEXT," +
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
                    "idConv INTEGER NOT NULL," +
                    "FOREIGN KEY(idConv) REFERENCES CONVERSATIONS(idConv)," +
                    "FOREIGN KEY (idLeader) REFERENCES PROFIL(idUser)" +
            ")"
            ;

    public static final String ERelation_CREATE_SKILL_TABLE =
            "create table SKILL (" +
                    "idSkill INTEGER PRIMARY KEY NOT NULL," +
                    "skillName TEXT NOT NULL"+
            ")"
            ;

    public static final String ERelation_CREATE_OWNSKILL_TABLE =
            "create table OWNSKILL (" +
                    "idUser INTEGER NOT NULL, " +
                    "idSkill INTEGER NOT NULL," +
                    "PRIMARY KEY (idUser, idSkill)," +
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
            "create table MESSAGES (" +
                    "idMsg INTEGER NOT NULL, " +
                    "msgContent TEXT NOT NULL," +
                    "idConv INTEGER NOT NULL," +
                    "idUser INTEGER NOT NULL," +
                    "date TEXT NOT NULL," +
                    "FOREIGN KEY(idUser) REFERENCES USERS(idUser)," +
                    "FOREIGN KEY(idConv) REFERENCES CONVERSATIONS(idConv)," +
                    "PRIMARY KEY(idMsg)"+
            ")"
            ;

    public static final String ERelation_CREATE_CONVERSATIONS_TABLE =
            "create table CONVERSATIONS (" +
                    "idConv INTEGER NOT NULL, " +
                    "nameConv TEXT NOT NULL, " +
                    "lastMessage TEXT," +
                    "dateMessage TEXT," +
                    "idMsg INT,"+
                    "idUserMsg INT,"+
                    "PRIMARY KEY(idConv)"+
            ")"
            ;

    public static final String ERelation_CREATE_ACCESSCONV_TABLE =
            "create table ACCESSCONV (" +
                    "idConv INTEGER NOT NULL," +
                    "idUser INTEGER NOT NULL," +
                    "FOREIGN KEY(idUser) REFERENCES USERS(idUser)," +
                    "FOREIGN KEY(idConv) REFERENCES CONVERSATIONS(idConv)," +
                    "PRIMARY KEY(idConv, idUser)"+
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
        db.execSQL(ERelation_CREATE_SKILL_TABLE);
        db.execSQL(ERelation_CREATE_OWNSKILL_TABLE);
        db.execSQL(ERelation_CREATE_ACCESSGRP_TABLE);
        db.execSQL(ERelation_CREATE_MESSAGES_TABLE);
        db.execSQL(ERelation_CREATE_CONVERSATIONS_TABLE);
        db.execSQL(ERelation_CREATE_ACCESSCONV_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS USER");
        db.execSQL("DROP TABLE IF EXISTS PROFIL");
        db.execSQL("DROP TABLE IF EXISTS GROUPS");
        db.execSQL("DROP TABLE IF EXISTS SKILL");
        db.execSQL("DROP TABLE IF EXISTS OWNSKILL");
        db.execSQL("DROP TABLE IF EXISTS ACCESSGRP");
        db.execSQL("DROP TABLE IF EXISTS MESSAGES");
        db.execSQL("DROP TABLE IF EXISTS CONVERSATIONS");
        db.execSQL("DROP TABLE IF EXISTS ACCESSCONV");
        onCreate(db);
    }


    public void reset() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS USER");
        db.execSQL("DROP TABLE IF EXISTS PROFIL");
        db.execSQL("DROP TABLE IF EXISTS GROUPS");
        db.execSQL("DROP TABLE IF EXISTS SKILL");
        db.execSQL("DROP TABLE IF EXISTS OWNSKILL");
        db.execSQL("DROP TABLE IF EXISTS ACCESSGRP");
        db.execSQL("DROP TABLE IF EXISTS MESSAGES");
        db.execSQL("DROP TABLE IF EXISTS CONVERSATIONS");
        db.execSQL("DROP TABLE IF EXISTS ACCESSCONV");
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

        long result = db.insertWithOnConflict("USER", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
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
        contentValues.put("birthday", profile.getBirthday());
        contentValues.put("email", profile.getEmail());
        contentValues.put("picture", profile.getPicture());
        int match;
        if(matched)
            match=1;
        else
            match=0;
        contentValues.put("matched", match);
        System.out.println("IDUSER INSERT : " + profile.getIdUser());
        long result = db.insertWithOnConflict("PROFIL", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        for(OwnSkill o : profile.getSkill()){
            insertOwnSkill(profile.getIdUser(), o.getIdSkill()
            );
        }
        if(result == -1){
            return false;
        }else{
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
        System.out.println("IDUSER GET : " + idUser);
        if(result.getCount() == 0){
            return null;
        }

        result.moveToFirst();
        Profil profil = new Profil(
                result.getInt(0),
                result.getString(1),
                result.getString(2),
                result.getString(3),
                result.getString(4),
                result.getString(5),
                result.getString(6),
                null,
                null
        );

        profil.setSkill(getAllOwnSkill(profil.getIdUser()));
        return profil;
    }

    public Cursor getAllProfile(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from PROFIL", null);
        return result;
    }

    public Cursor getMatchedProfile_data(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from PROFIL WHERE matched = 1", null);
        return result;
    }

    public ArrayList<Profil> getMatchedProfile(){
        Cursor result = getMatchedProfile_data();
        ArrayList<Profil> profils = new ArrayList<>();
        while(result.moveToNext()){
            Profil profil = new Profil(
                    result.getInt(0),
                    result.getString(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getString(6),
                    null,
                    null
            );
            profil.setSkill(getAllOwnSkill(profil.getIdUser()));
            profils.add(profil);
        }


        return  profils;
    }

    public Cursor getUnmatchedProfile(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from PROFIL WHERE matched = 0", null);
        return result;
    }

    /** SKILL**/
    public boolean insertSkill(Skill skill){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("idSkill", skill.getIdSkill());
        contentValues.put("skillName", skill.getSkillName());

        long result = db.insertWithOnConflict("SKILL", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
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

    public ArrayList<Skill> getAllSkill(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from SKILL", null);
        if(result.getCount() == 0){
            //show message "AUCUN USER CORREPONDANT A CET ID
            return null;
        }
        ArrayList<Skill> skills = new ArrayList<Skill>();
        while(result.moveToNext()){
            Skill skill = new Skill(
                    result.getInt(0),
                    result.getString(1),
                    null,
                    null
            );
            skills.add(skill);
        }
        return skills ;
    }

    public Skill getSkill(long idSkill){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from SKILL WHERE idSkill = ?", new String[] {String.valueOf(idSkill)});
        if(result.getCount() == 0){
            //show message "AUCUN USER CORREPONDANT A CET ID
            return null;
        }
        result.moveToFirst();
        Skill skill = new Skill (
                result.getInt(0),
                result.getString(1),
                null,
                null
        );
        return skill ;
    }

    /** OWNSKILL **/
    public boolean insertOwnSkill(Integer idUser, Integer idSkill){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("idUser", idUser);
        contentValues.put("idSkill", idSkill);

        long result = db.insertWithOnConflict("OWNSKILL", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
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

    public ArrayList<OwnSkill> getAllOwnSkill(int idUser){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from OWNSKILL WHERE idUser = ?", new String[]{idUser + ""});
        ArrayList<OwnSkill> ownSkills = new ArrayList<OwnSkill>();

        if(result.getCount() == 0){
            //show message "AUCUN USER CORREPONDANT A CET ID
            return null;
        }
        while(result.moveToNext()){
            OwnSkill skill= new OwnSkill(
                    result.getInt(1),
                    result.getInt(0)
            );
            ownSkills.add(skill);
        }
        return ownSkills ;
    }

    /** GROUPS **/
    public boolean insertGroup(Group group){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("idGroup", group.getIdGroup());
        contentValues.put("nameGroup", group.getNameGroup());
        contentValues.put("presentation", group.getPresentation());
        contentValues.put("idLeader", group.getIdLeader());
        contentValues.put("finish", group.isFinish());
        contentValues.put("idConv", group.getIdConv());
        db.insertWithOnConflict("GROUPS", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);

        long result = db.insertWithOnConflict("GROUPS", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
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
        contentValues.put("nameGroup", group.getNameGroup());
        contentValues.put("presentation", group.getPresentation());
        contentValues.put("idLeader", group.getIdLeader());
        contentValues.put("finish", group.isFinish());

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

    public Integer deleteAllGroup(){
        SQLiteDatabase db = getWritableDatabase() ;
        return db.delete("GROUPS", null, null);
    }

    public Group getGroup(int idGroup){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from GROUPS WHERE idGroup = ?", new String[] {idGroup + ""});

        if(result.getCount() == 0){
            //show message "AUCUN USER CORREPONDANT A CET ID
            return null;
        }
        result.moveToFirst();
        boolean endOfProject = result.getInt(4) == 1 ? true : false ;
        Group group = new Group(
                result.getInt(0),
                result.getString(1),
                result.getString(2),
                result.getInt(3),
                endOfProject,
                null,
                result.getInt(5),
                null,
                null
        );
        group.setContributors(getAllUserToGroup(result.getInt(0)));
        return group;
    }

    public ArrayList<Group> getAllGroups(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from GROUPS", null);
        ArrayList<Group> groups = new ArrayList<>();
        if(result.getCount() == 0){
            //show message "AUCUN USER CORREPONDANT A CET ID
            return null;
        }
        while(result.moveToNext()){
            boolean endOfProject = result.getInt(4) == 1 ? true : false ;
            Group group = new Group(
                    result.getInt(0),
                    result.getString(1),
                    result.getString(2),
                    result.getInt(3),
                    endOfProject,
                    null,
                    result.getInt(5),
                    null,
                    null
            );
            group.setContributors(getAllUserToGroup(result.getInt(0)));
            groups.add(group);
        }
        return groups;
    }

    /** ACCESSGROUP **/
    public boolean insertUserToGroup(int idUser, int idGroup){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("idUser", idUser);
        contentValues.put("idGroup", idGroup);

        long result = db.insertWithOnConflict("ACCESSGRP", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
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

    public ArrayList<Contributor> getAllUserToGroup(int idGroup){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from ACCESSGRP WHERE idGroup = ?", new String[]{idGroup + ""});
        ArrayList<Contributor> contributors = new ArrayList<>();
        while(result.moveToNext()){
            contributors.add(new Contributor(Integer.valueOf(result.getInt(0)), Integer.valueOf(result.getInt(1))));
        }
        return contributors;
    }

    /** MESSAGES **/
    public boolean insertMessage(Message message){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("idMsg", message.getIdMsg());
        contentValues.put("msgContent", message.getString());
        contentValues.put("idConv", message.getIdConv());
        contentValues.put("idUser", message.getIdUser());
        contentValues.put("date", message.getDate().toString());
        db.insertWithOnConflict("MESSAGES", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);

        long result = db.insertWithOnConflict("MESSAGES", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean updateMessage(Message message){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("idMsg", message.getIdMsg());
        contentValues.put("msgContent", message.getString());
        contentValues.put("idConv", message.getIdConv());
        contentValues.put("idUser", message.getIdUser());

        long result = db.update("MESSAGES", contentValues, "idMsg = ?", new String[]{message.getIdMsg() + ""});
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Integer deleteMessage(long id){
        SQLiteDatabase db = getWritableDatabase() ;
        return db.delete("MESSAGES", "idMsg" + " = ?", new String[]{String.valueOf(id)});
    }

    public Integer deleteAllMessage(){
        SQLiteDatabase db = getWritableDatabase() ;
        return db.delete("MESSAGES", null, null);
    }

    public Message getMessage(int idMsg) throws ParseException {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from MESSAGES WHERE idMsg = ?", new String[] {idMsg+ ""});

        if(result.getCount() == 0){
            //show message "AUCUN USER CORREPONDANT A CET ID
            return null;
        }
        result.moveToFirst();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'000Z'");
        Date date =  df.parse(result.getString(4));
        Message message= new Message(
                result.getInt(0),
                result.getString(1),
                result.getInt(2),
                result.getInt(3),
                date,
                null,
                null
        );
        return message;
    }

    public ArrayList<Message> getAllMessages() throws ParseException {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from MESSAGES", null);
        ArrayList<Message> messages = new ArrayList<>();
        if(result.getCount() == 0){
            //show message "AUCUN USER CORREPONDANT A CET ID
            return null;
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'000Z'");
        while(result.moveToNext()){
            Date date =  df.parse(result.getString(4));
            Message message= new Message(
                    result.getInt(0),
                    result.getString(1),
                    result.getInt(2),
                    result.getInt(3),
                    date,
                    null,
                    null
            );
            messages.add(message);
        }
        return messages;
    }

    /** CONVERSATION **/
    public boolean insertConversation(Conversation conv){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("idConv", conv.getIdConv());
        contentValues.put("nameConv", conv.getNameConv());
        ArrayList<Participant> participants = conv.getParticipants();
        for(Participant p : participants){
            insertUserToConv(p.getIdUser(), p.getIdConv());
        }
        if(conv.getLastMessage() != null) {
            contentValues.put("lastMessage", conv.getLastMessage().getString());
            contentValues.put("dateMessage", conv.getLastMessage().getDate().toString());
            contentValues.put("idUserMsg", conv.getLastMessage().getIdUser());
        }

        long result = db.insertWithOnConflict("CONVERSATIONS", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean updateConv(Conversation conv){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("idConv", conv.getIdConv());
        contentValues.put("nameConv", conv.getNameConv());

        long result = db.update("CONVERSATIONS", contentValues, "idConv = ?", new String[]{conv.getIdConv() + ""});
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public Integer deleteConv(long idConv){
        SQLiteDatabase db = getWritableDatabase() ;
        return db.delete("CONVERSATIONS", "idConv" + " = ?", new String[]{String.valueOf(idConv)});
    }

    public Integer deleteAllConv(){
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("CONVERSATIONS", null, null);
    }

    public Conversation getConv(int idConv) throws ParseException {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from CONVERSATIONS WHERE idConv = ?", new String[] {idConv + ""});

        if(result.getCount() == 0){
            //show message "AUCUN USER CORREPONDANT A CET ID
            return null;
        }
        result.moveToFirst();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'000Z'");
        Date date = df.parse(result.getString(3));
        Conversation conv = new Conversation(
                result.getInt(0),
                result.getString(1),
                new Message(result.getInt(4), result.getString(2), -1, result.getInt(5), date,null,null),
                null,
                null
        );
        return conv;
    }

    public Message getLastUpdate() throws ParseException {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM MESSAGES m, CONVERSATIONS c" +
                "WHERE m.idConv = c.idConv", null);

        result.moveToFirst();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'000Z'");
        Date date = df.parse(result.getString(4));
        Message message = new Message(
                result.getInt(0),
                result.getString(1),
                result.getInt(2),
                result.getInt(3),
                date,
                null,
                null
        );
        return message;
    }

    public ArrayList<Conversation> getAllConv() throws ParseException {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from CONVERSATIONS", null);

        ArrayList<Conversation> conversations = new ArrayList<Conversation>();
        if(result.getCount() == 0){
            //show message "AUCUN USER CORREPONDANT A CET ID
            return null;
        }

        DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        while(result.moveToNext()) {
            Date date = null;
            Message m = null;
            if(result.getString(3) != null) {
                date = df.parse(result.getString(3));
                m = new Message(result.getInt(4), result.getString(2), -1, result.getInt(5), date,null,null);
            }
            Conversation conv = new Conversation(
                    result.getInt(0),
                    result.getString(1),
                    m,
                    null,
                    null
            );
            conv.setParticipants(getAllUserToConv(result.getInt(0)));
            conversations.add(conv);
        }
        return conversations;
    }

    /** ACCESSCONV **/
    public boolean insertUserToConv(int idUser, int idConv){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("idConv", idConv);
        contentValues.put("idUser", idUser);

        long result = db.insertWithOnConflict("ACCESSCONV", null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public ArrayList<Participant> getAllUserToConv(int idConv){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("select * from ACCESSCONV WHERE idConv = ?", new String[]{idConv + ""});
        ArrayList<Participant> participants = new ArrayList<>();
        while(result.moveToNext()){
            participants.add(new Participant(Integer.valueOf(result.getInt(1)), Integer.valueOf(result.getInt(0))));
        }
        return participants;
    }

    public Integer deleteUserToConv(User user, Conversation conv){
        SQLiteDatabase db = getWritableDatabase() ;
        return db.delete("ACCESSCONV", "idConv" + "= ?" + "AND idUser = ?",
                new String[]{
                        String.valueOf(user.getIdUser()),
                        String.valueOf(conv.getIdConv())
                }
        );
    }

    public Integer deleteAllUserToConv(){
        SQLiteDatabase db = getWritableDatabase() ;
        return db.delete("ACCESSCONV", null,null);
    }


}
