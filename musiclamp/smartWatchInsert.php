<?php
	
	$watchAccount = $_POST["watchAccount"];
	$watchMode = $_POST["watchMode"];
	$watchType = $_POST["watchType"];
	$watchMusic = $_POST["watchMusic"];
	$watchLight = $_POST["watchLight"];	
	$watchStartDate = $_POST["watchStartDate"];
	$watchStartTime = $_POST["watchStartTime"];
	$watchHr = $_POST["watchHr"];
	
	$conn = mysqli_connect("localhost", "abc", "123456", "musiclampdb");
	$conn -> set_charset("UTF8");
	
	if($conn)
	{
		
		$q = "INSERT INTO `setting` (`setting_id`, `user_acc`, `user_mode`, `user_type`, `user_music`, `user_light`, `start_date`, `start_time`, `user_hr`) 
		VALUES (NULL, '$watchAccount', '$watchMode', '$watchType', '$watchMusic', '$watchLight', '$watchStartDate', '$watchStartTime', '$watchHr')";
		
		if(mysqli_query($conn, $q))
		{
			echo "智慧手環設定新增成功!!!";
		}
		else
		{
			echo "智慧手環設定新增失敗!!!";
		}
	}
	else
	{
		echo "沒有連線....";
	}
	
	$conn -> close();


?>