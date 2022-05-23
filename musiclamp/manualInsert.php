<?php
	
	$manualAccount = $_POST["manualAccount"];
	$manualMode = $_POST["manualMode"];
	$manualType = $_POST["manualType"];
	$manualMusic = $_POST["manualMusic"];
	$manualLight = $_POST["manualLight"];	
	$manualStartDate = $_POST["manualStartDate"];
	$manualStartTime = $_POST["manualStartTime"];
	
	$conn = mysqli_connect("localhost", "abc", "123456", "musiclampdb");
	$conn -> set_charset("UTF8");
	
	if($conn)
	{
		
		$q = "INSERT INTO `setting` (`setting_id`, `user_acc`, `user_mode`, `user_type`, `user_music`, `user_light`, `start_date`, `start_time`, `user_hr`) 
		VALUES (NULL, '$manualAccount', '$manualMode', '$manualType', '$manualMusic', '$manualLight', '$manualStartDate', '$manualStartTime', '')";
		
		if(mysqli_query($conn, $q))
		{
			echo "手動設定新增成功!!!";
		}
		else
		{
			echo "手動設定新增失敗!!!";
		}
	}
	else
	{
		echo "沒有連線....";
	}
	
	$conn -> close();


?>