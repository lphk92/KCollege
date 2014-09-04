function updateColor(val)
{
	localStorage["color"] = val;
	$("body").css("color", val);	
	$("body").css("background-image", "linear-gradient(top, " + val + " 1px, black 400px)");
	$("body").css("background-image", "-moz-linear-gradient(top, " + val + " 1px, black 400px)");
	$("body").css("background-image", "-webkit-linear-gradient(top, " + val + " 1px, black 400px)");
	$("body").css("background-image", "-ms-linear-gradient(top, " + val + " 1px, black 400px)");
}

function initColor()
{
	if (localStorage["color"])
	{
		updateColor(localStorage["color"]);
	}
	else
	{
		updateColor("black");
	}
}