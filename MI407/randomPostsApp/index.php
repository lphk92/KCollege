<?php session_start(); ?>
<!doctype html>
<html>
    <head>
        <title>Random Posts</title>
        <style>
        body
        {
            background-color: black;
            color: white;
            width: 1000px;
            margin-left: auto;
            margin-right: auto;
            <?php if (isset($_SESSION["username"])) { ?> margin-top: 20px; <?php } ?>
        }
        div#register
        {
            clear: both;
        }
        #left
        {
            float: left;
            width: 200px;
            padding: 15px;
        }
        #right
        {
            float: left;
            width: 600px;
            padding: 15px;
        }
        #top
        {
            position: fixed;
            top: 0px;
            left: 0px;
            background: gray;
            width: 100%;
            padding: 10px;
        }
        label
        {
            font-size: 20px;
            font-weight: bold;
        }
        input
        {
            font-size: 16px;
            display: block;
            padding: 3px;
            margin-bottom: 5px;
        }
        
        button
        {
            color: gray;
            padding: 3px;
            font-size: 14px;
            font-weight: bold;
            background: white;
            border: 2px gray solid;
            border-radius: 5px;
        }
        button:hover
        {
            background: greenyellow;
            border: 2px green solid;
        }
        a
        {
            text-decoration: none;
        }
        pre
        {
            white-space: pre-wrap;
        }
        </style>
    </head>
    <body>
    <?php
    if (!isset($_SESSION["username"]))
    { ?>
    <div id="left">
        <div id="register">
            <h1>Register</h1>
            <form name="register" action="register.php" method="post">
                <label>Email</label><input type="email" name="email">
                <label>Username</label><input type="text" name="username">
                <label>Password</label><input type="password" name="password">
                <input type="submit">
            </form>
        </div>
        <div id="login">
            <h1>Login</h1>
            <form name="login" action="login.php" method="post">
                <label>Username</label><input type="text" name="username">
                <label>Password</label><input type="password" name="password">
                <input type="submit">
            </form>
        </div>  
    </div>
    <?php
    }
    else
    {
    ?>
    <div id="top">
        <span id="username"><?php echo $_SESSION["username"]; ?></span>
        <a href="profile.php"><button>Profile</button></a>
        <a href="logout.php"><button>Logout</button></a>
    </div>  
    <?php
    }
    ?>
    <div id="right">
        <div id="stream">
        <?php
        $connection = mysqli_connect("localhost", "root", "", "inspiration");

        $sql = "SELECT title,content FROM posts ORDER BY RAND()";
        $result = mysqli_query($connection, $sql);
        while ($row = mysqli_fetch_array($result))
        {
            echo "<div class='post'><h2 class='rainbow'>$row[title]</h2><pre class='rainbow'>$row[content]</pre></div>";
        }

        mysqli_close($connection);
        ?>
        </div>
    </div>
    <!--<script>
    var posts = document.getElementsByClassName("rainbow");
    var colors = ["red", "orange", "yellow", "green", "blue", "purple"];
    var counter = 0;
    for (var i=0 ; i < posts.length ; i++)
    {
        var text = posts[i].innerHTML;
        posts[i].innerHTML = "";
        for (var j=0 ; j < text.length ; j++)
        {
            //posts[i].innerHTML += "<span onmouseover='changeColor(this)' style='color: " + colors[counter] + ";'>" + text[j] + "</span>";
            posts[i].innerHTML += "<span style='color: " + colors[counter] + ";'>" + text[j] + "</span>";
            counter = (counter + 1) % colors.length;
        }
    }
    
    function changeColor(elem)
    {
        elem.style.color = randomColor();
        elem.style.background = randomColor();
        document.getElementsByTagName("body")[0].style.background = randomColor();
    }
    
    function randomColor()
    {
        return colors[Math.floor((Math.random()*colors.length))]; 
    }
    </script>-->
    </body>
</html>
