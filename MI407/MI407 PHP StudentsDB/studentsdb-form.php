<?php
$connection = mysqli_connect("localhost", "root", "", "students_db");
if (mysqli_connect_errno($connection))
{
	exit("Failed to connect - " . mysqli_connect_error());
}
?>
<!doctype html>
<html>
	<head>
		<style>
		form
		{
			border: 1px dotted black;
			padding: 5px;
			margin: 15px 0px;
		}
		select
		{
			display: block;
		}
		</style>
	</head>
	<body>
		<form action="studentsdb-add-student.php" method="post">
			<h3>Add Student</h3>
			<label>Student</label>
			<input name="firstName" type="text">
			<input name="lastName" type="text">
			<input type="submit" value="Add">
		</form>
	
		<form action="studentsdb-drop-student.php" method="post">
			<h3>Drop Student</h3>
			<label>Student</label>
			<select name="studentId">
				<?php
				$result = mysqli_query($connection, "SELECT * from students");
				while ($row = mysqli_fetch_array($result))
				{
					echo "<option value='$row[id]'>$row[firstName] $row[lastName]</option>";
				}
				?>
			</select>
			<input type="submit" value="Drop">
		</form>
		
		<form action="studentsdb-enroll.php" method="post">
			<h3>Enroll Student</h3>
			<label>Student</label>
			<select name="studentId">
				<?php
				$result = mysqli_query($connection, "SELECT * from students");
				while ($row = mysqli_fetch_array($result))
				{
					echo "<option value='$row[id]'>$row[firstName] $row[lastName]</option>";
				}
				?>
			</select>
			
			<label>Course</label>
			<select name="courseId">
				<?php
				$result = mysqli_query($connection, "SELECT * from courses");
				while ($row = mysqli_fetch_array($result))
				{
					echo "<option value='$row[id]'>$row[name]</option>";
				}
				?>
			</select>
			<input type="submit" value="Enroll">
		</form>
	</body>
</html>