<!doctype html>
<html>
	<body>
	<h1>PHP Database</h1>
		<?php
		$connection = mysqli_connect("localhost", "root", "", "test");
		if (mysqli_connect_errno($connection))
		{
			echo "Failed to connect - " . mysqli_connect_error();
		}	
		
		$sql = "INSERT INTO testTable (name, lucky_number) VALUES ('lucas', 42)";
		mysqli_query($connection, $sql);
		mysqli_close($connection);
		?>
	</body>
</html>