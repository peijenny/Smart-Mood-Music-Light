<?php

	$modifyAccount = $_POST["modifyAccount"];
	$modifySelectStartDate = $_POST["modifySelectStartDate"];
	$modifySelectStartTime = $_POST["modifySelectStartTime"];
	
	/*
	$modifyMode = $_POST["modifyMode"];
	$modifyType = $_POST["modifyType"];
	$modifyMusic = $_POST["modifyMusic"];
	$modifyLight = $_POST["modifyLight"];
	$modifyStartDate = $_POST["modifyStartDate"];
	$modifyStartTime = $_POST["modifyStartTime"];
	*/
	
	$conn = mysqli_connect("localhost", "abc", "123456", "musiclampdb");
	$conn -> set_charset("UTF8");
	
	if($conn)
	{
		$q = "DELETE FROM `setting` WHERE `user_acc` = '$modifyAccount' and `setting`.`start_date` = '$modifySelectStartDate' and `setting`.`start_time` = '$modifySelectStartTime'";
		

		if(mysqli_query($conn, $q))
		{
			echo "刪除設定成功!!!";
			
		}
		else
		{
			echo "刪除設定失敗!!!";
		}
	}
	else
	{
		echo "沒有連線....";
	}

	$conn -> close();


?>