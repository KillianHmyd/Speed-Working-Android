package parisdescartes.pjs4;


import java.util.ArrayList;

import parisdescartes.pjs4.classItems.Group;
import parisdescartes.pjs4.classItems.Profil;
import parisdescartes.pjs4.classItems.ResponseService;
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

    public static final String ENDPOINT = "http://192.168.1.89:8080/api";

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
    void createGroup(@Header("Token") String token, @Body Group group, Callback<ResponseService> callback);

    //Mise à jour d'un groupe
    @PATCH("/group")
    void updateGroup(@Header("Token") String token, @Body Group group, Callback<ResponseService> callback);

    //Fin du groupe/projet
    @PATCH("/group/end")
    void endGroup(@Header("Token") String token, @Body int idGroup, Callback<ResponseService> callback);

	//Supprimer un groupe
	@DELETE("/group")
    void deleteGroup(@Header("Token") String token, @Body int idGroup, Callback<ResponseService> callback);
	
	//Récupérer les matchings
	@GET("/match/suggestion")
    void getSuggestion(@Header("Token") String token, @Query("nbRelations") int nbRelations, Callback<ArrayList<Profil>> callback);
	
	//Accepter 
	@POST("/match/accept")
	void matchAccept(@Header("Token") String token, @Body User user, Callback<ResponseService> callback);

	//Refuser
	@POST("/match/refuse")
    void matchRefuse(@Header("Token") String token, @Body User user, Callback<ResponseService> callback);
	
	

}
