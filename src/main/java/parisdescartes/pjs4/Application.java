package parisdescartes.pjs4;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Killian on 24/03/2016.
 */
public class Application extends android.app.Application {
    private ERelationDbHelper db = new ERelationDbHelper(this);

    public ERelationDbHelper getDb(){
        return db;
    }

    public void resetDb(){
        this.db = new ERelationDbHelper(this);
    }

}
