/**
 * AndTinder v0.1 for Android
 *
 * @Author: Enrique López Mañas <eenriquelopez@gmail.com>
 * http://www.lopez-manas.com
 *
 * TAndTinder is a native library for Android that provide a
 * Tinder card like effect. A card can be constructed using an
 * image and displayed with animation effects, dismiss-to-like
 * and dismiss-to-unlike, and use different sorting mechanisms.
 *
 * AndTinder is compatible with API Level 13 and upwards
 *
 * @copyright: Enrique López Mañas
 * @license: Apache License 2.0
 */

package parisdescartes.pjs4.swipeCards.model;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

public class CardModel {

	private String   title;
	private String   description;
	private Drawable cardImageDrawable;
	private Drawable cardLikeImageDrawable;
	private Drawable cardDislikeImageDrawable;
	private Integer idUser;

    private OnCardDismissedListener mOnCardDismissedListener = new CardModel.OnCardDismissedListener() {
		@Override
		public void onLike() {
			Log.i("Swipeable Cards", "I like the card "+getTitle());

		}

		@Override
		public void onDislike() {
			Log.i("Swipeable Cards","I dislike the card "+getTitle());
		}
	};

    private OnClickListener mOnClickListener = new CardModel.OnClickListener() {
		@Override
		public void OnClickListener() {
			Log.i("Swipeable Cards","I am pressing the card "+getTitle());
		}
	};

    public interface OnCardDismissedListener {
        void onLike();
        void onDislike();
    }

    public interface OnClickListener {
        void OnClickListener();
    }

	public CardModel() {
		this(null, null, (Drawable)null, null);
	}

	public CardModel(String title, String description, Drawable cardImage, Integer idUser) {
		this.title = title;
		this.description = description;
		this.cardImageDrawable = cardImage;
		this.idUser = idUser;
	}

	public CardModel(String title, String description, Bitmap cardImage, Integer idUSer) {
		this.title = title;
		this.description = description;
		this.cardImageDrawable = new BitmapDrawable(null, cardImage);
		this.idUser = idUSer;
	}

	public String getTitle() {
		return title;
	}

	public Integer getIdUser(){ return idUser;}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Drawable getCardImageDrawable() {
		return cardImageDrawable;
	}

	public void setCardImageDrawable(Drawable cardImageDrawable) {
		this.cardImageDrawable = cardImageDrawable;
	}

	public Drawable getCardLikeImageDrawable() {
		return cardLikeImageDrawable;
	}

	public void setCardLikeImageDrawable(Drawable cardLikeImageDrawable) {
		this.cardLikeImageDrawable = cardLikeImageDrawable;
	}

	public Drawable getCardDislikeImageDrawable() {
		return cardDislikeImageDrawable;
	}

	public void setCardDislikeImageDrawable(Drawable cardDislikeImageDrawable) {
		this.cardDislikeImageDrawable = cardDislikeImageDrawable;
	}

    public void setOnCardDismissedListener( OnCardDismissedListener listener ) {
        this.mOnCardDismissedListener = listener;
    }

    public OnCardDismissedListener getOnCardDismissedListener() {
       return this.mOnCardDismissedListener;
    }


    public void setOnClickListener( OnClickListener listener ) {
        this.mOnClickListener = listener;
    }

    public OnClickListener getOnClickListener() {
        return this.mOnClickListener;
    }
}
