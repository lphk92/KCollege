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
		
		$sql = "SELECT lucky_number FROM testTable WHERE name='lucas'";
		$result = mysqli_query($connection, $sql);
		
		while ($row = mysqli_fetch_array($result))
		{
			echo "$row[lucky_number] <br>";
		}
		
		mysqli_close($connection);
		?>
	</body>
</html>