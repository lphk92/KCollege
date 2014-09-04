<!doctype html>
<html>
	<body>
		<?php
		echo "<h1>PHP Test Page</h1>\n";
		
		echo "<p>";
		date_default_timezone_set('Asia/Tokyo');
		$hour = date("H");
		$min = date("i");
		echo "The current time is $hour:$min <br><br>";
		
		if ($hour < 12)
		{
			echo "Good Morning!";
		}
		else if ($hour < 16)
		{
			echo "Good Afternoon!";
		}
		else if ($hour < 20)
		{
			echo "Good Evening!";
		}
		else if ($hour < 24)
		{
			echo "Good Night!";
		}
		else
		{
			echo "Well...I have no clue what time it is...<br>Good day!";
		}
		
		echo "</p>";
		?>
	</body>
</html>