package dkl.kshare;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbManager extends SQLiteOpenHelper {
	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 4;
	
	// Database Name
	private static final String DATABASE_NAME = "postsManager";
	
	// Posts Table Name
	private static final String TABLE_POSTS = "posts";
	private static final String TABLE_USERS = "users";
	private static final String TABLE_RIDERS = "riders";
	
	// Posts Table Columns Names
	private static final String KEY_USER_ID = "user"; // used in both posts and users table
	private static final String KEY_MEETING_LOCATION = "meeting_loc";
	private static final String KEY_DESTINATION = "destination";
	private static final String KEY_DATE_TIME = "date_and_time";
	private static final String KEY_AVAILABLE_SEATS = "available_seats";
	private static final String KEY_POST_ID = "id";
	
	// Users Table Columns Names
	private static final String KEY_PASSWORD = "password";
	private static final String KEY_EMAIL = "email";
	
	//creates Posts table
			private static final String CREATE_POSTS_TABLE = " CREATE TABLE IF NOT EXISTS " + TABLE_POSTS + "("
					+ KEY_USER_ID + " INTEGER, " + KEY_MEETING_LOCATION + " TEXT, " 
					+ KEY_DESTINATION +  " TEXT, " 			+ KEY_DATE_TIME + " DATETIME, " 
					+ KEY_AVAILABLE_SEATS + " TEXT, " 		+ KEY_POST_ID + " INTEGER PRIMARY KEY " 
					+ ")" ;
			
	// creates users table
	private static final String CREATE_USERS_TABLE = " CREATE TABLE IF NOT EXISTS " + TABLE_USERS 
			+ "("
			+ KEY_USER_ID + " INTEGER PRIMARY KEY, " + KEY_PASSWORD + " TEXT, " 
			+ KEY_EMAIL + " TEXT " 
			+ ")";
	
	// creates riders table
		private static final String CREATE_RIDERS_TABLE = " CREATE TABLE IF NOT EXISTS " + TABLE_RIDERS 
				+ "("
				+ KEY_USER_ID + " INTEGER, " + KEY_POST_ID + " INTEGER "
				+ ")";
	
	public DbManager(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL( CREATE_POSTS_TABLE );	
		db.execSQL( CREATE_USERS_TABLE );
		db.execSQL( CREATE_RIDERS_TABLE );
	}
	
	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_POSTS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_RIDERS);
		
		// Create table again
		onCreate(db);
	}
	
	// Deletes database
	public void deleteDatabase(SQLiteDatabase db) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + "posts");
		db.execSQL("DROP TABLE IF EXISTS " + "users");
		
		// Create db again
		onCreate(db);
	}
	
	// Deletes all users
		public void deleteUsers(SQLiteDatabase db) {
			// Drop older table if existed
			db.execSQL("DROP TABLE IF EXISTS " + "users");
			db.execSQL( CREATE_USERS_TABLE );
		}
		
	// Deletes all posts
			public void deletePosts(SQLiteDatabase db) {
				// Drop older table if existed
				db.execSQL("DROP TABLE IF EXISTS " + "posts");
				db.execSQL( CREATE_POSTS_TABLE );
			}
			
	// Deletes all riders
	public void deleteRiders(SQLiteDatabase db) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + "riders");
		db.execSQL( CREATE_RIDERS_TABLE );
	}

	
	public static final SimpleDateFormat SDF = new SimpleDateFormat(
			"EEE, MMM dd, yyyy 'at' HH:mm:ss");
	
	// Converts Date into String
	public String convertDateToString(Date date) {
		String dateTime = SDF.format(date);
		return dateTime;	
	}
	
	// Converts String into Date
	public Date convertStringToDate(String s) throws ParseException {
		Date dateTime = SDF.parse(s);
		return dateTime;
	}
	
	
	// Adding new post
	public long addPost(Post post) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_USER_ID, post.getUserID()); 					// Post User id
		values.put(KEY_MEETING_LOCATION, post.getMeetingLocation()); 	// Post Meeting location
		values.put(KEY_DESTINATION, post.getDestination()); 		// Post Destination
		values.put(KEY_DATE_TIME, convertDateToString(post.getMeetingTime())); // Post Date and time
		values.put(KEY_AVAILABLE_SEATS, post.getAvailableSeats()); 	// Post Available Seats
		
		// Inserting Row
		long id = db.insert(TABLE_POSTS,null, values);
		db.close(); // Closing database connection
		
		// returns post id
		return id;
	}
	
	// Adding new user
	public long addUser(User user) {
			SQLiteDatabase db = this.getWritableDatabase();
			
			ContentValues values = new ContentValues();
			values.put(KEY_PASSWORD, user.getpassword()); 	// User password
			values.put(KEY_EMAIL, user.getEmail()); 		// User email
			
			// Inserting Row
			long id = db.insert(TABLE_USERS,null, values);
			db.close(); // Closing database connection
			
			//returns user id
			return id;
		}
	
	// adds new rider
	public void addRider(int userId, int postId) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_USER_ID, userId); 	// User id
		values.put(KEY_POST_ID, postId); 	// post id
		
		// Inserting Row
		db.insert(TABLE_RIDERS,null, values);
		db.close(); // Closing database connection
	}
	
	
	// Getting single post by post id
	public Post getPost(int id) throws ParseException {
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.query(TABLE_POSTS, new String[] {KEY_USER_ID, 
				KEY_MEETING_LOCATION, KEY_DESTINATION, KEY_DATE_TIME,
				KEY_AVAILABLE_SEATS, KEY_POST_ID }, KEY_POST_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
		Post post = new Post(
				cursor.getInt(0),  		// user id
				cursor.getString(1),	// meet loc
				cursor.getString(2), 	// destination
				this.convertStringToDate(cursor.getString(3)), 	// date
				cursor.getInt(4),		// seats
				cursor.getInt(5) );		// post id
		// return post
		return post;
		}
	
	// Getting single user by id
	public User getUser(int id) {
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.query(TABLE_USERS, new String[] { KEY_USER_ID, 
				KEY_PASSWORD, KEY_EMAIL }, KEY_USER_ID + " = ?",
				new String[] { String.valueOf(id) }, null, null, null, null);
		if (cursor != null)
			cursor.moveToFirst();
		
		String password = cursor.getString(cursor.getColumnIndex(KEY_PASSWORD));
		String email = cursor.getString(cursor.getColumnIndex(KEY_EMAIL));
		User user = new User(
				password,
				email,
				Integer.parseInt(cursor.getString(0)) );
		// return user
		return user;
		}
	
	// Getting single user by email
		public User getUser(String email) {
			SQLiteDatabase db = this.getReadableDatabase();
			
			Cursor cursor = db.query(TABLE_USERS, new String[] { KEY_USER_ID, 
					KEY_PASSWORD, KEY_EMAIL }, KEY_EMAIL + " = ?",
					new String[] { String.valueOf(email) }, null, null, null, null);
			if (cursor != null)
				cursor.moveToFirst();
			
			User user = new User(
					cursor.getString(cursor.getColumnIndex(KEY_PASSWORD)),
					cursor.getString(cursor.getColumnIndex(KEY_EMAIL)),
					Integer.parseInt(cursor.getString(0)) );
			// return user
			return user;
			}
	
	
	
	
	// Getting All Posts
	public List<Post> getAllPosts() throws ParseException {
		List<Post> postList = new ArrayList<Post>();
		// Select All Query
		String selectQuery = "SELECT * FROM " + TABLE_POSTS;
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		//looping through all rows and adding to list
		if(cursor.moveToFirst()) {
			do{
				Post post = new Post();
				post.setUserID(cursor.getInt(0));
				post.setMeetingLocation(cursor.getString(1));
				post.setDestination(cursor.getString(2));
				post.setMeetingTime(this.convertStringToDate(cursor.getString(3)));
				post.setAvailableSeats(cursor.getInt(4));
				post.setPostID(cursor.getInt(5));
				// Adding post to list
				postList.add(post);
			} while(cursor.moveToNext());
		}
		return postList;
	}

	// Getting All Users
	public List<User> getAllUsers() {
		List<User> userList = new ArrayList<User>();
		// Select All Query
		String selectQuery = "SELECT * FROM " + TABLE_USERS;
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		//looping through all rows and adding to list
		if(cursor.moveToFirst()) {
			do{
				User user = new User(
						cursor.getString(1),
						cursor.getString(2),
						cursor.getInt(0));
				// Adding post to list
				userList.add(user);
			} while(cursor.moveToNext());
		}
		return userList;
	}
	
	// Getting All Riders
		public List<Rider> getAllRiders() {
			List<Rider> riderList = new ArrayList<Rider>();
			// Select All Query
			String selectQuery = "SELECT * FROM " + TABLE_RIDERS;
			
			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectQuery, null);
			
			//looping through all rows and adding to list
			if(cursor.moveToFirst()) {
				do{
					Rider rider= new Rider(
							cursor.getInt(0),
							cursor.getInt(1));
							
					// Adding post to list
					riderList.add(rider);
				} while(cursor.moveToNext());
			}
			return riderList;
		}
		
	// gets all posts from a certain user
	public List<Post> getAllUsersPosts(int userId) throws ParseException{
		List<Post> allUsersPosts = new ArrayList<Post>();
		List<Post> allPosts = this.getAllPosts();
		for(Post p: allPosts)
			if(p.getUserID()==userId)
				allUsersPosts.add(p);
		return allUsersPosts;
	}

	// gets all rides from a certain user
	public List<Rider> getAllUsersRides(int userId) {
		List<Rider> allUsersRides = new ArrayList<Rider>();
		List<Rider> allRides = this.getAllRiders();
		for(Rider r: allRides)
			if(r.getUserId()==userId)
				allUsersRides.add(r);
		return allUsersRides;
	}
	
	// gets all rides from a certain user
		public List<Rider> getAllPostsRides(int postId) {
			List<Rider> allPostsRides = new ArrayList<Rider>();
			List<Rider> allRides = this.getAllRiders();
			for(Rider r: allRides)
				if(r.getPostId()==postId)
					allPostsRides.add(r);
			return allPostsRides;
		}
		
		
	// Updating single Post
	public int updatePost(Post post) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_USER_ID, post.getUserID()); // Post User id
		values.put(KEY_MEETING_LOCATION, post.getMeetingLocation()); // Post Meeting location
		values.put(KEY_DESTINATION, post.getDestination()); // Post Destination
		values.put(KEY_DATE_TIME, convertDateToString(post.getMeetingTime())); // Post Date
		values.put(KEY_AVAILABLE_SEATS, post.getAvailableSeats()); // Post Available seats
		values.put(KEY_POST_ID, post.getPostID()); // Post ID
		
		// Updating row
		return db.update(TABLE_POSTS, values, KEY_POST_ID + " = ? ",
				new String[] {String.valueOf(post.getPostID()) });
	}
	
	// Deleting single post
	public void deletePost(Post post) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_POSTS,KEY_POST_ID + " = ?",
				new String[] {String.valueOf(post.getPostID()) });
		db.close();
	}
	
	// Deleting single user
		public void deleteUser(User user) {
			SQLiteDatabase db = this.getWritableDatabase();
			db.delete(TABLE_USERS,KEY_USER_ID + " = ?",
					new String[] {String.valueOf(user.getId()) });
			db.close();
		}
	// counts number of entries
	public int getEntryCount(String TABLE_NAME) {
		String countQuery = "SELECT * FROM " + TABLE_NAME;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		
		// Return count
		return cursor.getCount();
	}
}