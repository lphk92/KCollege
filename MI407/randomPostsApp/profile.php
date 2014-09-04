<?php
session_start();
$username = $_SESSION['username'];
?>
<!doctype html>
<html>
    <head>
        <title>User Profile</title>
		<style>
		body
		{
			background-color: black;
			color: white;
		}
		label
		{
			font-size: 20px;
			font-weight: bold;
		}
		input
		{
			font-size: 16px;
			display: block;
			padding: 3px;
			margin-bottom: 5px;
		}
		
		button
		{
			color: gray;
			padding: 10px;
			font-size: 20px;
			font-weight: bold;
			background: white;
			border: 2px gray solid;
			border-radius: 5px;
		}
		button:hover
		{
			background: greenyellow;
			border: 2px green solid;
		}
		
		div#controls
		{
			display: block;
		}				
		div#posts
		{
			display: block;
			margin: 10px 0px;
			border: 1px solid white;
		}
			div.post
			{
				padding: 10px;
				border: 2px white dotted;
				box-sizing: content-box;
			}
			div.post:hover
			{
				background: darkgray;
			}
			
		a
		{
			text-decoration: none;
		}
		</style>
    </head>
    <body>
        <h1>Profile for user <?php echo $username; ?></h1>
		<div id="controls">
			<a href="index.php"><button>Index</button></a>
			<a href="newPost.php"><button>New Post</button></a>
			<a href="logout.php"><button>Logout</button></a>
		</div>
		
		<div id="posts">
		<?php
		$connection = mysqli_connect("localhost", "root", "", "inspiration");

		$sql = "SELECT title,content FROM posts WHERE userid=$_SESSION[id]";
		$result = mysqli_query($connection, $sql);
		while ($row = mysqli_fetch_array($result))
		{
			echo "<div class='post' id='$row[title]'>$row[title] --- $row[content]</div>";
		}

		mysqli_close($connection);
		?>
		</div>
    </body>
</html>
