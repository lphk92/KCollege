<?php
$studentId = $_REQUEST["studentId"];

$connection = mysqli_connect("localhost", "root", "", "students_db");
if (mysqli_connect_errno($connection))
{
	exit("Failed to connect - " . mysqli_connect_error());
}	

$sql = "DELETE FROM enrollment WHERE studentId=$studentId";
if (!mysqli_query($connection, $sql))
{
	exit("Failed to delete from database - " . mysqli_error($connection));
}

$sql = "DELETE FROM students WHERE id=$studentId";
if (!mysqli_query($connection, $sql))
{
	exit("Failed to delete from database - " . mysqli_error($connection));
}

header("Location: studentsdb-form.php");
?>