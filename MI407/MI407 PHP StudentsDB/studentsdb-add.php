<?php
$firstName = $_REQUEST["firstName"];
$lastName = $_REQUEST["lastName"];

$connection = mysqli_connect("localhost", "root", "", "students_db");
if (mysqli_connect_errno($connection))
{
	exit("Failed to connect - " . mysqli_connect_error());
}	

$sql = "INSERT INTO students (firstName, lastName) VALUES
		('$firstName', '$lastName')";
if (!mysqli_query($connection, $sql))
{
	exit("Failed to insert into database - " . mysqli_error($connection));
}

header("Location: studentsdb-form.php");
?>