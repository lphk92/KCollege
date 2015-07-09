package lucas.waitassist;

import java.util.ArrayList;
import java.util.List;

import lucas.waitassist.PatronContract.PatronEntry;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * A helper for accessing and interacting with the database.
 * @author Lucas Kushner
 *
 */
public class PatronDbHelper extends SQLiteOpenHelper
{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Patron.db";
    
    public PatronDbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    
    /**
     * Adds the given Patron into the database.
     * @param patron
     * @return The row number of the newly entered Patron, or -1 if an error occurred.
     */
    public long addPatron(Patron patron)
    {
        ContentValues values = new ContentValues();
        values.put(PatronEntry.COLUMN_NAME_TABLE_ID, patron.getTableId());
        values.put(PatronEntry.COLUMN_NAME_SEAT_ID, patron.getSeatId());
        values.put(PatronEntry.COLUMN_NAME_MENU_SELECTION, patron.getMenuSelection());
        
        SQLiteDatabase db = this.getWritableDatabase();
        return db.insert(PatronEntry.TABLE_NAME, null, values);
    }
    
    /**
     * Retrieves all entries from the database and returns
     * them as Patron objects.
     * @return
     */
    public List<Patron> getAllPatrons()
    {
        String[] columns = new String[] {
                PatronEntry.COLUMN_NAME_TABLE_ID, 
                PatronEntry.COLUMN_NAME_SEAT_ID,
                PatronEntry.COLUMN_NAME_MENU_SELECTION
        };
        
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(PatronEntry.TABLE_NAME, columns, null, null, null, null, null);
        ArrayList<Patron> patrons = new ArrayList<Patron>();
        
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            Patron p = new Patron(
                    cursor.getString(cursor.getColumnIndexOrThrow(PatronEntry.COLUMN_NAME_TABLE_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(PatronEntry.COLUMN_NAME_SEAT_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(PatronEntry.COLUMN_NAME_MENU_SELECTION)));
            patrons.add(p);
            cursor.moveToNext();
        }
        
        return patrons;
    }
    
    /**
     * Clears all information in the database.
     */
    public void clear()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(PatronContract.SQL_DELETE);
        db.execSQL(PatronContract.SQL_CREATE);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(PatronContract.SQL_CREATE);
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(PatronContract.SQL_DELETE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        onUpgrade(db, oldVersion, newVersion);
    }
}
