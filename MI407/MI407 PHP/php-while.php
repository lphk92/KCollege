<!doctype html>
<html>
	<body>
		<?php
		echo "<h1>PHP Test Page</h1>\n";
		
		// Enter a do-while loop until finding the answer
		echo "<p style='word-wrap: break-word;'>";
		do
		{
			$next = rand(1, 1000);			
			echo $next . ".";
		} while ($next != 42);
		echo "</p>";
		?>
	</body>
</html>