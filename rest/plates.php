<?php

include_once('config.php');

$storeName = $_GET['storeName'];

$qur = mysql_query("select * from RestaurantPlates
                    where RestaurantName = '$storeName' ");
$result = array();

while($r = mysql_fetch_array($qur)){
  extract($r);
  $Name = utf8_encode($Name);
  $Description = utf8_encode($Description);
  $result[] = array("name" => $Name, "Descripción"=>$Description,"imgurl"=> $ImgUrl);
}

$json = array("info" => $result);

@mysql_close($conn);

/*Output*/
header('Content-type: application/json');
echo json_encode($json);
