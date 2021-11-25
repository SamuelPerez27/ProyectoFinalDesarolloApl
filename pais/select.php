<?php

$conexion =mysqli_connect("localhost", "id18004791_admin", "3lLid3rPili#73","id18004791_teorganizo");
if(!$conexion){
    echo "Error de conexion";
}

$result= array();
$result['datos'] =array();
$query ="SELECT * FROM pais";
$response = mysqli_query($conexion, $query);

while($row = mysqli_fetch_array($response))
{
  $index['id_pais'] =$row['0'];
  $index['codigo'] =$row['1'];
  $index['nombre'] =$row['2'];

  array_push($result['datos'],$index);
}
$result["Exito"]="1";
echo json_encode($result);



?>