<!doctype html>
<html>
	<body>
		<?php
		echo "<h1>PHP Test Page</h1>\n";
		
		$countries = array("Japan", "America", "United Kingdom");
		$capitals = array("Japan"=>"Tokyo",
							 "America"=>"Washington DC",
							 "United Kingdom"=>"London");
		echo "<p><h2>Just a list of countries</h2>\n";
		for ($i = 0 ; $i < count($countries); $i++)
		{
			echo $countries[$i] . "<br>";
		}
		
		echo "</p>";
		
		echo "<p><h2>Now with the capitals</h2>";
		foreach ($capitals as $country=>$capital)
		{
			echo "The capital of $country is $capital<br>";
		}
		echo "</p>";
		?>
	</body>
</html>