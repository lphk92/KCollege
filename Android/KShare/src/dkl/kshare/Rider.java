package dkl.kshare;

public class Rider {

	private int userId;
    private int postId;

    /* Blank constructor* */
    public Rider()
    {
        // blank!
    }

    /* Constructor without id* */
    public Rider(int userId, int postId)
    {
        this.userId = userId;
        this.postId = postId;
    }

	public int getUserId() {
		return userId;
	}

	public int getPostId() {
		return postId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setPostId(int postId) {
		this.postId = postId;
	}
}
