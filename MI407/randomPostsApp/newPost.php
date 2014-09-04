<!doctype html>
<html>
	<head>
		<title>New Post Entry</title>
		<style>
		body
		{
			background-color: black;
			color: white;
		}
		div
		{
			float: left;
			margin: 0px 30px;
		}
		label
		{
			font-size: 20px;
			font-weight: bold;
		}
		input, textarea
		{
			font-size: 16px;
			display: block;
			padding: 3px;
			margin-bottom: 5px;
		}
		</style>
	</head>
	<body>
		<form name="newPost" action="submitPost.php" method="post">
			<label>Title</label><input type="text" name="title">			
			<label>Content</label><textarea name="content"></textarea>		
			<input type="submit">
		</form>
</html>