<?php

	require 'init.php';
	$msg = $_POST["message"];
	$title = $_POST["title"];
	$path_fcm = 'https://fcm.googleapis.com/fcm/send';
	$server_key = "AAAA8HNLE0A:APA91bF9lDrh4o0A3rsR2IgnRBaO94ERt8qe8lEYpJbMb_xSrJDEVIZXPMxOrsz0WDF48t1bMG-cXNxATtNn19cftf6cRpxZ3EQwhkrLS6EZVPVgSZ-tFDXlSuOiJOlv_1hwBjf7JOBF";
	
	$sql = "select *from tokens;";
	$result = mysqli_query($con,$sql);
	








/*$query= "select * from contact;"; 
	$result = mysqli_query($con,$query);
	$response = array();
    while ($row = mysqli_fetch_array($result))
    {
    	array_push($response,array("Name"=>$row["name"],"ID"=>$row["id"]));
    	
    }*/
















	
	
		while ($row = mysqli_fetch_array($result)) {
			
			$key = $row["token"];





			$msg = array
			(
				'message' 	=> 'I Love Bangladesh',
				'title'		=> $title,
				'body'	=>$msg,
				'click_action'=>'com.example.hasantarek.firebasepushnotification.notificationActivity'

				
			);








			$headers = array(
			'Authorization:key = ' .$server_key,
			'Content-Type:application/json' 
			);




			$fields = array(
				"to"=>$key,
				"data"=>$msg
				);





			/*$fields = array("to"=>$key,"notification"=>array('title'=>$title,'body'=>$msg,
				'data'=>'I Love Bangladesh',
				'click_action'=>'com.example.hasantarek.firebasepushnotification.notificationActivity'));*/

			$pay_load = json_encode($fields);

			$curl_session = curl_init();

			curl_setopt($curl_session,CURLOPT_URL,$path_fcm);
			curl_setopt($curl_session,CURLOPT_POST,true);
			curl_setopt($curl_session,CURLOPT_HTTPHEADER,$headers);
			curl_setopt($curl_session,CURLOPT_RETURNTRANSFER,true);
			curl_setopt($curl_session,CURLOPT_SSL_VERIFYPEER,false);
			curl_setopt($curl_session,CURLOPT_IPRESOLVE,CURL_IPRESOLVE_V4);
	
			curl_setopt($curl_session,CURLOPT_POSTFIELDS,$pay_load);

			

			$res =  curl_exec($curl_session);
			curl_close($curl_session);

			
	}

	
    mysqli_close($con);
	

	//echo json_encode($tokens);
	//echo "\n\n";
	//echo json_encode($key);

/*	$headers = array(
		'Authorization:key = ' .$server_key,
		'Content-Type:application/json' 
	);

	$fields = array("to"=>$key,"notification"=>array('title'=>$title,'body'=>$msg));

	$pay_load = json_encode($fields);

	$curl_session = curl_init();

	curl_setopt($curl_session,CURLOPT_URL,$path_fcm);
	curl_setopt($curl_session,CURLOPT_POST,true);
	curl_setopt($curl_session,CURLOPT_HTTPHEADER,$headers);
	curl_setopt($curl_session,CURLOPT_RETURNTRANSFER,true);
	curl_setopt($curl_session,CURLOPT_SSL_VERIFYPEER,false);
	curl_setopt($curl_session,CURLOPT_IPRESOLVE,CURL_IPRESOLVE_V4);
	
	curl_setopt($curl_session,CURLOPT_POSTFIELDS,$pay_load);

	$result =  curl_exec($curl_session);*/
	//curl_close($curl_session);
	//mysqli_close($con);

?>