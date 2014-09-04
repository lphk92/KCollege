<!doctype html>
<html>
	<body>
		<?php
		echo "<h1>PHP Fortune Teller</h1>\n";
		
		echo "<p>";
		date_default_timezone_set('Asia/Tokyo');
		$val = date("s") % 5;
		
		switch($val)
		{
			case 1: echo "It is going to be a FANTASTIC day!";
				break;
			case 2: echo "Stay sharp! Otherwise there is a high chance of you running into a tree";
				break;
			case 3: echo "Don't worry, it will turn out okay";
				break;
			case 4: echo "Ooo.....you may just want to go back to bed and call it a day...";
				break;
			default: echo "You may or may not have an experience of questionable significance";
		}
				
		echo "</p>";
		?>
	</body>
</html>