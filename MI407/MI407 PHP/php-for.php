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
		
		echo "<h2>And now for some sorting</h2>";
		sort($countries); echo "<h4>Ascending</h4>";prtCnt();
		rsort($countries); echo "<h4>Descending</h4>";prtCnt();
		asort($capitals); echo "<h4>Ascending Value</h4>"; prtCpt();
		arsort($capitals); echo "<h4>Descending Value</h4>"; prtCpt();
		ksort($capitals); echo "<h4>Ascending Key</h4>"; prtCpt();
		krsort($capitals); echo "<h4>Descending Key</h4>"; prtCpt();
		
		function prtCnt()
		{
			global $countries;
			echo "<p>";
			for ($i = 0 ; $i < count($countries); $i++)
			{
				echo $countries[$i] . "<br>";
			}
			echo "</p>";
		}
		function prtCpt()
		{
			global $capitals;
			echo "<p>";
			foreach ($capitals as $country=>$capital)
			{
				echo "The capital of $country is $capital<br>";
			}
			echo "</p>";
		}
		?>
	</body>
</html>