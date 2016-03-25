package parisdescartes.pjs4.fragments;

/**
 * Created by Kévin on 24/03/2016.
 */

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


import parisdescartes.pjs4.R;
import parisdescartes.pjs4.swipeCards.model.CardModel;
import parisdescartes.pjs4.swipeCards.view.CardContainer;
import parisdescartes.pjs4.swipeCards.view.SimpleCardStackAdapter;


/**
 * Created by Kévin on 24/03/2016.
 */
public class OneFragment extends Fragment {

    private CardContainer mCardContainer;

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

        Resources r = getResources();

        SimpleCardStackAdapter adapter = new SimpleCardStackAdapter(this.getContext());

        adapter.add(new CardModel("Title1", "Description goes here", r.getDrawable(R.drawable.picture1), 1));
        adapter.add(new CardModel("Title2", "Description goes here", r.getDrawable(R.drawable.picture2), 2));


        mCardContainer.setAdapter(adapter);
        adapter.add(new CardModel("Title3", "Description goes here", r.getDrawable(R.drawable.picture3), 3));
        mCardContainer.setAdapter(adapter);

        mCardContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mCardContainer.requestFocusFromTouch();
                mCardContainer.requestFocus();
                return false;
            }
        });
        return view;
    }
}