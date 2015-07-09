package lucas.waitassist;

import android.provider.BaseColumns;

/**
 * A simple class for storing SQL Strings that define the database.
 * @author Lucas Kushner
 *
 */
public class PatronContract
{        
    public static final String SQL_CREATE = "CREATE TABLE " + PatronEntry.TABLE_NAME + " (" +
                                            PatronEntry.COLUMN_NAME_ID + " AUTO INCREMENT INTEGER PRIMARY KEY," +
                                            PatronEntry.COLUMN_NAME_TABLE_ID + " TEXT, " +
                                            PatronEntry.COLUMN_NAME_SEAT_ID + " TEXT, " +
                                            PatronEntry.COLUMN_NAME_MENU_SELECTION + " TEXT)";
    public static final String SQL_DELETE = "DROP TABLE IF EXISTS " + PatronEntry.TABLE_NAME;

    public PatronContract() { }
    
    public static abstract class PatronEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "patrons";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_TABLE_ID = "tableId";
        public static final String COLUMN_NAME_SEAT_ID = "seatId";
        public static final String COLUMN_NAME_MENU_SELECTION = "menu";
    }
}
