<!doctype html>
<html>
	<body>
		<?php
		/* These are some comments
		about our first PHP file*/
		
		//create the header
		echo "<h1>Hello World!</h1>";
		
		// These are all in the global scope
		$num1 = 12;
		$num2 = 30;
		
		function localSum()
		{
			/* Despite the variable names, these are different
			   and defined within the local scope */
			$num1 = 26;
			$num2 = 10;
			return $num1 + $num2;
		}
		
		$localsum = localSum();
		$sum = $num1 + $num2;
		$diff = $num1 - $num2;
		$prod = $num1 * $num2;
		$quot = $num1 / $num2;
		$mod = $num1 % $num2;
		
		echo "<p>";
		echo "The sum of $num1 and $num2 = $sum" . "<br>";
		echo "The difference of $num1 and $num2 = $diff \n" . "<br>";
		echo "The product of $num1 and $num2 = $prod" . "<br>";
		echo "The quotient of $num1 and $num2 = $quot<br>";
		echo "The modulus of $num1 and $num2 = $mod<br>";
		echo "The sum of numbers with the <em>same</em> " .
			 "variable names but <b>different</b> scope = $localsum" . "<br>";
		echo "</p>";
		
		
		
		?>
	</body>
</html>