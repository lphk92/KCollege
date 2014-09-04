<?php
$connection = mysqli_connect("localhost", "root", "", "students_db");
if (mysqli_connect_errno($connection))
{
	exit("Failed to connect - " . mysqli_connect_error());
}	

$sql = "INSERT INTO students(firstName, lastName) VALUES 
		('John', 'Smith'),
		('Mary', 'Jones')";
if (!mysqli_query($connection, $sql))
{
	exit("Failed to insert into database - " . mysqli_error($connection));
}

$sql = "INSERT INTO courses(id, name) VALUES 
		('CP0001', 'Introduction to Computer Programming'),
		('CP0002', 'Programming the Web'),
		('CP0003', 'Computerized Society')";
if (!mysqli_query($connection, $sql))
{
	exit("Failed to insert into database - " . mysqli_error($connection));
}

echo "<h2>Success!</h2>";
?>