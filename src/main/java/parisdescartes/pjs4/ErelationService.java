package parisdescartes.pjs4;


import java.util.ArrayList;

import parisdescartes.pjs4.classItems.Conversation;
import parisdescartes.pjs4.classItems.Group;
import parisdescartes.pjs4.classItems.IdGroup;
import parisdescartes.pjs4.classItems.IdUser;
import parisdescartes.pjs4.classItems.Profil;
import parisdescartes.pjs4.classItems.ResponseAddUser;
import parisdescartes.pjs4.classItems.ResponseCreateGroup;
import parisdescartes.pjs4.classItems.ResponseMatch;
import parisdescartes.pjs4.classItems.ResponseService;
import parisdescartes.pjs4.classItems.ServiceAddUserToGroup;
import parisdescartes.pjs4.classItems.Skill;
import parisdescartes.pjs4.classItems.User;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.PATCH;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Killian on 08/02/2016.
 * Interface de communication avec notre propre API en Node.js
 * Les communications HTTP sont effectuées avec Retrofit
 */
public interface ErelationService {

    public static final String ENDPOINT = "http://51.255.30.141/api";

	// l'utilisateur qui se connecte
    @GET("/user")
    void connect(@Header("Token") String token, Callback<User> callback);

	//Création d'un utilisateur
    @POST("/user")
    void createUser(@Header("Token") String token, Callback<ResponseService> callback);

	//Récupérer l'utilisateur
	@GET("/profil/{idUser}")
    void getProfil(@Header("Token") String token, @Path("idUser") int idUser, Callback<Profil> callback);

    //Modifier le profil user
	@PUT("/profil")
    void updateProfil(@Header("Token") String token, @Body Profil profil, Callback<ResponseService> callback);

	//Supprimer le compte de l'user
	@DELETE("/user")
    void deleteUser(@Header("Token") String token, Callback<ResponseService> callback);

    //Ajouter des compétences/skills
    @POST("/profil/skill")
    void addSkill(@Header("Token") String token, @Body Skill skill, Callback<ResponseService> callback);

    //Supprimer des compétences/skills
    @DELETE("/profil/skill")
    void deleteSkill(@Header("Token") String token, @Body Skill skill, Callback<ResponseService> callback);

    //Récupérer les groupes de l'user
	@GET("/group")
    void getGroups(@Header("Token") String token, Callback<ArrayList<Group>> callback);

    //Créer un groupe
    @POST("/group")
    void createGroup(@Header("Token") String token, @Body Group group, Callback<ResponseCreateGroup> callback);

    //Mise à jour d'un groupe
    @PATCH("/group")
    void updateGroup(@Header("Token") String token, @Body Group group, Callback<ResponseService> callback);

    //Fin du groupe/projet
    @PATCH("/group/end")
    void endGroup(@Header("Token") String token, @Body IdGroup idGroup, Callback<ResponseService> callback);

	//Supprimer un groupe
	@DELETE("/group")
    void deleteGroup(@Header("Token") String token, @Body int idGroup, Callback<ResponseService> callback);

    @POST("/group/user")
    void addUserToGroup(@Header("Token") String token, @Body ServiceAddUserToGroup serviceAddUserToGroup, Callback<ResponseAddUser> callback);
	
	//Récupérer les matchings
	@GET("/match/suggestion")
    void getSuggestion(@Header("Token") String token, @Query("nbRelations") int nbRelations, Callback<ArrayList<Profil>> callback);
	
	//Accepter 
	@POST("/match/accept")
	void matchAccept(@Header("Token") String token, @Body IdUser idUser, Callback<ResponseMatch> callback);

	//Refuser
	@POST("/match/refuse")
    void matchRefuse(@Header("Token") String token, @Body IdUser idUser, Callback<ResponseMatch> callback);

    //Récupérer conversation
    @GET("/conversations")
    void getConversation(@Header("Token") String token, Callback<ArrayList<Conversation>> callback);

    //Récupérer un groupe
    @GET("/group/{idGroup}")
    void getGroup(@Header("Token") String token, @Path("idGroup") int idGroup, Callback<Group> callback);


}
