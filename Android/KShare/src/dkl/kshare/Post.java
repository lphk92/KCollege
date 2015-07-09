package dkl.kshare;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Post
{
    private int userID;
    private String meetingLoc;
    private String destination;
    private int postID;
    private Date meetingTime;
    private int availableSeats;

    @Override
    public String toString()
    {
        SimpleDateFormat format = new SimpleDateFormat();
        return this.destination + " (" + format.format(this.meetingTime) + ")";
    }
    /*
     * A blank constructor
     * 
     * *
     */
    public Post()
    {
        // blank!
    }

    /*
     * A constructor without a postID initialized
     * 
     * *
     */
    public Post(int userID, String meetingLocation, String destination,
            Date time, int seats)
    {
        this.userID = userID;
        this.meetingLoc = meetingLocation;
        this.destination = destination;
        this.meetingTime = time;
        this.setAvailableSeats(seats);

    }

    /*
     * A full constructor (with postID)
     * 
     * *
     */
    public Post(int userID, String meetingLocation, String destination,
            Date time, int seats, int postID)
    {
        this.userID = userID;
        meetingLoc = meetingLocation;
        this.destination = destination;
        this.meetingTime = time;
        this.setAvailableSeats(seats);
        this.postID = postID;

    }

    // Getters
    public int getPostID()
    {
        return this.postID;
    }

    public int getUserID()
    {
        return this.userID;
    }

    public String getDestination()
    {
        return this.destination;
    }

    public String getMeetingLocation()
    {
        return this.meetingLoc;
    }

    public Date getMeetingTime()
    {
        return this.meetingTime;
    }

    public int getAvailableSeats()
    {
        return availableSeats;
    }

    // Setters
    public void setPostID(int newPostID)
    {
        this.postID = newPostID;
    }

    public void setUserID(int newUserID)
    {
        this.userID = newUserID;
    }

    public void setDestination(String newDestination)
    {
        this.destination = newDestination;
    }

    public void setMeetingLocation(String newMeetingLocation)
    {
        this.meetingLoc = newMeetingLocation;
    }

    public void setMeetingTime(Date newMeetingTime)
    {
        this.meetingTime = newMeetingTime;
    }

    public void setAvailableSeats(int availableSeats)
    {
        this.availableSeats = availableSeats;
    }
}
