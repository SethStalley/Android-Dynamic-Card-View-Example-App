<?php

include_once('config.php');

$qur = mysql_query("select * from ParkActivities
                    where Category= 'STORE' ");
$result = array();

while($r = mysql_fetch_array($qur)){
  extract($r);
  $Name = utf8_encode($Name);
  $Schedule = utf8_encode($Schedule);
  $State = utf8_encode($State);
  $result[] = array("name" => $Name, "schedule" => $Schedule,
  "imgurl"=> $ImgUrl);
}

$json = array("info" => $result);

@mysql_close($conn);

/*Output*/
header('Content-type: application/json');
echo json_encode($json);
