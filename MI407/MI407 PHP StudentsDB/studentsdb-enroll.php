<?php
$studentId = $_REQUEST["studentId"];
$courseId = $_REQUEST["courseId"];

$connection = mysqli_connect("localhost", "root", "", "students_db");
if (mysqli_connect_errno($connection))
{
	exit("Failed to connect - " . mysqli_connect_error());
}	

$sql = "SELECT * FROM enrollment WHERE studentId=$studentId AND courseId='$courseId'";
$result = mysqli_query($connection, $sql);
$rowCount = mysqli_num_rows($result);
if ($rowCount == 0)
{
	$sql = "INSERT INTO enrollment (studentId, courseId) VALUES 
			('$studentId', '$courseId')";
	if (!mysqli_query($connection, $sql))
	{
		exit("Failed to insert into database - " . mysqli_error($connection));
	}

	header("Location: studentsdb-form.php");
}
else
{
	echo "<p>The desired enrollment has already been made.</p>";
}
?>