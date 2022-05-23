<?php

	$modifyAccount = $_POST["modifyAccount"];
	$modifySelectStartDate = $_POST["modifySelectStartDate"];
	$modifySelectStartTime = $_POST["modifySelectStartTime"];
	$modifyMode = $_POST["modifyMode"];
	$modifyType = $_POST["modifyType"];
	$modifyMusic = $_POST["modifyMusic"];
	$modifyLight = $_POST["modifyLight"];
	$modifyStartDate = $_POST["modifyStartDate"];
	$modifyStartTime = $_POST["modifyStartTime"];
	
	$conn = mysqli_connect("localhost", "abc", "123456", "musiclampdb");
	$conn -> set_charset("UTF8");
	
	if($conn)
	{
		$q = "UPDATE `setting` SET `user_mode` = '$modifyMode', `user_type` = '$modifyType', `user_music` = '$modifyMusic', `user_light` = '$modifyLight',
		`start_date` = '$modifyStartDate', `start_time` = '$modifyStartTime' WHERE `setting`.`user_acc` = '$modifyAccount' and
		`setting`.`start_date` = '$modifySelectStartDate' and `setting`.`start_time` = '$modifySelectStartTime'";

		if(mysqli_query($conn, $q))
		{
			echo "修改設定成功!!!";
			
		}
		else
		{
			echo "修改設定成功!!!";
		}
	}
	else
	{
		echo "沒有連線....";
	}

	$conn -> close();


?>