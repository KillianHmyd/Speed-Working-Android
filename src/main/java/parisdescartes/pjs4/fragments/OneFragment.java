package parisdescartes.pjs4.fragments;

/**
 * Created by Kévin on 24/03/2016.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.Toast;


import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.ArrayList;

import parisdescartes.pjs4.ErelationService;
import parisdescartes.pjs4.R;
import parisdescartes.pjs4.activities.ConnectActivity;
import parisdescartes.pjs4.activities.MainActivity;
import parisdescartes.pjs4.classItems.IdUser;
import parisdescartes.pjs4.classItems.Profil;
import parisdescartes.pjs4.classItems.ResponseService;
import parisdescartes.pjs4.swipeCards.model.CardModel;
import parisdescartes.pjs4.swipeCards.view.CardContainer;
import parisdescartes.pjs4.swipeCards.view.SimpleCardStackAdapter;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;


/**
 * Created by Kévin on 24/03/2016.
 */
public class OneFragment extends Fragment {

    private CardContainer mCardContainer;
    private ArrayList<Profil> suggestions;
    ProgressDialog progress;

    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_one, container, false);

        mCardContainer = (CardContainer) view.findViewById(R.id.layoutview);

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();

        ErelationService erelationConnect = new RestAdapter.Builder().
                setEndpoint(ErelationService.ENDPOINT).
                setConverter(new GsonConverter(gson)).
                build().
                create(ErelationService.class);

        erelationConnect.getSuggestion(AccessToken.getCurrentAccessToken().getToken(), 10, new Callback<ArrayList<Profil>>() {
            @Override
            public void success(ArrayList<Profil> profils, Response response) {
                setSuggestionsCards(profils);
            }

            @Override
            public void failure(RetrofitError error) {
                System.out.println(error);
                ((MainActivity) getActivity()).errorDialog("Connexion au serveur impossible.");
                ((MainActivity) getActivity()).errorDialog(error.getMessage());
            }
        });

        return view;
    }

    public CardModel.OnClickListener getOnClickListener(final Context context, final int idUser){
        return new CardModel.OnClickListener() {
            @Override
            public void OnClickListener() {
                Intent intent = new Intent(getContext(), ConnectActivity.class);
                //startActivity(intent);
            }
        };
    }

    public CardModel.OnCardDismissedListener getOnCardDismissedListener(final Context context, final int idUser){
        return new CardModel.OnCardDismissedListener() {

            @Override
            public void onLike() {
                ErelationService erelationConnect = new RestAdapter.Builder().
                        setEndpoint(ErelationService.ENDPOINT).
                        setConverter(new GsonConverter(new GsonBuilder()
                                .create())).
                        build().
                        create(ErelationService.class);

                erelationConnect.matchAccept(AccessToken.getCurrentAccessToken().getToken(), new IdUser(idUser), new Callback<ResponseService>() {
                    @Override
                    public void success(ResponseService responseService, Response response) {
                        Toast.makeText(context, "Utilisateur accepté", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(context, "Erreur", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onDislike() {
                ErelationService erelationConnect = new RestAdapter.Builder().
                        setEndpoint(ErelationService.ENDPOINT).
                        setConverter(new GsonConverter(new GsonBuilder()
                                .create())).
                        build().
                        create(ErelationService.class);

                erelationConnect.matchRefuse(AccessToken.getCurrentAccessToken().getToken(), new IdUser(idUser), new Callback<ResponseService>() {
                    @Override
                    public void success(ResponseService responseService, Response response) {
                        Toast.makeText(context, "Utilisateur refusé", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(context, "Erreur", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
    }

    public void setSuggestionsCards(ArrayList<Profil> profils){
        suggestions = new ArrayList<>();
        final SimpleCardStackAdapter adapter = new SimpleCardStackAdapter(getContext());
        for (int i = profils.size() - 1; i >= 0; i--) {
            suggestions.add(profils.get(i));
        }
        for (final Profil p : suggestions) {
            System.out.println(p.getIdUser());
            Resources r = getResources();
            Picasso.with(getContext()).load(p.getPicture()).into(new com.squareup.picasso.Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    CardModel cardModel = new CardModel(p.getFirstname() + " " + p.getLastname(), "Description goes here", bitmap, p.getIdUser(), getContext());
                    cardModel.setOnCardDismissedListener(getOnCardDismissedListener(getContext(), p.getIdUser()));
                    cardModel.setOnClickListener(getOnClickListener(getContext(), p.getIdUser()));
                    adapter.add(cardModel);
                    if (suggestions.indexOf(p) + 1 == suggestions.size())
                        mCardContainer.setAdapter(adapter);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {
                    //TODO CHARGEMENT
                }
            });

        }
    }
}