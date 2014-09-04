<!doctype html>
<html>
	<body>
	<h1>PHP Database</h1>
		<?php
		$connection = mysqli_connect("localhost", "root", "");

		if (mysqli_connect_errno($connection))
		{
			echo "Failed to connect - " . mysqli_connect_error();
		}

		if (mysqli_query($connection, "CREATE DATABASE test"))
		{
			echo "<b>Succesfully created database \"test\"</b>";
		}
		else
		{
			echo "<b>Failed creating database - </b> " . mysqli_error($connection);
		}
		
		echo "<br>";
		
		if (mysqli_query($connection, "CREATE TABLE test.testTable(name CHAR(10), lucky_number INT)"))
		{
			echo "<b>Succesfully created table \"testTable\"</b>";
		}
		else
		{
			echo "<b>Failed creating table - </b> " . mysqli_error($connection);
		}
		?>
		
	</body>
</html>