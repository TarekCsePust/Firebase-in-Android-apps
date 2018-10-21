<?php

	require 'init.php';
	$token = $_POST["fcm_token"];
	//$token = "heloo";
	$sql = "insert into tokens values('".$token."');";
    mysqli_query($con,$sql);
    mysqli_close($con);
?>