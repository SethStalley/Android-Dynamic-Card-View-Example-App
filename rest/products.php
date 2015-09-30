<?php

include_once('config.php');

$storeName = $_GET['storeName'];

$qur = mysql_query("select * from StoreProducts
                    where StoreName= '$storeName' ");
$result = array();

while($r = mysql_fetch_array($qur)){
  extract($r);
  $Name = utf8_encode($Name);
  $result[] = array("name" => $Name, "price"=>$Price,
  "available"=> $Available,  "imgurl"=> $ImgUrl);
}

$json = array("info" => $result);

@mysql_close($conn);

/*Output*/
header('Content-type: application/json');
echo json_encode($json);
