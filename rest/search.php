<?php

include_once('config.php');

$item = $_GET['item'];

$qur = mysql_query("select * from ParkActivities
                    where Name like '%$item%' ");
$result = array();

while($r = mysql_fetch_array($qur)){
  extract($r);
  $Name = utf8_encode($Name);
  $Description = utf8_encode($Description);
  $Location = utf8_encode($Location);
  $Schedule = utf8_encode($Schedule);
  $State = utf8_encode($State);
  $Category = utf8_encode($Category);
  $result[] = array("name" => $Name, "Descripción"=>$Description, "Lugar"=>$Location,
   "Horario"=> $Schedule, "Estado" => $State,
   "Categoria"=>$Category,"imgurl"=> $ImgUrl);
}

$json = array("info" => $result);

@mysql_close($conn);

/*Output*/
header('Content-type: application/json');
echo json_encode($json);
