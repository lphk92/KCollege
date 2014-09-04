<!doctype html>
<html>
	<head>
		<title>Registration Result</title>
	</head>
	<body>
        <p>
        This is the registration page.
        </p>
        <?php 
        function isValid($str)
        {
            return !is_null($str) and trim($str) != "";
        }

        // Check that all of the necessary values are there
        if(isValid($_POST['username']) and isValid($_POST['password']) and isValid($_POST['email']))
        {
            $connection = mysqli_connect("localhost", "root", "", "inspiration");

            $sql_insert = "INSERT INTO users (username, password, email) VALUES('$_POST[username]','$_POST[password]','$_POST[email]')";
            if(mysqli_query($connection, $sql_insert))
            {
                echo "Okay! Entry successful";
            }
            else
            {
                echo "There was an error when submitting the SQL query.";
                echo mysqli_error($connection);
            }

            mysqli_close($connection);
        }
        else
        {
            echo "<p>There was an error registering your account. A valid username, password, and email must be included.</p>";
        }
        ?>
	</body>	
</html>
