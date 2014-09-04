<?php
session_start();
function isValid($str)
{
	return !is_null($str) and trim($str) != "";
}

if (isValid($_POST['username']) and isValid($_POST['password']))
{
	$connection = mysqli_connect("localhost", "root", "", "inspiration");

	$sql= "SELECT password, id FROM users WHERE username='$_POST[username]'";
	$result = mysqli_query($connection, $sql);
	if($result)
	{
		$row = mysqli_fetch_array($result);
		$password = $row['password'];
		$id = $row['id'];
		if ($password == $_POST['password'])
		{
			$_SESSION['username']=$_POST['username'];
			$_SESSION['password']=$password;
			$_SESSION['id']=$id;
			header("Location: profile.php");
		}
		else
		{
			echo "Password mismatch";
		}
	}
	else
	{
		echo "There was an error when submitting the SQL query.";
		echo mysqli_error($connection);
	}

	mysqli_close($connection);

}
else
{
	echo "<p>There was an error registering your account. A valid username and password, be included.</p>";
}
?>