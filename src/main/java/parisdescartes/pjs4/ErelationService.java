package com.example.rahman.pjs4Android;

import com.example.rahman.pjs4Android.classItems.Group;
import com.example.rahman.pjs4Android.classItems.Profil;
import com.example.rahman.pjs4Android.classItems.RequestConnect;
import com.example.rahman.pjs4Android.classItems.Skill;
import com.example.rahman.pjs4Android.classItems.User;


import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.PATCH;
import retrofit.http.POST;
import retrofit.http.PUT;

/**
 * Created by Killian on 08/02/2016.
 * Interface de communication avec notre propre API en Node.js
 * Les communications HTTP sont effectuées avec Retrofit
 */
public interface ErelationService {

    public static final String ENDPOINT = "http://192.168.1.89:8080";

	// l'utilisateur qui se connecte
    @POST("/api/user")
    void connect(@Body RequestConnect requestConnect, Callback<Profil> callback);

	//Création d'un utilisateur
    @POST("/api/user")
    void createUser(@Header("Token") String token);

	//Récupérer l'utilisateur
	@GET("api/profil/:idUser")
    void getProfile();

    //Modifier le profil user
	@PUT("api/profil")  //A refaire pour le moment

	//Supprimer le compte de l'user
	@DELETE("/api/user")
    void deleteUser();

    //Ajouter des compétences/skills
    @POST("/api/profil/skill")
    void addSkill(int idUser,@Body Skill skill);

    //Supprimer des compétences/skills
    @POST("/api/profil/skill")
    void deleteSkill(int idUser, @Body Skill skill);

    //Récupérer les groupes de l'user
	@GET("/api/group")

    //Créer un groupe
    @POST("/api/group")
    void createGroup(int idUser, @Body Group group);

    //Mise à jour d'un groupe
    @PATCH("api/group")

    //Fin du groupe/projet
    @PATCH("api/group")
    void endGroup();

	//Supprimer un groupe
	@DELETE("/api/group")
    void deleteGroup();
	
	//Récupérer les matchings
	@GET("/api/match/suggestion")

	
	//Accepter 
	@POST("/api/match/accept")
	void matchAccept(@Body User user);

	//Refuser
	@POST("/api/match/refuse")
    void matchRefuse(@Body User user);
	
	

}
