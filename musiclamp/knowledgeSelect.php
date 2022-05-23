<?php
	
	$account = $_POST["account"];
	
	$conn = mysqli_connect("localhost", "abc", "123456", "musiclampdb");
	$conn -> set_charset("UTF8");
	
	if($conn)
	{
		$q = "SELECT * FROM `knowledge`";
		$result = mysqli_query($conn, $q);
		if(mysqli_num_rows($result) > 0)
		{
			echo "QA小知識查詢成功!!!";
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
			echo "QA小知識查詢失敗!!!";
		}
	}
	else
	{
		echo "沒有連線....";
	}
	
	$conn -> close();
	
	
	



?>