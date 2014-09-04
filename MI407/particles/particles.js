// Dimensions
var width = window.innerWidth;
var height = window.innerHeight/2;

//Initialize the canvas
var canvas = document.getElementById("canvas");
canvas.width = width;
canvas.height = height;
var context = canvas.getContext("2d");

//Initialize list of particles
var particles = new Array();

window.onresize = function()
{
	width = window.innerWidth;
	height = window.innerHeight / 4.1;
	canvas.width = width;
	canvas.height = height;
	context = canvas.getContext("2d");
	particles = new Array();
}

function generateParticle()
{
	var particle = new Object();
	particle.x = Math.random()*width;
	particle.y = Math.random()*height;
	
	particle.dimension = Math.random()*40 + 10;
	
	particle.vx = Math.random()*20 - 10;
	particle.vy = 0;//Math.random()*20 - 10;
	
	r = Math.floor(Math.random()*255);
	g = Math.floor(Math.random()*255);
	b = Math.floor(Math.random()*255);
	
	particle.color = "rgba(" + r + ", " + g + ", " + b + ", 0.5)";
	return particle;
}

function draw()
{
	var width = document.documentElement.clientWidth;
	var height = document.documentElement.clientHeight;

	// Fill in background
	context.globalCompositeOperation = "source-over";
	context.fillStyle = "rgba(0, 0, 0, 0.1)";
	context.fillRect(0, 0, width, height);
	
	// Draw the particles
	context.globalCompositeOperation = "lighter";
	for (var i = 0 ; i < particles.length ; i++)
	{
		var p = particles[i];
		var halfDim = p.dimension/2;

		context.fillStyle = p.color;
		context.fillRect(p.x, p.y, p.dimension, p.dimension);
		
		p.x += p.vx;
		p.y += p.vy;
		
		if (p.x > width) p.x = 0;
		if (p.y > height) p.y = 0;
		if (p.x < (0 - p.dimension)) p.x = width;
		if (p.y < (0 - p.dimension)) p.y = height;
	}
}

var decrease = false;
function addParticle()
{
	if (particles.length < 100 && !decrease)
	{
		particles.push(generateParticle());
	}
	else
	{
		decrease = true;
	}
	
	if (decrease)
	{
		particles.pop();
	}
	
	if (particles.length == 0)
	{
		decrease = false;
	}
}

setInterval(draw, 33);
setInterval(addParticle, 300);