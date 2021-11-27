<?php

$conexion =mysqli_connect("localhost", "id18017212_id18004791_admin", "3lLid3rPili#73","id18017212_id18004791_teorganizo");
if(!$conexion){
    echo "Error de conexion";
}

$result= array();
$result['cliente'] =array();
$query ="SELECT * FROM cliente";
$response = mysqli_query($conexion, $query);

while($row = mysqli_fetch_array($response))
{
  $index['cedula'] =$row['0'];
  $index['nombre'] =$row['1'];
  $index['apellido'] =$row['2'];


  array_push($result['cliente'],$index);
}
$result["Exito"]="1";
echo json_encode($result);



?>