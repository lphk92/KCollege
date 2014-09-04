<?php
$connection = mysqli_connect("localhost", "root", "");
if (mysqli_connect_errno($connection))
{
	exit("Failed to connect - " . mysqli_connect_error());
}	

$sql = "CREATE DATABASE students_db";
if (!mysqli_query($connection, $sql))
{
	exit("Failed to create database - " . mysqli_error($connection));
}

$sql = "CREATE TABLE students_db.students(" .
		"id int NOT NULL AUTO_INCREMENT," .
		"firstName VARCHAR(255) NOT NULL," .
		"lastName VARCHAR(255) NOT NULL," .
		"PRIMARY KEY (id))";
if (!mysqli_query($connection, $sql))
{
	exit("Failed to create table - " . mysqli_error($connection));
}

$sql = "CREATE TABLE students_db.courses(" .
		"name VARCHAR(255) NOT NULL," .
		"id VARCHAR(16) NOT NULL)";
if (!mysqli_query($connection, $sql))
{
	exit("Failed to create table - " . mysqli_error($connection));
}

$sql = "CREATE TABLE students_db.enrollment(" .
		"courseId VARCHAR(16) NOT NULL," .
		"studentId int NOT NULL)";
if (!mysqli_query($connection, $sql))
{
	exit("Failed to create table - " . mysqli_error($connection));
}

echo "<h2>Success!</h2>";
?>