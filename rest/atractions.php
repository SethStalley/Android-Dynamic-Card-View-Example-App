<?php

include_once('config.php');

$qur = mysql_query("select * from ParkActivities
                    where Category= 'ATRACTION' ");
$result = array();

while($r = mysql_fetch_array($qur)){
  extract($r);
  $Name = utf8_encode($Name);
  $Description = utf8_encode($Description);
  $Schedule = utf8_encode($Schedule);
  $State = utf8_encode($State);

  $result[] = array("name" => $Name, "Descripción" => $Description,
  "Horario" => $Schedule, "Estado" => $State, "Capacidad"=>$Capacity,
  "imgurl"=> $ImgUrl);
}

$json = array("info" => $result);

@mysql_close($conn);

/*Output*/
header('Content-type: application/json');
echo json_encode($json);
