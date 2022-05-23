<?php
	header("Content-Type:text/html; charset=utf-8");
	
	$account = $_POST["account"];
	
	$conn = mysqli_connect("localhost", "abc", "123456", "musiclampdb");
	$conn -> set_charset("UTF8");
	

	
	if($conn)
	{
		$q = "SELECT `name`,`gender`,`birthday`,`email` FROM `user` WHERE `account` like '$account'";
		$result = mysqli_query($conn, $q);
		if(mysqli_num_rows($result) > 0)
		{
			echo "使用者資料正確!!!";
			
			while ($row = $result->fetch_assoc())  // 當該指令執行有回傳
			{
				$output[] = $row;  // 就逐項將回傳的東西放到陣列中
				//print_r($row);
				//print("<br>");
			}
			print(Json_encode($output, JSON_UNESCAPED_UNICODE));
			
		}
		else
		{
			echo "使用者資料錯誤!!!";
		}
	}
	else
	{
		echo "沒有連線....";
	}
	
	$conn -> close();
	
	
	/*
	
	// 設定MySQL的連線資訊並開啟連線
	// 資料庫位置、使用者名稱、使用者密碼、資料庫名稱
	$account = $_POST["account"];
	$link = mysqli_connect("localhost", "abc", "123456", "asp");
	$link -> set_charset("UTF8");  // 設定語系避免亂碼
	// SQL指令
	$result = $link -> query("SELECT * FROM `member` WHERE `mem_name` = $account");
	
	
	while ($row = $result->fetch_assoc())  // 當該指令執行有回傳
	{
		$output[] = $row;  // 就逐項將回傳的東西放到陣列中
		print_r($row);
		print("<br>");

	}
	// 將資料陣列轉成 Json 並顯示在網頁上，並要求不把中文編成UNICODE
	print("<br>");
	print(Json_encode($output, JSON_UNESCAPED_UNICODE));
	print("<br>");
	//print_r($output[1]);
	
	
	
	$link -> close();*/
	



?>