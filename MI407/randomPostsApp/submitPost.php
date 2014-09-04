<?php
session_start();
$connection = mysqli_connect("localhost", "root", "", "inspiration");

$title = mysqli_real_escape_string($connection, $_REQUEST[title]);
$content = mysqli_real_escape_string($connection, $_REQUEST[content]);
$sql_insert = "INSERT INTO posts (userid, title, content) VALUES('$_SESSION[id]','$title','$content')";
if(mysqli_query($connection, $sql_insert))
{
	echo "Okay! Entry successful";
	header("Location: profile.php");
}
else
{
	echo "There was an error when submitting the SQL query.";
	echo mysqli_error($connection);
}

mysqli_close($connection);
?>