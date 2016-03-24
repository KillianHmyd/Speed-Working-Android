package parisdescartes.pjs4;

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
